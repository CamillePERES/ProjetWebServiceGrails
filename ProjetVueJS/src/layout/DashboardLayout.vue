<template>
  <div class="wrapper" :class="{ 'nav-open': $sidebar.showSidebar }">
    <side-bar
      :background-color="sidebarBackground"
      short-title="Argon"
      title="Argon"
    >
      <template v-slot:links>
        
        <sidebar-item v-for="item in getItems()" v-bind:key="item.name"
          :link="{
            name: item.name,
            icon: item.icon,
            path: item.path,
          }"
        />

        <!--<sidebar-item
          :link="{
            name: 'Icons',
            icon: 'ni ni-planet text-blue',
            path: '/icons',
          }"
        />
        <sidebar-item
          :link="{
            name: 'Maps',
            icon: 'ni ni-pin-3 text-orange',
            path: '/maps',
          }"
        />
        <sidebar-item
          :link="{
            name: 'User Profile',
            icon: 'ni ni-single-02 text-yellow',
            path: '/profile',
          }"
        />
        <sidebar-item
          :link="{
            name: 'Tables',
            icon: 'ni ni-bullet-list-67 text-red',
            path: '/tables',
          }"
        />
        <sidebar-item
          :link="{
            name: 'Login',
            icon: 'ni ni-key-25 text-info',
            path: '/login',
          }"
        />
        <sidebar-item
          :link="{
            name: 'Register',
            icon: 'ni ni-circle-08 text-pink',
            path: '/register',
          }"
        />
        <sidebar-item
          :link="{
            name: 'Adverts',
            icon: 'ni ni-bullet-list-67 text-red',
            path: '/adverts',
          }"
        />
        <sidebar-item
          :link="{
            name: 'Users',
            icon: 'ni ni-single-02 text-yellow',
            path: '/users',
          }"
        />-->

      </template>
    </side-bar>
    <div class="main-content" :data="sidebarBackground">
      <dashboard-navbar></dashboard-navbar>

      <div @click="toggleSidebar">
        <!-- your content here -->
        <router-view></router-view>
        <content-footer v-if="!$route.meta.hideFooter"></content-footer>
      </div>
    </div>
  </div>
</template>
<script>
import DashboardNavbar from "./DashboardNavbar.vue";
import ContentFooter from "./ContentFooter.vue";
import UserService from "../services/UserService";

export default {
  components: {
    DashboardNavbar,
    ContentFooter,
  },
  data() {
    return {
      profile: undefined,
      sidebarBackground: "vue", //vue|blue|orange|green|red|primary
      itemsAdmin: [
          {
            name: 'Adverts',
            icon: 'ni ni-bullet-list-67 text-red',
            path: '/adverts',
          },
          {
            name: 'Users',
            icon: 'ni ni-single-02 text-yellow',
            path: '/users',
          }
          /*{
            name: 'User Profile',
            icon: 'ni ni-single-02 text-yellow',
            path: '/profile',
          }*/
      ],
      itemsModo: [
          {
            name: 'Adverts',
            icon: 'ni ni-bullet-list-67 text-red',
            path: '/adverts',
          }
          /*{
            name: 'User Profile',
            icon: 'ni ni-single-02 text-yellow',
            path: '/profile',
          }*/
      ],
      itemsClient: [
          {
            name: 'User Profile',
            icon: 'ni ni-single-02 text-yellow',
            path: '/profile',
          }
      ]
    };
  },
  methods: {
    toggleSidebar() {
      if (this.$sidebar.showSidebar) {
        this.$sidebar.displaySidebar(false);
      }
    },
    getItems() {
      const role = UserService.getRole();

      if(role === 'ROLE_ADMIN'){
        return this.itemsAdmin;
      }
      else if( role === 'ROLE_MODERATOR'){
        return this.itemsModo;
      }
      else if( role === 'ROLE_CLIENT'){
        return this.itemsClient;
      }

      return [];
    },
  },
};
</script>
<style lang="scss"></style>
