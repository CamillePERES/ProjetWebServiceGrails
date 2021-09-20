import { createRouter, createWebHashHistory } from "vue-router";

import DashboardLayout from "@/layout/DashboardLayout";
import AuthLayout from "@/layout/AuthLayout";

import Dashboard from "../views/Dashboard.vue";
import Icons from "../views/Icons.vue";
import Maps from "../views/Maps.vue";
import Profile from "../views/UserProfile.vue";
import Tables from "../views/Tables.vue";
import Adverts from "../views/Adverts.vue";
import UserService from "../services/UserService";


import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
//import { userSetter } from "core-js/fn/symbol";

const routes = [
  {
    path: "/",
    redirect: "/dashboard",
    component: DashboardLayout,
    beforeEnter: (to, from, next) => {
      console.log("guarddashboard")
      if (!UserService.isAuthentified()) 
        next({ name: 'login' })
      else  next()
      // if the user is not authenticated, `next` is called twice      
    },
    children: [
      {
        path: "/dashboard",
        name: "dashboard",
        components: { default: Dashboard },
      },
      {
        path: "/icons",
        name: "icons",
        components: { default: Icons },
      },
      {
        path: "/maps",
        name: "maps",
        components: { default: Maps },
      },
      {
        path: "/profile",
        name: "profile",
        components: { default: Profile },
      },
      {
        path: "/tables",
        name: "tables",
        components: { default: Tables },
      },
      {
        path: "/adverts",
        name: "adverts",
        components: { default: Adverts },
      }
    ],
  },
  {
    path: "/",
    redirect: "login",
    component: AuthLayout,
    beforeEnter: (to, from, next) => {
      console.log("guardlogin")
      console.log(to.name)
      if (UserService.isAuthentified()) next({ name: 'dashboard' })
      // if the user is not authenticated, `next` is called twice
      else next()
    },
    children: [
      {
        path: "/login",
        name: "login",
        components: { default: Login },
      },
      {
        path: "/register",
        name: "register",
        components: { default: Register },
      },
    ],
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  linkActiveClass: "active",
  routes,
});

export default router;
