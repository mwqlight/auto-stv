package com.frameflow.dto.request;

import lombok.Data;

@Data
public class ConversionRequest {
    private String sourceCode;
    private String sourceFramework;
    private String targetFramework;
    private ConversionOptions options;
    
    @Data
    public static class ConversionOptions {
        private boolean preserveComments;
        private boolean convertStyles;
        private boolean advancedOptimizations;
    }
}