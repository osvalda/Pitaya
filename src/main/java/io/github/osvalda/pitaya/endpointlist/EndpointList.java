package io.github.osvalda.pitaya.endpointlist;

import io.github.osvalda.pitaya.models.CoverageObject;

import java.util.Map;

public interface EndpointList {

    Map<String, CoverageObject> processEndpointListFile(String fileName);

}
