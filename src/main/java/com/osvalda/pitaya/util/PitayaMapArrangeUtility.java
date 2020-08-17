package com.osvalda.pitaya.util;

import com.osvalda.pitaya.models.AreaWiseCoverageObject;
import com.osvalda.pitaya.models.CoverageObject;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class PitayaMapArrangeUtility {

    /**
     * Rearranges the given coverage map by the endpoint's areas. The keys are the areas and the values are
     * {@link com.osvalda.pitaya.models.CoverageObject} types.
     *
     * @param coverages the initial coverage map
     * @return a rearranged coverage map
     *
     * @author Akos Osvald
     */
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

    /**
     * Collects the covered endpoints by areas and stores them in
     * {@link com.osvalda.pitaya.models.AreaWiseCoverageObject} types.
     *
     * @param coverages the initial coverage map
     * @return a map with AreaWiseEndpoint vales and areas as keys
     *
     * @author Akos Osvald
     */
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
