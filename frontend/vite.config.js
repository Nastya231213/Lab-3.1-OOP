import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/touragency': {
        target: 'http://localhost:8085',
        changeOrigin: true,
        secure: false,
      },
    },
  },
})
