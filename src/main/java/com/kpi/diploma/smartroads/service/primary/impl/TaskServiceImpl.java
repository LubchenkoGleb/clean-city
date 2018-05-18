package com.kpi.diploma.smartroads.service.primary.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.kpi.diploma.smartroads.model.document.map.Container;
import com.kpi.diploma.smartroads.model.document.map.MapObject;
import com.kpi.diploma.smartroads.model.document.map.Task;
import com.kpi.diploma.smartroads.model.document.map.TaskPoint;
import com.kpi.diploma.smartroads.model.document.user.Company;
import com.kpi.diploma.smartroads.model.util.data.kmeans.KMEansRequest;
import com.kpi.diploma.smartroads.model.util.data.kmeans.KMeansRow;
import com.kpi.diploma.smartroads.model.util.data.shortest.path.ShortestPathRow;
import com.kpi.diploma.smartroads.model.util.exception.CompanyEndpoinsNotSet;
import com.kpi.diploma.smartroads.model.util.exception.TaskCreationException;
import com.kpi.diploma.smartroads.model.util.title.value.ContainerValues;
import com.kpi.diploma.smartroads.model.util.title.value.MapObjectDescriptionValues;
import com.kpi.diploma.smartroads.repository.map.MapObjectRepository;
import com.kpi.diploma.smartroads.repository.map.RouteRepository;
import com.kpi.diploma.smartroads.repository.map.TaskRepository;
import com.kpi.diploma.smartroads.repository.user.CompanyRepository;
import com.kpi.diploma.smartroads.service.primary.TaskService;
import com.kpi.diploma.smartroads.service.util.ConversionService;
import com.kpi.diploma.smartroads.service.util.http.HttpMethods;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

