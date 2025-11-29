package com.frameflow.exception;

import com.frameflow.dto.response.ConversionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ConversionResponse> handleGeneralException(Exception ex) {
        ConversionResponse response = new ConversionResponse();
        response.setTargetCode("// 转换过程中发生错误: " + ex.getMessage());
        response.setConversionScore(0);
        
        // 添加错误信息到issues
        ConversionResponse.Issue issue = new ConversionResponse.Issue();
        issue.setType("ERROR");
        issue.setMessage(ex.getMessage());
        response.setIssues(java.util.Collections.singletonList(issue));
        
        // 设置元数据
        ConversionResponse.Metadata metadata = new ConversionResponse.Metadata();
        metadata.setExecutionTime(0);
        metadata.setComplexity("SIMPLE");
        response.setMetadata(metadata);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}