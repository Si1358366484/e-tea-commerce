import './assets/main.scss'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { createApp } from 'vue'
import {createPinia} from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from '@/router'
import { createPersistedState } from 'pinia-persistedstate-plugin'
import App from './App.vue'
import locale from 'element-plus/dist/locale/zh-cn.mjs'

const app = createApp(App)
const pinia = createPinia()
const persist = createPersistedState()
pinia.use(persist)
app.use(pinia)
app.use(ElementPlus,{locale})
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(router)
app.mount('#app')