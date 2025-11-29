package com.frameflow.service.impl;

import com.frameflow.converter.core.ConversionEngine;
import com.frameflow.dto.request.ConversionRequest;
import com.frameflow.dto.response.ConversionResponse;
import com.frameflow.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversionServiceImpl implements ConversionService {
    
    @Autowired
    private ConversionEngine conversionEngine;
    
    @Override
    public ConversionResponse convert(String sourceCode, String sourceFramework, String targetFramework, ConversionRequest.ConversionOptions options) {
        // 执行转换逻辑
        return conversionEngine.convert(sourceCode, sourceFramework, targetFramework, options);
    }
}