package io.github.osvalda.pitaya.endpointlist;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EndpointListProcessorFactory {

    public static EndpointList createEndpointListProcessor(String endpointList) {
        if(endpointList.endsWith("txt")) {
            return new PitayaTextEndpointList();
        } else {
            return new SwaggerV3EndpointList();
        }
    }
}
