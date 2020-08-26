package io.github.osvalda.pitaya.endpointlist;

import io.github.osvalda.pitaya.models.CoverageObject;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.OpenAPIV3Parser;

import java.util.HashMap;
import java.util.Map;

public class SwaggerV3EndpointList implements EndpointList {

    @Override
    public Map<String, CoverageObject> processEndpointListFile(String fileName) {
        Map<String, CoverageObject> endpoints = new HashMap<>();
        OpenAPI openAPI = new OpenAPIV3Parser().read(fileName);

        if (openAPI != null) {
            Paths paths = openAPI.getPaths();
            paths.keySet().forEach(endpoint -> processPathItem(paths.get(endpoint), endpoint, endpoints));
        }

        return endpoints;
    }

    private void processPathItem(PathItem item, String endpoint, Map<String, CoverageObject> endpoints) {
        for(PathItem.HttpMethod method : item.readOperationsMap().keySet()) {
            String area = String.join(", ", item.readOperationsMap().get(method).getTags());
            endpoints.put(method + " " + endpoint, new CoverageObject(area, method + " " + endpoint));
        }
    }
}
