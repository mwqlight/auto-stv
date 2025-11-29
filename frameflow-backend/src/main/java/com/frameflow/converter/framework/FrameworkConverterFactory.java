package com.frameflow.converter.framework;

import com.frameflow.converter.framework.react.ReactToVue3Converter;
import com.frameflow.converter.framework.vue.Vue2ToVue3Converter;
import com.frameflow.dto.request.ConversionRequest;
import com.frameflow.dto.response.ConversionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FrameworkConverterFactory {
    
    @Autowired
    private Vue2ToVue3Converter vue2ToVue3Converter;
    
    @Autowired
    private ReactToVue3Converter reactToVue3Converter;
    
    public ConversionResponse convert(String sourceCode, String sourceFramework, String targetFramework, ConversionRequest.ConversionOptions options) {
        try {
            // 根据源框架和目标框架选择合适的转换器
            if ("Vue2".equals(sourceFramework) && "Vue3".equals(targetFramework)) {
                return vue2ToVue3Converter.convert(sourceCode, options);
            } else if ("React".equals(sourceFramework) && "Vue3".equals(targetFramework)) {
                return reactToVue3Converter.convert(sourceCode, options);
            } else if ("Vue3".equals(sourceFramework) && "Vue2".equals(targetFramework)) {
                // 添加Vue3到Vue2的反向转换支持（如果存在相应的转换器）
                ConversionResponse response = new ConversionResponse();
                response.setTargetCode("// Vue3到Vue2的转换暂未实现");
                response.setConversionScore(0);
                
                ConversionResponse.Metadata metadata = new ConversionResponse.Metadata();
                metadata.setExecutionTime(System.currentTimeMillis());
                metadata.setComplexity("SIMPLE");
                response.setMetadata(metadata);
                
                return response;
            } else if (sourceFramework.equals(targetFramework)) {
                // 如果源框架和目标框架相同，则直接返回源代码
                ConversionResponse response = new ConversionResponse();
                response.setTargetCode(sourceCode);
                response.setConversionScore(100); // 完全匹配
                
                ConversionResponse.Metadata metadata = new ConversionResponse.Metadata();
                metadata.setExecutionTime(0L); // 几乎没有执行时间
                metadata.setComplexity("SIMPLE");
                response.setMetadata(metadata);
                
                return response;
            } else {
                // 其他不支持的转换
                ConversionResponse response = new ConversionResponse();
                response.setTargetCode("// 当前不支持从 " + sourceFramework + " 到 " + targetFramework + " 的转换\n// 支持的转换:\n// 1. Vue2 -> Vue3\n// 2. React -> Vue3");
                response.setConversionScore(0);
                
                ConversionResponse.Metadata metadata = new ConversionResponse.Metadata();
                metadata.setExecutionTime(System.currentTimeMillis());
                metadata.setComplexity("SIMPLE");
                response.setMetadata(metadata);
                
                return response;
            }
        } catch (Exception e) {
            // 处理转换过程中的异常
            ConversionResponse response = new ConversionResponse();
            response.setTargetCode("// 转换过程中发生错误: " + e.getMessage() + "\n// 请检查源代码是否符合" + sourceFramework + "语法规范");
            response.setConversionScore(0);
            
            ConversionResponse.Metadata metadata = new ConversionResponse.Metadata();
            metadata.setExecutionTime(System.currentTimeMillis());
            metadata.setComplexity("SIMPLE");
            response.setMetadata(metadata);
            
            // 添加错误信息到issues
            ConversionResponse.Issue issue = new ConversionResponse.Issue();
            issue.setType("ERROR");
            issue.setMessage(e.getMessage());
            issue.setSuggestion("请检查源代码是否符合" + sourceFramework + "语法规范");
            response.setIssues(java.util.Collections.singletonList(issue));
            
            return response;
        }
    }
}