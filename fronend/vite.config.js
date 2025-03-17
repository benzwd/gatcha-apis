import { defineConfig } from 'vite'
import tailwindcss from '@tailwindcss/vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss(),],
    server: {
        proxy: {
            '/api/monsters': {
                target: 'http://localhost:8082',
                changeOrigin: true,
                rewrite: (path) => {
                    const newPath = path.replace(/^\/api/, '');
                    console.log(`Proxying ${path} to ${newPath}`);
                    return newPath;
                },
            },
            '/api/player': {
                target: 'http://localhost:8081',
                changeOrigin: true,
                rewrite: (path) => {
                    const newPath = path.replace(/^\/api/, '');
                    console.log(`Proxying ${path} to ${newPath}`);
                    return newPath;
                },
            },
            '/api/invocation': {
                target: 'http://localhost:8083',
                changeOrigin: true,
                rewrite: (path) => {
                    const newPath = path.replace(/^\/api/, '');
                    console.log(`Proxying ${path} to ${newPath}`);
                    return newPath;
                },
            },
        },
    },
})
