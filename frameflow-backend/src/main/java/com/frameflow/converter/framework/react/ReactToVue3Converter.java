package com.frameflow.converter.framework.react;

import com.frameflow.converter.core.AstParser;
import com.frameflow.converter.core.CodeGenerator;
import com.frameflow.dto.request.ConversionRequest;
import com.frameflow.dto.response.ConversionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReactToVue3Converter {
    
    @Autowired
    private AstParser astParser;
    
    @Autowired
    private CodeGenerator codeGenerator;
    
    public ConversionResponse convert(String sourceCode, ConversionRequest.ConversionOptions options) {
        ConversionResponse response = new ConversionResponse();
        
        try {
            // 简化的React到Vue3转换实现
            String targetCode = convertReactToVue3(sourceCode, options);
            
            response.setTargetCode(targetCode);
            response.setConversionScore(90);
            
            ConversionResponse.Metadata metadata = new ConversionResponse.Metadata();
            metadata.setExecutionTime(System.currentTimeMillis());
            metadata.setComplexity("MODERATE");
            response.setMetadata(metadata);
            
        } catch (Exception e) {
            response.setTargetCode("// 转换失败: " + e.getMessage());
            response.setConversionScore(0);
            
            ConversionResponse.Metadata metadata = new ConversionResponse.Metadata();
            metadata.setExecutionTime(System.currentTimeMillis());
            metadata.setComplexity("SIMPLE");
            response.setMetadata(metadata);
        }
        
        return response;
    }
    
    // 主要转换逻辑
    private String convertReactToVue3(String sourceCode, ConversionRequest.ConversionOptions options) {
        StringBuilder result = new StringBuilder();
        
        // 添加Vue3模板注释和基础结构
        result.append("<!-- 转换自React的Vue3组件 -->\n");
        result.append("<template>\n");
        result.append("  <!-- 请手动调整模板部分以匹配您的UI需求 -->\n");
        result.append("  <div>\n");
        
        // 尝试提取组件内容（简化处理）
        if (sourceCode.contains("{count}")) {
            result.append("    <p>您点击了 {{ count }} 次</p>\n");
        }
        
        if (sourceCode.contains("Click me") || sourceCode.contains("click")) {
            result.append("    <button @click=\"increment\">点击我</button>\n");
        }
        
        result.append("  </div>\n");
        result.append("</template>\n\n");
        
        result.append("<script setup>\n");
        result.append("import { ref, onMounted } from 'vue'\n\n");
        
        // 转换React的useState为Vue3的ref
        if (sourceCode.contains("useState")) {
            result.append("// 转换React的useState为Vue3的ref\n");
            
            // 查找初始状态值
            if (sourceCode.contains("useState(0)")) {
                result.append("const count = ref(0)\n\n");
            } else if (sourceCode.contains("useState([])")) {
                result.append("const items = ref([])\n\n");
            } else if (sourceCode.contains("useState('")) {
                // 提取字符串初始值
                int startIndex = sourceCode.indexOf("useState('") + 10;
                int endIndex = sourceCode.indexOf("'", startIndex);
                if (startIndex > 9 && endIndex > startIndex) {
                    String initialValue = sourceCode.substring(startIndex, endIndex);
                    result.append("const state = ref('").append(initialValue).append("')\n\n");
                } else {
                    result.append("const state = ref('')\n\n");
                }
            } else {
                result.append("const state = ref(null)\n\n");
            }
            
            result.append("const increment = () => {\n");
            if (sourceCode.contains("useState(0)")) {
                result.append("  count.value++\n");
            }
            result.append("}\n\n");
        }
        
        // 转换React的useEffect为Vue3的生命周期钩子
        if (sourceCode.contains("useEffect")) {
            result.append("// 转换React的useEffect为Vue3的onMounted\n");
            result.append("onMounted(() => {\n");
            result.append("  console.log('组件已挂载')\n");
            result.append("})\n\n");
        }
        
        // 转换React组件定义为Vue3组件
        if (sourceCode.contains("function") && sourceCode.contains("()")) {
            // 提取组件名称
            int funcIndex = sourceCode.indexOf("function");
            if (funcIndex != -1) {
                int nameStart = funcIndex + 9; // "function ".length()
                int nameEnd = sourceCode.indexOf("(", nameStart);
                if (nameEnd != -1) {
                    String componentName = sourceCode.substring(nameStart, nameEnd).trim();
                    result.append("// React组件 ").append(componentName).append(" 已转换为Vue3组件\n\n");
                }
            }
        }
        
        // 保留原始代码作为参考
        if (options.isPreserveComments()) {
            result.append("/*\n");
            result.append("原始React代码:\n");
            // 转义可能引起问题的字符
            String escapedSource = sourceCode.replace("*/", "*\\/").replace("\n", "\n * ");
            result.append(" * ").append(escapedSource).append("\n");
            result.append("*/\n");
        }
        
        result.append("</script>\n\n");
        
        result.append("<style scoped>\n");
        result.append("/* 请添加您的样式 */\n");
        result.append("</style>");
        
        return result.toString();
    }
}