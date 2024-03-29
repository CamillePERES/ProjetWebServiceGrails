/*!

=========================================================
* Vue Argon Dashboard - v2.0.1
=========================================================

* Product Page: https://www.creative-tim.com/product/vue-argon-dashboard
* Copyright 2021 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/vue-argon-dashboard/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import ArgonDashboard from "./plugins/argon-dashboard";
import "element-plus/lib/theme-chalk/index.css";
import axios from 'axios';
import Toaster from '@meforma/vue-toaster';

// Add a request interceptor
axios.interceptors.request.use(function (config) {
        const newConfig = {
      ...config,
     headers: { Authorization: `Bearer ${localStorage.getItem('user-token')}` }
    };
    console.log(newConfig)
    return newConfig;
  }, function (error) {
    // Do something with request error
    return Promise.reject(error);
});

const appInstance = createApp(App).use(Toaster);
appInstance.use(router);
appInstance.use(ArgonDashboard);
appInstance.mount("#app");
