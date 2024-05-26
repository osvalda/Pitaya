package io.github.osvalda.pitaya.models;

import lombok.Getter;

/**
 * Represents covered and uncovered endpoints of an area.
 *
 * @author Akos Osvald
 */
public class AreaWiseCoverageObject {

    @Getter
    private int coveredEndpoints;
    @Getter
    private int allEndpoints;
    @Getter
    private int ignoredEndpoints = 0;

    /**
     * Creates a new area wise coverage object with first endpoint
     *
     * @param covered how many test case cover the first endpoint
     */
    public AreaWiseCoverageObject(int covered, boolean ignored) {
        allEndpoints = 1;
        if(covered > 0) {
            this.coveredEndpoints = 1;
        } if (ignored) {
            ignoredEndpoints += 1;
        }
    }

    /**
     * Overwritten default constructor.
     * The first endpoint is not yet covered by any tests.
     */
    public AreaWiseCoverageObject() {
        allEndpoints = 1;
    }

    /**
     * Increases the number of endpoints in area and sets its coverage.
     *
     * @param covered how many test case cover the newly added endpoint
     */
    public void increaseCoverage(int covered, boolean ignored) {
        allEndpoints += 1;
        if(covered > 0) {
            this.coveredEndpoints += 1;
        } if (ignored) {
            ignoredEndpoints += 1;
        }
    }

    /**
     * Returns the number of uncovered endpoints of the area.
     */
    public int getUncoveredEndpointNum() {
        return allEndpoints - coveredEndpoints - ignoredEndpoints;
    }
}
