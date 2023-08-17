import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(),],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  build: {
    outDir: '../backend/src/main/resources/static', // Build Directory
  },
  server : {
    proxy :{
      '/api' : {
				target : 'http://localhost:8787', // Spring Boot Server
				changeOrigin: true // cross origin 허용
      }
    }
  }  

});
