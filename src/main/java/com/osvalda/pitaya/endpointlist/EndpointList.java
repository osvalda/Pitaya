package com.osvalda.pitaya.endpointlist;

import com.osvalda.pitaya.models.CoverageObject;

import java.util.Map;

public interface EndpointList {

    Map<String, CoverageObject> processEndpointListFile(String fileName);

}
