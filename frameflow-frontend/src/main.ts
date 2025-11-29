import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'

// 引入全局样式
import './styles/theme.css'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.mount('#app')