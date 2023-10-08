import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import store from './store/index.js' // Vuex 스토어를 가져옵니다.
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'

import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { fas } from '@fortawesome/free-solid-svg-icons'
import {far} from '@fortawesome/free-regular-svg-icons'
import {fab} from '@fortawesome/free-brands-svg-icons'

import axios from 'axios'

const app = createApp(App)
app.use(router)  // 라우터 사용
app.use(store) // vuex 사용

app.config.globalProperties.axios = axios;
/* add icons to the library */
library.add(fas,far,fab)

app.component('font-awesome-icon', FontAwesomeIcon)
app.mount('#app')