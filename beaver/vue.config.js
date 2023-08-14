const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: '../backend/src/main/resources/static', // Build Directory
	devServer: {
		proxy: {
			'/api' : {
				target : 'http://localhost:8787', // Spring Boot Server
				changeOrigin: true // cross origin 허용
            }
		},
	}
})