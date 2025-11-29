package com.frameflow.service;

import com.frameflow.dto.request.ConversionRequest;
import com.frameflow.dto.response.ConversionResponse;

public interface ConversionService {
    ConversionResponse convert(String sourceCode, String sourceFramework, String targetFramework, ConversionRequest.ConversionOptions options);
}