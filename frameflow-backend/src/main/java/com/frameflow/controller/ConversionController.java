package com.frameflow.controller;

import com.frameflow.dto.request.ConversionRequest;
import com.frameflow.dto.response.ConversionResponse;
import com.frameflow.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/convert")
public class ConversionController {

    @Autowired
    private ConversionService conversionService;

    // 1. 框架转换API (单文件)
    @PostMapping("/single")
    public ResponseEntity<ConversionResponse> convertSingle(
            @RequestBody ConversionRequest request) {
        try {
            // 执行转换逻辑
            ConversionResponse result = conversionService.convert(
                    request.getSourceCode(),
                    request.getSourceFramework(),
                    request.getTargetFramework(),
                    request.getOptions()
            );
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 创建错误响应
            ConversionResponse errorResponse = new ConversionResponse();
            errorResponse.setTargetCode("// 转换请求处理失败: " + e.getMessage());
            errorResponse.setConversionScore(0);
            
            ConversionResponse.Metadata metadata = new ConversionResponse.Metadata();
            metadata.setExecutionTime(System.currentTimeMillis());
            metadata.setComplexity("SIMPLE");
            errorResponse.setMetadata(metadata);
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    // 2. 批量转换API
    @PostMapping("/batch")
    public ResponseEntity<String> convertBatch(
            @RequestParam("sourceDir") MultipartFile sourceZip,
            @RequestParam("sourceFramework") String sourceFramework,
            @RequestParam("targetFramework") String targetFramework) {
        try {
            // 处理ZIP文件
            // 执行批量转换
            // 生成转换报告
            // 返回ZIP下载链接
            return ResponseEntity.ok("Batch conversion initiated");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("批量转换处理失败: " + e.getMessage());
        }
    }
    
    // 3. 框架支持查询
    @GetMapping("/frameworks")
    public ResponseEntity<String> getSupportedFrameworks() {
        try {
            // 返回支持的框架矩阵
            return ResponseEntity.ok("{\"frameworks\":[\"Vue2\",\"Vue3\",\"React\",\"Angular\",\"JSP/Servlet\",\"Flutter\"]}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\":\"获取框架信息失败: " + e.getMessage() + "\"}");
        }
    }
}