package com.frameflow.converter.core;

import com.frameflow.converter.framework.FrameworkConverterFactory;
import com.frameflow.dto.request.ConversionRequest;
import com.frameflow.dto.response.ConversionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversionEngine {
    
    @Autowired
    private FrameworkConverterFactory frameworkConverterFactory;
    
    public ConversionResponse convert(String sourceCode, String sourceFramework, String targetFramework, ConversionRequest.ConversionOptions options) {
        // 使用框架转换器工厂来选择合适的转换器
        return frameworkConverterFactory.convert(sourceCode, sourceFramework, targetFramework, options);
    }
}