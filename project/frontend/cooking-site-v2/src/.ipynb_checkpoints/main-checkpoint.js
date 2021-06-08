import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import 'material-design-icons-iconfont/dist/material-design-icons.css'

Vue.config.productionTip = false



new Vue({
  data: {
    currentUser: {"id" : "-1",
                  "userName" : "aGuest"
               },
    baseUrl: "http://localhost:8080",
    dbType : "SQL"
  },
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')

