import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    host: '0.0.0.0', // 允许局域网和外部访问
    port: 5174,       // 强制使用5174端口
    strictPort: true, // 端口被占用时直接报错，不自动切换
    proxy: {
      '/api': {//获取路径中包含api的请求
        target: 'http://localhost:8080',
        changeOrigin: true,//改变源
        rewrite: (path) => {
          const newPath = path.replace(/^\/api/, '')//重写路径
          return newPath
        }
      }
    }
  }
})
