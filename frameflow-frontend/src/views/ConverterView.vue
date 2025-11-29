<template>
  <div class="converter-platform">
    <!-- 全局粒子背景 -->
    <div class="particle-canvas"></div>
    
    <header class="platform-header">
      <h1 class="logo">
        FRAME<span class="accent">FLOW</span>
        <span class="subtitle">前端框架互转平台</span>
      </h1>
      <div class="mode-switch">
        <button 
          class="neon-button"
          :class="{ active: conversionMode === 'single' }" 
          @click="conversionMode = 'single'"
        >
          单文件转换
        </button>
        <button 
          class="neon-button"
          :class="{ active: conversionMode === 'batch' }" 
          @click="conversionMode = 'batch'"
        >
          批量项目转换
        </button>
      </div>
    </header>
    
    <!-- 支持的转换提示 -->
    <div class="supported-conversions">
      <p>✨ 支持的转换: Vue2 → Vue3, React → Vue3, Vue3 → Vue2 (实验性), 相同框架直接返回</p>
    </div>
    
    <main class="conversion-area" v-if="conversionMode === 'single'">
      <div class="source-panel neon-panel">
        <div class="panel-header">
          <h3>源代码</h3>
          <select class="neon-select" v-model="sourceFramework">
            <option value="Vue2">Vue2</option>
            <option value="Vue3">Vue3</option>
            <option value="React">React</option>
            <option value="Angular">Angular</option>
            <option value="JSP/Servlet">JSP/Servlet</option>
          </select>
        </div>
        <textarea 
          class="code-editor"
          v-model="sourceCode" 
          placeholder="请输入源代码..."
          @input="scheduleConversion"
        ></textarea>
        <div class="file-operations">
          <button class="neon-button" @click="useExample">使用示例</button>
        </div>
      </div>
      
      <div class="converter-control">
        <div class="conversion-button" @click="executeConversion">
          <span class="arrow-icon">→</span>
          <div class="glow-effect"></div>
        </div>
        <div class="conversion-options">
          <label class="neon-checkbox">
            <input type="checkbox" v-model="preserveComments"> 保留注释
          </label>
        </div>
      </div>
      
      <div class="target-panel neon-panel">
        <div class="panel-header">
          <h3>转换结果</h3>
          <select class="neon-select" v-model="targetFramework">
            <option value="Vue2">Vue2</option>
            <option value="Vue3">Vue3</option>
            <option value="React">React</option>
            <option value="Angular">Angular</option>
            <option value="JSP/Servlet">JSP/Servlet</option>
          </select>
        </div>
        <textarea class="code-editor" v-model="targetCode" readonly></textarea>
        <div class="output-actions">
          <button class="neon-button" @click="copyResult">复制结果</button>
        </div>
      </div>
    </main>
    
    <main class="batch-conversion-area neon-panel" v-else>
      <h3>批量项目转换</h3>
      <p>功能开发中...</p>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { convertCode } from '../api/conversion'

// 状态管理
const conversionMode = ref<'single' | 'batch'>('single')
const sourceCode = ref('')
const targetCode = ref('')
const sourceFramework = ref('Vue2')
const targetFramework = ref('Vue3')

// 转换选项
const preserveComments = ref(true)

// 转换定时器
let conversionTimer: number | null = null

// 延迟转换
const scheduleConversion = () => {
  if (conversionTimer) {
    clearTimeout(conversionTimer)
  }
  
  conversionTimer = window.setTimeout(() => {
    executeConversion()
  }, 500)
}

// 使用示例代码
const examples = {
  Vue2: `&lt;template&gt;
  &lt;div&gt;
    &lt;h1&gt;{{ message }}&lt;/h1&gt;
    &lt;button @click="handleClick"&gt;点击我&lt;/button&gt;
    &lt;p&gt;计数器: {{ count }}&lt;/p&gt;
  &lt;/div&gt;
&lt;/template&gt;

&lt;script&gt;
export default {
  data() {
    return {
      message: 'Hello Vue2!',
      count: 0
    }
  },
  methods: {
    handleClick() {
      this.message = 'Vue2 button clicked!';
      this.count++;
    }
  },
  mounted() {
    console.log('Vue2组件已挂载');
  }
}
&lt;/script&gt;`,
  Vue3: `&lt;template&gt;
  &lt;div&gt;
    &lt;h1&gt;{{ message }}&lt;/h1&gt;
    &lt;button @click="handleClick"&gt;点击我&lt;/button&gt;
    &lt;p&gt;计数器: {{ count }}&lt;/p&gt;
  &lt;/div&gt;
&lt;/template&gt;

&lt;script setup&gt;
import { ref, onMounted } from 'vue'

const message = ref('Hello Vue3!')
const count = ref(0)

const handleClick = () => {
  message.value = 'Vue3 button clicked!'
  count.value++
}

onMounted(() => {
  console.log('Vue3组件已挂载');
})
&lt;/script&gt;`,
  React: `import React, { useState, useEffect } from 'react';

function App() {
  const [message, setMessage] = useState('Hello React!');
  const [count, setCount] = useState(0);
  
  const handleClick = () => {
    setMessage('React button clicked!');
    setCount(count + 1);
  };
  
  useEffect(() => {
    console.log('React组件已挂载');
  }, []);

  return (
    &lt;div&gt;
      &lt;h1&gt;{message}&lt;/h1&gt;
      &lt;button onClick={handleClick}&gt;点击我&lt;/button&gt;
      &lt;p&gt;计数器: {count}&lt;/p&gt;
    &lt;/div&gt;
  );
}

export default App;
`,

};

