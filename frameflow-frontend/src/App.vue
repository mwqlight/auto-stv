<template>
  <div class="app">
    <!-- 粒子背景效果 -->
    <div class="particle-background"></div>
    
    <header class="platform-header">
      <h1 class="logo">
        FRAME<span class="accent">FLOW</span>
        <span class="subtitle">前端框架互转平台</span>
      </h1>
    </header>
    <main>
      <ConverterView />
    </main>
  </div>
</template>

<script setup lang="ts">
import ConverterView from './views/ConverterView.vue'
</script>

<style scoped>
.app {
  min-height: 100vh;
  background-color: var(--background-color);
  color: var(--text-color);
  position: relative;
  overflow: hidden;
}

.particle-background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: 
    radial-gradient(circle at 10% 20%, rgba(0, 243, 255, 0.1) 0%, transparent 20%),
    radial-gradient(circle at 90% 80%, rgba(114, 9, 183, 0.1) 0%, transparent 20%);
  z-index: -1;
  animation: particles-move 20s infinite linear;
}

@keyframes particles-move {
  0% {
    transform: translate(0, 0);
  }
  50% {
    transform: translate(20px, 20px);
  }
  100% {
    transform: translate(0, 0);
  }
}

.platform-header {
  text-align: center;
  padding: 30px 0;
  position: relative;
}

.logo {
  font-family: 'Rajdhani', sans-serif;
  font-size: 3rem;
  font-weight: 600;
  letter-spacing: 2px;
  margin: 0;
  background: linear-gradient(90deg, var(--primary-color), var(--secondary-color));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  text-shadow: var(--neon-shadow-primary);
}

.accent {
  color: var(--primary-color);
  text-shadow: var(--neon-shadow-primary);
}

.subtitle {
  display: block;
  font-size: 1.2rem;
  color: var(--text-secondary);
  margin-top: 10px;
  font-weight: normal;
}
</style>