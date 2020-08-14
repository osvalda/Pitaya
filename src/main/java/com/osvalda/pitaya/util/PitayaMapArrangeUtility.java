package com.osvalda.pitaya.util;

import com.osvalda.pitaya.models.AreaWiseCoverageObject;
import com.osvalda.pitaya.models.CoverageObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PitayaMapArrangeUtility {

    private PitayaMapArrangeUtility() {
        throw new IllegalStateException("This is a utility class!");
    }

    public static Map<String, List<CoverageObject>> arrangeEndpointsByAreas(Map<String, CoverageObject> coverages) {
        Map<String, List<CoverageObject>> areaWiseEndpointMap = new HashMap<>();
        coverages.values().forEach(elem -> {
            List<CoverageObject> endpoints = new ArrayList<>();
            if (areaWiseEndpointMap.containsKey(elem.getArea())) {
                endpoints = areaWiseEndpointMap.get(elem.getArea());
            }
            endpoints.add(elem);
            areaWiseEndpointMap.put(elem.getArea(), endpoints);
        });
        return areaWiseEndpointMap;
    }

    public static Map<String, AreaWiseCoverageObject> collectAreaWiseEndpointDetails(Map<String, CoverageObject> coverages) {
        Map<String, AreaWiseCoverageObject> areaWiseCoverages = new HashMap<>();
        coverages.values().forEach(endpoint -> {
            if (areaWiseCoverages.containsKey(endpoint.getArea())) {
                areaWiseCoverages.get(endpoint.getArea()).increaseCoverage(endpoint.getTestCases().size());
            } else {
                areaWiseCoverages.put(endpoint.getArea(), new AreaWiseCoverageObject(endpoint.getTestCases().size()));
            }
        });
        return areaWiseCoverages;
    }
}