//import javafx.util.Pair;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private final int GARBAGE_TRUCK_CAPACITY = 5;

    private final HttpMethods httpMethods;

    private final RouteRepository routeRepository;

    private final MapObjectRepository mapObjectRepository;

    private final CompanyRepository companyRepository;

    private final TaskRepository taskRepository;

    public TaskServiceImpl(HttpMethods httpMethods,
                           RouteRepository routeRepository,
                           MapObjectRepository mapObjectRepository,
                           CompanyRepository companyRepository,
                           TaskRepository taskRepository) {
        this.httpMethods = httpMethods;
        this.routeRepository = routeRepository;
        this.mapObjectRepository = mapObjectRepository;
        this.companyRepository = companyRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void createTasksForAllServices() {

    }

    @Override
    public List<Task> createTaskForService(String serviceId) {
        log.info("'createTaskForService' invoked with params'{}'", serviceId);

        validate(serviceId);

        List<MapObject> allByOwnerId = mapObjectRepository.findAllByOwnerId(serviceId);
        log.info("'allByOwnerId={}'", allByOwnerId);

        Map<String, Container> idContainerMap = allByOwnerId.stream()
                .filter(mapObject -> mapObject.getDescription().equals(MapObjectDescriptionValues.CONTAINER.toString()))
                .map(mo -> (Container) mo)
                .collect(Collectors.toMap(Container::getId, container -> container));

        Map<String, List<String>> requestsByType = createRequestByType(idContainerMap.values());

        List<Task> response = new ArrayList<>();
        ExecutorService executor = Executors.newWorkStealingPool();

        requestsByType.forEach((type, ids) -> executor.submit(() -> {

            if (!ids.isEmpty()) {

                List<KMeansRow> kMeansRows = createKMeansMatrix(ids, idContainerMap);
                log.debug("kMeansRows for type'{}' = '{}'", type, kMeansRows);

                List<List<String>> clusters = getClusters(kMeansRows);
                clusters = normalizeKMeans(GARBAGE_TRUCK_CAPACITY, clusters, kMeansRows);
                log.debug("after clusters={}", clusters);

                Map<String, Integer> duplications = calculateDuplications(clusters);
//            log.debug("'duplications={}'", duplications);
                List<List<String>> clustersWithoutDuplications = deleteDuplicatesFromCluster(clusters);
//            log.info("'clustersWithoutDuplications={}'", clustersWithoutDuplications);

                List<List<ShortestPathRow>> clustersForShortestPath = new ArrayList<>();
                clustersWithoutDuplications.forEach(cluster ->
                        clustersForShortestPath.add(buildMatrixForShortestPath(cluster, allByOwnerId)));
                log.debug("'clustersForShortestPath={}'", clustersForShortestPath);

                List<List<String>> shortestPaths = getShortestPath(clustersForShortestPath);

                shortestPaths.forEach(cluster -> response.add(createTask(cluster, serviceId, duplications, type)));
            }
        }));

        executor.shutdown();

        try {
            executor.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new TaskCreationException(e.getMessage());
        }

        return taskRepository.save(response);
    }

    private Company validate(String companyId) {

        Company company = companyRepository.findOne(companyId);

        if (company.getStart() == null || company.getFinish() == null) {
            throw new CompanyEndpoinsNotSet("company should have start and finish to process task");
        }

        return company;
    }

    private List<KMeansRow> createKMeansMatrix(List<String> ids, Map<String, Container> idContainerMap) {
        // TODO: 10/05/18 optimize for same ids
        List<KMeansRow> kMeansRows = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {

            String id = ids.get(i);

            if (i != 0 && ids.get(i - 1).equals(id)) {
                KMeansRow previousRow = kMeansRows.get(kMeansRows.size() - 1);
                KMeansRow newRow = previousRow.toBuilder().build();
                newRow.setId(previousRow.getId().split("/")[0] + "/" + UUID.randomUUID());
                kMeansRows.add(newRow);
            } else {
                List<String> idsWithoutCurrent = getIdsWithOutCurrent(ids, id);

                List<Long> distanceToOtherObjects = getDistanceToOtherObjects(idContainerMap.get(id), idsWithoutCurrent);

                int firstIndex = ids.indexOf(id);
                int lastIndex = ids.lastIndexOf(id);

                distanceToOtherObjects.addAll(firstIndex, Collections.nCopies(lastIndex - firstIndex + 1, 0L));

                KMeansRow row = new KMeansRow(id + "/" + UUID.randomUUID(), distanceToOtherObjects);
                kMeansRows.add(row);
            }
        }

        return kMeansRows;
    }

    private List<List<String>> getClusters(List<KMeansRow> kMeansRows) {

        int clustersAmount = (int) Math.ceil(kMeansRows.size() / (double) GARBAGE_TRUCK_CAPACITY);

        KMEansRequest kmEansRequest = new KMEansRequest(clustersAmount, kMeansRows);
        JsonNode response = httpMethods.sendPostRequest(
                "https://us-central1-cleancity-web-1506581318975.cloudfunctions.net/kmeans", kmEansRequest);

        List<List<String>> clusters = new ArrayList<>();
        clusters = ConversionService.convertToObject(response, clusters.getClass());

        return clusters;
    }

    private List<List<String>> getShortestPath(List<List<ShortestPathRow>> shortestPathRequest) {

        JsonNode response = httpMethods.sendPostRequest(
                "https://us-central1-cleancity-web-1506581318975.cloudfunctions.net/shortestPath", shortestPathRequest);

        List<List<String>> path = new ArrayList<>();
        return ConversionService.convertToObject(response, path.getClass());
    }

    private List<Long> getDistanceToOtherObjects(MapObject mapObject, List<String> otherIds) {
        log.debug("'getDistanceToOtherObjects' invoked with params'{}, {}'", mapObject, otherIds);

        ArrayList<Long> response = new ArrayList<>(Collections.nCopies(otherIds.size(), 0L));

        mapObject.getStartRoutes().forEach(route -> {

            String id = route.getFinish().getId();

            if (otherIds.contains(id)) {
                int firstIndex = otherIds.indexOf(id);
                int lastIndex = otherIds.lastIndexOf(id);

                for (int i = firstIndex; i < lastIndex + 1; i++) {
                    response.set(i, route.getLength());
                }
            }

        });
        log.debug("'response={}'", response);

        return response;
    }

    public List<List<String>> normalizeKMeans(Integer clusterSize, List<List<String>> clustersByIds, List<KMeansRow> kMeansRows) {
//        log.info("'normalizeKMeans' params'{}, {}, {}", clusterSize, clustersByIds, kMeansRows);

        List<Integer> cannotBeMovedIndexes = new ArrayList<>();

        List<List<Integer>> clustersByIndexes = createIndexBasedClusters(clustersByIds, createIdIndexMap(kMeansRows));
        clustersByIndexes.sort(Comparator.comparingInt((ToIntFunction<List>) List::size).reversed());
//        log.info("'clustersByIndexes={}'", clustersByIndexes);

        while (clustersByIndexes.get(0).size() > clusterSize) {

            List<Integer> currentClusterIndexes = clustersByIndexes.get(0);
//            log.info("'currentClusterIndexes={}'", currentClusterIndexes);

            Map<Integer, Pair<Integer, Long>> currentClusterDistances = new HashMap<>();
            for (Integer id : currentClusterIndexes) {
                currentClusterDistances.put(id, new ImmutablePair<>(-1, Long.MAX_VALUE));
            }

            for (Integer currentClusterIndex : currentClusterIndexes) {
                for (int i = 0; i < kMeansRows.size(); i++) {
                    if (currentClusterIndex != i && !cannotBeMovedIndexes.contains(i) && !currentClusterIndexes.contains(i)) {

                        Long avgDist = (kMeansRows.get(currentClusterIndex).getValue().get(i) + kMeansRows.get(i).getValue().get(currentClusterIndex)) / 2;
//                        log.info("i={}, j={}, oldDist={}, newDist={}", currentClusterIndex, i, currentClusterDistances.get(currentClusterIndex), avgDist);

                        if (avgDist < currentClusterDistances.get(currentClusterIndex).getValue()) {
                            currentClusterDistances.put(currentClusterIndex, new ImmutablePair<>(i, avgDist));
                        }
                    }
                }
            }
//            log.info("'currentClusterDistances={}'", currentClusterDistances);

            Map<Integer, Pair<Integer, Long>> orderedCurrentClusterDistances = orderDistances(currentClusterDistances);
//            log.info("'orderedCurrentClusterDistances={}'", orderedCurrentClusterDistances);

            orderedCurrentClusterDistances.forEach((k, v) -> {

                if (currentClusterIndexes.size() > clusterSize) {
                    moveElementToAnotherCluster(currentClusterIndexes, k, v.getKey(), clustersByIndexes);
                }

                cannotBeMovedIndexes.add(k);
                clustersByIndexes.sort(Comparator.comparingInt((ToIntFunction<List>) List::size).reversed());

            });
        }
//        log.info("'clustersByIndexes={}'", clustersByIndexes);

        return createIdsBasedClusters(clustersByIndexes, createIndexIdMap(kMeansRows));
    }

    public List<ShortestPathRow> buildMatrixForShortestPath(List<String> ids, List<MapObject> mapObjects) {
//        log.info("'buildMatrixForShortestPath' params'{}, {}'", ids, mapObjects);

        MapObject start = mapObjects.stream()
                .filter(mo -> mo.getDescription().equals(MapObjectDescriptionValues.START.toString())).findAny().get();

        MapObject finish = mapObjects.stream()
                .filter(mo -> mo.getDescription().equals(MapObjectDescriptionValues.FINISH.toString())).findAny().get();

        List<MapObject> mapObjectFromCluster = mapObjects.stream()
                .filter(mo -> ids.contains(mo.getId())).collect(Collectors.toList());

        List<ShortestPathRow> shortestPathRows = new ArrayList<>();

        mapObjectFromCluster.forEach(mo -> {

            List<String> idsWithOutCurrent = getIdsWithOutCurrent(ids, mo.getId());
            idsWithOutCurrent.addAll(Arrays.asList(start.getId(), finish.getId()));
            List<Long> distances = getDistanceToOtherObjects(mo, idsWithOutCurrent);
            distances.add(ids.indexOf(mo.getId()), 0L);

            ShortestPathRow shortestPathRow = new ShortestPathRow(mo.getId());
            shortestPathRow.getValue().addAll(distances);
            shortestPathRows.add(shortestPathRow);
        });

        ShortestPathRow startRow = new ShortestPathRow(start.getId());
        ArrayList<String> idsForStart = new ArrayList<>(ids);
        idsForStart.add(finish.getId());
        List<Long> startDistances = getDistanceToOtherObjects(start, idsForStart);
        startDistances.add(startDistances.size() - 1, 0L);
        startRow.setValue(startDistances);
        startRow.setStart(true);
        shortestPathRows.add(startRow);

        ShortestPathRow finishRow = new ShortestPathRow(finish.getId());
        ArrayList<String> idsForFinish = new ArrayList<>(ids);
        idsForFinish.add(start.getId());
        List<Long> finishDistances = getDistanceToOtherObjects(finish, idsForFinish);
        finishDistances.add(0L);
        finishRow.setValue(finishDistances);
        finishRow.setFinish(true);
        shortestPathRows.add(finishRow);

//        ArrayList<Long> distanceToStart = new ArrayList<>();
//        ArrayList<Long> distanceToFinish = new ArrayList<>();
//        ArrayList<ShortestPathRow> shortestPathRows = new ArrayList<>();
//
//        mapObjectFromCluster.forEach(mo -> {
//
//            ShortestPathRow shortestPathRow = new ShortestPathRow(mo.getId());
//
//            mo.getStartRoutes().forEach(route -> {
//
//                if (route.getFinish().getDescription().equals(MapObjectDescriptionValues.CONTAINER.toString())) {
//                    shortestPathRow.getValue().add(route.getLength());
//                } else if (route.getFinish().getDescription().equals(MapObjectDescriptionValues.START.toString())) {
//                    distanceToStart.add(route.getLength());
//                } else if (route.getFinish().getDescription().equals(MapObjectDescriptionValues.FINISH.toString())) {
//                    distanceToFinish.add(route.getLength());
//                }
//            });
//
//            shortestPathRows.add(shortestPathRow);
//        });
        return shortestPathRows;
    }

    private Task createTask(List<String> pathIds, String companyId, Map<String, Integer> duplications, String containerValue) {
        List<TaskPoint> taskPoints = new ArrayList<>();

        for (int i = 1; i < pathIds.size(); i++) {

            TaskPoint taskPoint = new TaskPoint();

            if (i == 1) {
                taskPoint.setType(MapObjectDescriptionValues.START);
            } else if (i == pathIds.size() - 1) {
                taskPoint.setType(MapObjectDescriptionValues.FINISH);
            } else {
                taskPoint.setType(MapObjectDescriptionValues.CONTAINER);
            }

            if (i != pathIds.size() - 1) {
                taskPoint.setAmount(duplications.get(pathIds.get(i)));
            } else {
                taskPoint.setAmount(0);
            }

            taskPoint.setRoute(routeRepository.findByStartIdAndFinishId(pathIds.get(i - 1), pathIds.get(i)));

            taskPoints.add(taskPoint);
        }

        Task task = new Task();
        task.setActive(true);
        task.setCompanyId(companyId);
        task.setPoints(taskPoints);
        task.setContainerValue(containerValue);

        return task;
    }


    private Map<String, List<String>> createRequestByType(Collection<Container> allByOwnerId) {

        Map<String, List<String>> requestsByType = new HashMap<>();
        for (ContainerValues containerValues : ContainerValues.values()) {
            requestsByType.put(containerValues.toString(), new ArrayList<>());
        }

        allByOwnerId.forEach(container ->
                container.getDetails().forEach(dt -> {
                    if (dt.isFull() && !dt.isPending()) {
                        requestsByType.get(dt.getType().toString()).addAll(Collections.nCopies(dt.getAmount(), container.getId()));
                        dt.setPending(true);
                    }
                }));


        mapObjectRepository.save(allByOwnerId);
        log.debug("'createRequestByType' response '{}'", requestsByType);

        return requestsByType;
    }

    private List<List<Integer>> createIndexBasedClusters(List<List<String>> clustersByIds, Map<String, Integer> indexIdMap) {

        return clustersByIds
                .stream()
                .map(listIds -> listIds.stream().map(indexIdMap::get).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<List<String>> createIdsBasedClusters(List<List<Integer>> clustersByIndexed, Map<Integer, String> idIndexMap) {

        return clustersByIndexed
                .stream()
                .map(listIds -> listIds.stream().map(idIndexMap::get).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private Map<Integer, Pair<Integer, Long>> orderDistances(Map<Integer, Pair<Integer, Long>> nonOrdered) {

        return nonOrdered.entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    private void moveElementToAnotherCluster(List<Integer> currentCluster, Integer elementIndex,
                                             Integer nearestNeighborIndex, List<List<Integer>> clustersByIndexes) {
//        log.info("'moveElementToAnotherCluster' params '{}, {}, {}, {}", currentCluster, elementIndex, nearestNeighborIndex, clustersByIndexes);

        currentCluster.remove(elementIndex);

        List<Integer> newCluster = clustersByIndexes
                .stream().filter(cluster -> cluster.contains(nearestNeighborIndex)).findAny().get();
        newCluster.add(elementIndex);
    }

    private Map<Integer, String> createIndexIdMap(List<KMeansRow> matrix) {

        Map<Integer, String> indexIdMap = new HashMap<>();

        for (int i = 0; i < matrix.size(); i++) {
            indexIdMap.put(i, matrix.get(i).getId());
        }

        return indexIdMap;
    }

    private Map<String, Integer> createIdIndexMap(List<KMeansRow> matrix) {

        Map<String, Integer> idIndexMap = new HashMap<>();

        for (int i = 0; i < matrix.size(); i++) {
            idIndexMap.put(matrix.get(i).getId(), i);
        }

        return idIndexMap;
    }

    private List<String> getIdsWithOutCurrent(List<String> ids, String excludedId) {
        List<String> idsWithoutCurrent = new ArrayList<>(ids);
        while (idsWithoutCurrent.contains(excludedId))
            idsWithoutCurrent.remove(excludedId);
        return idsWithoutCurrent;
    }

    public List<List<String>> deleteDuplicatesFromCluster(List<List<String>> clustersWithDuplicates) {
        return clustersWithDuplicates
                .stream()
                .map(list -> new ArrayList<>(list.stream().map(id -> id.split("/")[0]).collect(Collectors.toSet())))
                .collect(Collectors.toList());
    }

    public Map<String, Integer> calculateDuplications(List<List<String>> clustersWithDuplicates) {
        Map<String, Integer> calculatedMap = new HashMap<>();
        clustersWithDuplicates.forEach(list -> list.forEach(idUUID -> {
            String id = idUUID.split("/")[0];
            if (calculatedMap.containsKey(id)) {
                calculatedMap.put(id, calculatedMap.get(id) + 1);
            } else {
                calculatedMap.put(id, 1);
            }
        }));
        return calculatedMap;
    }
}