const useExample = () => {
  sourceCode.value = examples[sourceFramework.value];
}

// 执行转换
const executeConversion = async () => {
  if (!sourceCode.value.trim()) {
    targetCode.value = ''
    return
  }
  
  try {
    const result = await convertCode({
      sourceCode: sourceCode.value,
      sourceFramework: sourceFramework.value,
      targetFramework: targetFramework.value,
      options: {
        preserveComments: preserveComments.value,
        convertStyles: true,
        advancedOptimizations: false
      }
    })
    
    targetCode.value = result.targetCode
    
    // 如果有错误或警告信息，显示给用户
    if (result.issues && result.issues.length > 0) {
      const errorMessages = result.issues.map(issue => `${issue.type}: ${issue.message}`).join('\n')
      console.warn('转换警告/错误:', errorMessages)
      // 可以考虑使用更友好的UI方式显示这些信息
    }
  } catch (error) {
    console.error('转换失败:', error)
    if (error.message && error.message.includes('Network Error')) {
      targetCode.value = '// 转换失败: 无法连接到转换服务\n// 请确保后端服务正在运行'
    } else {
      targetCode.value = '// 转换失败: ' + (error.message || '未知错误') + '\n// 请检查控制台获取更多详细信息'
    }
  }
}

// 复制结果
const copyResult = () => {
  navigator.clipboard.writeText(targetCode.value)
    .then(() => {
      alert('已复制到剪贴板')
    })
    .catch(err => {
      console.error('复制失败:', err)
    })
}

</script>

<style scoped>
.converter-platform {
  max-width: 1400px;
  margin: 0 auto;
  padding: 20px;
  position: relative;
}

.particle-canvas {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: 
    radial-gradient(circle at 10% 20%, rgba(0, 243, 255, 0.1) 0%, transparent 20%),
    radial-gradient(circle at 90% 80%, rgba(114, 9, 183, 0.1) 0%, transparent 20%);
  z-index: -1;
  pointer-events: none;
}

.platform-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding: 20px;
  background: rgba(10, 14, 23, 0.7);
  border: var(--border-primary);
  border-radius: 8px;
}

.supported-conversions {
  text-align: center;
  margin-bottom: 20px;
  padding: 10px;
  background: rgba(0, 30, 60, 0.5);
  border-radius: 10px;
  border: 1px solid rgba(0, 255, 234, 0.3);
  backdrop-filter: blur(10px);
}

.supported-conversions p {
  color: #00ffea;
  font-size: 14px;
  text-shadow: 0 0 10px rgba(0, 255, 234, 0.7);
  animation: glow 2s ease-in-out infinite alternate;
  margin: 0;
}

.mode-switch {
  display: flex;
  gap: 10px;
}

.mode-switch .neon-button.active {
  background: var(--primary-color);
  color: var(--background-color);
  box-shadow: var(--neon-shadow-primary);
}

.conversion-area {
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  gap: 20px;
  align-items: start;
}

.source-panel, .target-panel {
  display: flex;
  flex-direction: column;
  height: 600px;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border-bottom: var(--border-primary);
}

.code-editor {
  flex: 1;
  width: 100%;
  padding: 15px;
  background: rgba(0, 0, 0, 0.3);
  color: var(--text-color);
  border: none;
  resize: none;
  font-family: 'JetBrains Mono', 'Courier New', monospace;
  font-size: 14px;
  line-height: 1.5;
}

.file-operations {
  padding: 15px;
  border-top: var(--border-primary);
  text-align: right;
}

.converter-control {
  display: flex;
  flex-direction: column;
  gap: 30px;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

.conversion-options {
  color: var(--text-secondary);
}

.neon-checkbox input {
  margin-right: 8px;
}

.output-actions {
  padding: 15px;
  border-top: var(--border-primary);
  text-align: right;
}

.batch-conversion-area {
  min-height: 500px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.batch-conversion-area h3 {
  color: var(--primary-color);
  text-shadow: var(--neon-shadow-primary);
  margin-bottom: 20px;
}
</style>

