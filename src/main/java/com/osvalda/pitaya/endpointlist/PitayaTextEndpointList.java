package com.osvalda.pitaya.endpointlist;

import com.osvalda.pitaya.models.CoverageObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PitayaTextEndpointList implements EndpointList {

    @Override
    public Map<String, CoverageObject> processEndpointListFile(String fileName) {
        Map<String, CoverageObject> endpoints = new HashMap<>();
        try {
            File file = FileUtils.getFile((Objects.requireNonNull(getClass().getClassLoader()
                    .getResource(fileName)))
                    .getPath());

            if (FileUtils.sizeOf(file) == 0) {
                throw new IllegalStateException("The endpoint input file is empty!");
            }
            FileUtils.readLines(file, StandardCharsets.UTF_8).forEach(fileLine -> {
                if(!fileLine.isEmpty()) {
                    String[] endpointLine = StringUtils.splitByWholeSeparator(fileLine, ", ");
                    if(endpointLine.length == 2) {
                        endpoints.put(endpointLine[0], new CoverageObject(endpointLine[1], endpointLine[0]));
                    }
                }
            });
            if (endpoints.isEmpty()) {
                throw new IllegalStateException("The endpoint input file has wrong formatting!");
            }
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException("Opening the endpoint input list file (" + fileName + ") has failed!", e);
        }
        return endpoints;
    }
}
