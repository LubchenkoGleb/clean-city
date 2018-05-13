package com.kpi.diploma.smartroads.service.util.google;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;
import com.kpi.diploma.smartroads.model.util.data.GoogleRoute;
import com.kpi.diploma.smartroads.model.util.exception.GoogleBouldRouteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoogleService {

    @Value("${google.apiKey}")
    private String googleApiKey;

    public GoogleRoute buildRoute(LatLng origin, LatLng destination) {
        GeoApiContext context = new GeoApiContext.Builder().apiKey(googleApiKey).build();
        log.info("origin={}, destination={}", origin, destination);

        try {

            DirectionsResult result = DirectionsApi.newRequest(context)
                    .origin(origin)
                    .destination(destination)
                    .await();

            DirectionsRoute route = result.routes[0];
            log.info("route={}, {}", route, route.overviewPolyline.decodePath());

            Long distance = 0L;
            for (DirectionsLeg leg : route.legs) {
                distance += leg.distance.inMeters;
            }

            return new GoogleRoute(distance, route.overviewPolyline.getEncodedPath());

        } catch (Exception e) {
            throw new GoogleBouldRouteException(e.getMessage());
        }
    }
}
