package com.kpi.diploma.smartroads.service.primary.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.kpi.diploma.smartroads.model.document.map.Container;
import com.kpi.diploma.smartroads.model.document.map.MapObject;
import com.kpi.diploma.smartroads.model.document.map.Route;
import com.kpi.diploma.smartroads.model.dto.task.KMeansRow;
import com.kpi.diploma.smartroads.model.util.title.value.ContainerValues;
import com.kpi.diploma.smartroads.repository.ContainerRepository;
import com.kpi.diploma.smartroads.repository.MapObjectRepository;
import com.kpi.diploma.smartroads.repository.RouteRepository;
import com.kpi.diploma.smartroads.service.primary.TaskService;
import com.kpi.diploma.smartroads.service.util.ConversionService;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private final ContainerRepository containerRepository;

    private final RouteRepository routeRepository;

    private final MapObjectRepository mapObjectRepository;

    public TaskServiceImpl(ContainerRepository containerRepository, RouteRepository routeRepository, MapObjectRepository mapObjectRepository) {
        this.containerRepository = containerRepository;
        this.routeRepository = routeRepository;
        this.mapObjectRepository = mapObjectRepository;
    }

    @Override
    public void createTasksForAllServices() {

    }

    @Override
    public JsonNode createTaskForService(String serviceId) {
        log.info("'createTaskForService' invoked with params'{}'", serviceId);

//        List<Container> allByOwnerId = containerRepository.findAllByOwnerId(serviceId);
//        log.info("'allByOwnerId={}'", allByOwnerId);

        List<MapObject> allByOwnerId = mapObjectRepository.findAllByOwnerId(serviceId);
        log.info("'allByOwnerId={}'", allByOwnerId);

        Map<String, List<String>> requestsByType = new HashMap<>();
        for (ContainerValues containerValues : ContainerValues.values()) {
            requestsByType.put(containerValues.toString(), new ArrayList<>());
        }

//        allByOwnerId.forEach(container -> container.getDetails().forEach(dt -> {
//            if (dt.isFull()) {
//                requestsByType.get(dt.getType()).addAll(Collections.nCopies(dt.getAmount(), container.getId()));
//            }
//        }));
//
//        Map<String, Container> containterMap = allByOwnerId
//                .stream().collect(Collectors.toMap(Container::getId, container -> container));
//
//        requestsByType.forEach((type, ids) -> {
//
//            List<KMeansRow> kMeansRows = new ArrayList<>();
//            ids.forEach(id -> {
//
//                List<String> idsWithoutCurrent = getIdsWithOutCurrent(ids, id);
//
//                List<Long> distanceToOtherObjects = getDistanceToOtherObjects(containterMap.get(id), idsWithoutCurrent);
//
//                int firstIndex = ids.indexOf(id);
//                int lastIndex = ids.lastIndexOf(id);
//
//                distanceToOtherObjects.addAll(firstIndex, Collections.nCopies(lastIndex - firstIndex + 1, 0L));
//
//                KMeansRow row = new KMeansRow(id, distanceToOtherObjects);
//                kMeansRows.add(row);
//            });
//            log.info("'type={}, kMeansRows={}", type, kMeansRows);
//
//
//            // TODO: 03/05/18 send request for k means
//        });

        return ConversionService.convertToJsonNode(requestsByType);
    }

    private List<Long> getDistanceToOtherObjects(Container mapObject, List<String> otherIds) {
//        log.info("'getDistanceToOtherObjects' invoked with params'{}, {}'", mapObject, otherIds);
//        log.info("'mapObject.getStartRoutes().size()={}'", mapObject.getStartRoutes().size());
//        log.info("'mapObject.getStartRoutes()={}'", mapObject.getStartRoutes());

        ArrayList<Long> response = new ArrayList<>(Collections.nCopies(otherIds.size(), 0L));

        List<Route> byStartId = routeRepository.findAllByStartId(mapObject.getId());
//        log.info("'byStartId={}'", byStartId);

        byStartId.forEach(route -> {
//            log.info("'route={}'", route);
//            log.info("'routeSFinish={}'", route.getFinish());

            String id = route.getFinish().getId();

            if (otherIds.contains(id)) {
                int firstIndex = otherIds.indexOf(id);
                int lastIndex = otherIds.lastIndexOf(id);
//                log.info("'firstIndex={}, lastIndex={}'", firstIndex, lastIndex);

                for (int i = firstIndex; i < lastIndex + 1; i++) {
                    response.set(i, route.getLength());

                }
            }

        });
//        log.info("'response={}'", response);

        return response;
    }


    public List<List<String>> normalizeKMeans(Integer clusterSize, List<List<String>> clustersByIds, List<KMeansRow> kMeansRows) {

        List<Integer> cannotBeMovedIndexes = new ArrayList<>();

        List<List<Integer>> clustersByIndexes = createIndexBasedClusters(clustersByIds, createIdIndexMap(kMeansRows));
        clustersByIndexes.sort(Comparator.comparingInt((ToIntFunction<List>) List::size).reversed());
        log.info("'clustersByIndexes={}'", clustersByIndexes);

        while (clustersByIndexes.get(0).size() > clusterSize) {

            List<Integer> currentClusterIndexes = clustersByIndexes.get(0);
            log.info("'currentClusterIndexes={}'", currentClusterIndexes);

            Map<Integer, Pair<Integer, Long>> currentClusterDistances = new HashMap<>();
            for (Integer id : currentClusterIndexes) {
                currentClusterDistances.put(id, new Pair<>(-1, Long.MAX_VALUE));
            }

            for (Integer currentClusterIndex : currentClusterIndexes) {
                for (int i = 0; i < kMeansRows.size(); i++) {
                    if (currentClusterIndex != i && !cannotBeMovedIndexes.contains(i) && !currentClusterIndexes.contains(i)) {

                        Long avgDist = (kMeansRows.get(currentClusterIndex).getValue().get(i) + kMeansRows.get(i).getValue().get(currentClusterIndex)) / 2;
                        log.info("i={}, j={}, oldDist={}, newDist={}", currentClusterIndex, i, currentClusterDistances.get(currentClusterIndex), avgDist);

                        if (avgDist < currentClusterDistances.get(currentClusterIndex).getValue()) {
                            currentClusterDistances.put(currentClusterIndex, new Pair<>(i, avgDist));
                        }
                    }
                }
            }
            log.info("'currentClusterDistances={}'", currentClusterDistances);

            Map<Integer, Pair<Integer, Long>> orderedCurrentClusterDistances = orderDistances(currentClusterDistances);
            log.info("'orderedCurrentClusterDistances={}'", orderedCurrentClusterDistances);

            orderedCurrentClusterDistances.forEach((k, v) -> {

                if (currentClusterIndexes.size() > clusterSize) {
                    moveElementToAnotherCluster(currentClusterIndexes, k, v.getKey(), clustersByIndexes);
                }

                cannotBeMovedIndexes.add(k);
                clustersByIndexes.sort(Comparator.comparingInt((ToIntFunction<List>) List::size).reversed());

            });
        }
        log.info("'cannotBeMovedIndexes after normalization={}'", clustersByIndexes);

        return createIdsBasedClusters(clustersByIndexes, createIndexIdMap(kMeansRows));
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
}
