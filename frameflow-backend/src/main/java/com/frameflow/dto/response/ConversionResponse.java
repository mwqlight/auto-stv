package com.frameflow.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class ConversionResponse {
    private String targetCode;
    private int conversionScore; // 0-100
    private List<Issue> issues;
    private Metadata metadata;
    
    @Data
    public static class Issue {
        private String type; // WARNING, ERROR, INFO
        private String message;
        private int[] lineNumbers;
        private String suggestion;
    }
    
    @Data
    public static class Metadata {
        private long executionTime;
        private String complexity; // SIMPLE, MODERATE, COMPLEX
    }
}