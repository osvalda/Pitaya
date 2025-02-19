package io.github.osvalda.pitaya.util;

import io.github.osvalda.pitaya.models.AreaWiseCoverageObject;
import io.github.osvalda.pitaya.models.CoverageObject;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class PitayaMapArrangeUtility {

    /**
     * Rearranges the given coverage map by the endpoint's areas. The keys are the areas and the values are
     * {@link CoverageObject} types.
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
     * {@link AreaWiseCoverageObject} types.
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
                areaWiseCoverages.get(endpoint.getArea()).increaseCoverage(endpoint.getTestCases().size(), endpoint.isIgnored());
            } else {
                areaWiseCoverages.put(endpoint.getArea(), new AreaWiseCoverageObject(endpoint.getTestCases().size(), endpoint.isIgnored()));
            }
        });
        return areaWiseCoverages;
    }

    /**
     * Counts the endpoints with at least one test case.
     *
     * @param coverages the initial coverage map
     * @return the number of endpoints with at least one corresponding test case
     */
    public static int countCoveredEndpoints(Map<String, CoverageObject> coverages) {
        return Math.toIntExact(coverages.values().stream().filter(endpoint ->
                        !endpoint.getTestCases().isEmpty() && !endpoint.isIgnored())
                .count());
    }

    /**
     * Calculates the average coverage by area
     *
     * @param areaWise the area based coverage results
     * @return average coverage percentage by area
     */
    public static int calculateAverageCoveragePercentage(Map<String, AreaWiseCoverageObject> areaWise) {
        return (int)Math.round(areaWise.values()
                .stream()
                .collect(Collectors.summarizingDouble(act -> ((double) act.getCoveredEndpoints() / act.getAllEndpointsWithoutIgnored()) * 100d))
                .getAverage());
    }

    /**
     * Counts the fully covered areas. Fully covered means that all the belonging endpoints are individually covered.
     *
     * @param coverages the initial coverage map
     * @return the number of areas with full coverage
     */
    public static int[] countCoveredAreas(Map<String, CoverageObject> coverages) {
        Map<String, List<CoverageObject>> areaWise = arrangeEndpointsByAreas(coverages);
        int[] resultArray = {0, 0, 0};

        areaWise.keySet().forEach(actAreaName -> {
            resultArray[processAreaList(areaWise.get(actAreaName))] += 1;
        });

        return resultArray;
    }

    /*
        0 - not covered at all
        1 - at least one endpoint is covered but not all
        2 - all endpoints are covered
     */
    private int processAreaList(List<CoverageObject> endpoints) {
        int result = 0;

        for (CoverageObject act : endpoints) {
            if (!act.getTestCases().isEmpty()) {
                result = 2;
            } else {
                if (result == 2) {
                    return 1;
                }
            }
        }

        return result;
    }
}
