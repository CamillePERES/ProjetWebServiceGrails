<template>
  <div>
    <base-header
      type="gradient-success"
      class="pb-6 pb-8 pt-5 pt-md-8"
    ></base-header>

    <div class="container-fluid mt--7">
      <div class="row">
        <div class="col">
          <div class="card shadow">
            <div class="card-header border-0">
              <div class="row align-items-center">
                <div class="col">
                  <h3 class="mb-0">
                    {{ title }}
                  </h3>
                </div>
                <div class="col text-right">
                  <base-button
                    type="primary"
                    @click="modalOpen = true"
                    size="sm"
                    >Create user</base-button
                  >
                </div>
              </div>
            </div>

            <div class="table-responsive">
              <base-table
                class="table align-items-center table-flush"
                :thead-classes="'thead-light'"
                tbody-classes="list"
                :data="users"
              >
                <template v-slot:columns>
                  <th>Username</th>
                  <th>Role</th>
                  <th>Details</th>
                  <th>Delete</th>
                </template>

                <template v-slot:default="row">
                  <td class="username">
                    {{ row.item.user.username }}
                  </td>
                  <td class="role">
                    {{ roleToString(row.item.roles) }}
                  </td>
                  <td class="details">
                    <base-button
                      @click="onClickDetails(row.item.user.id)"
                      type="primary"
                      size="sm"
                      >Show details</base-button
                    >
                  </td>
                  <td class="delete">
                    <base-button
                      @click="onClickDelete(row.item.user.id)"
                      type="primary"
                      size="sm"
                      >Delete user</base-button
                    >
                  </td>
                </template>
              </base-table>
            </div>

            <div class="card-footer d-flex justify-content-end">
              <base-pagination 
                @input="onClickPagination" 
                v-bind:total="page.total" 
                v-bind:value="valuePagination"
                v-bind:perPage="page.max"
                ></base-pagination>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <modal v-model:show="modalOpen">
    <template v-slot:header>
      <h5 class="modal-title" id="exampleModalLabel">New account</h5>
    </template>
    <div class="row">
      <div class="col-lg-6">
        <base-input
          alternative=""
          label="Username"
          placeholder="Username"
          input-classes="form-control-alternative"
          v-model="model.username"
        />
      </div>
      <div class="col-lg-6">
        <base-input
          alternative=""
          label="Password"
          type="password"
          placeholder="password"
          input-classes="form-control-alternative"
          v-model="model.password"
        />
      </div>
    </div>
    <template v-slot:footer>
      <base-button type="secondary" @click="modalOpen = false"
        >Close</base-button
      >
      <base-button v-on:click="createUser" type="primary"
        >Save user</base-button
      >
    </template>
  </modal>

  <modal v-model:show="modalDetails">
    <template v-slot:header>
      <h5 class="modal-title" id="exampleModalLabel">
        Details of the user: {{ selectedUser?.user.username }}
      </h5>
    </template>
    <div class="row">
      <div class="col-12">
        <label for="name"> Username </label>
      </div>
      <div class="col-12">
          <base-input v-if="selectedUser" v-model="selectedUser.user.username" />
      </div>
      <div class="col-12">
        <label for="name"> Role </label>
      </div>
      <div class="col-12">
          <div class = "row" v-for="r in roles" :key="r.id">
              <div class="col-3">
                <input type="checkbox" v-bind:value="r.authority" v-model="selectedRole">
              </div>
              <div class="col-9">
                {{r.authority}}
              </div>
          </div>
      </div>
    </div>
    <div class="pl-lg-4">
      <div class="row">
        <div class="col-md-12">
          <div class="table-responsive">
            <base-table
              class="table align-items-center table-flush"
              :thead-classes="'thead-light'"
              tbody-classes="list"
              :data="advertOfSelectedUser"
              v-if="advertOfSelectedUser.length > 0"
            >
              <template v-slot:columns>
                <th>Title</th>
                <th>Description</th>
                <th>Price</th>
                <th></th>
              </template>

              <template v-slot:default="row">
                <td class="title">
                  {{ row.item.title }}
                </td>
                <td class="description">
                  {{ row.item.description }}
                </td>
                <td class="price">
                  {{ row.item.price }}
                </td>
              </template>
            </base-table>
            <div v-else>There is no adverts for this user.</div>
          </div>
        </div>
      </div>
    </div>
    <template v-slot:footer>
      <base-button type="secondary" v-on:click="updateUser">Update</base-button>
      <base-button type="secondary" @click="modalDetails = false"
        >Close</base-button
      >
    </template>
  </modal>

  <modal
    v-model:show="modalDelete"
    data-backdrop="static"
    data-keyboard="false"
  >
    <div class="row">Do you want to delete this user?</div>
    <template v-slot:footer>
      <base-button v-on:click="deleteUser" type="primary">Yes</base-button>
      <base-button type="primary" @click="modalDelete = false">No</base-button>
    </template>
  </modal>
</template>

<script>
import userService from "../services/UserService";
import roleService from "../services/RoleService";

export default {
  name: "users",
  components: {},
  data: function () {
    return {
      users: [],
      title: "Users list",
      model: {
        username: "",
        password: "",
      },
      modalOpen: false,
      modalDetails: false,
      modalDelete: false,
      selectedUser: undefined,
      advertOfSelectedUser: [],
      deletedUser: undefined,
      shouldShowModal: false,
      roles:[], 
      selectedRole:[], 
      valuePagination: 1,
      page:{
        max:5,
        offset: 0, 
        total:0,
        value: 0
      }
    };
  },
  mounted: function () {
    console.log("Before HTML Page");
    this.getAllUsers();
    this.getAllRoles();
  },
  methods: {
    async getAllUsers() {
      try {
        var response = await userService.pagination(this.page.max, this.page.offset);
        console.log(response)
        this.page.total = response.data.total;
        console.log("TOTAL");
        console.log(this.page.total);
        this.users = response.data.users;
      } catch (error) {
        console.log(error);
      }
    },
    async createUser() {
      try {
        var response = await userService.createUser(this.model);
        console.log(response);
        this.$toast.success("Success ! User created");
        this.model = {
          username: "",
          password: "",
        };
        this.getAllUsers();
        this.modalOpen = false;
      } catch (error) {
        console.log(error);
        this.$toast.error("Error ! User has not been created");
      }
    },
    async showUser(id) {
      try {
        var response = await userService.getUser(id);
        console.log(response);
        this.selectedUser = response.data;
        /*avant : roles[0].authority (authority:Role_Admin)
        map met la valeur contenue dans "authority" dans un nouveau tableau
        maintenant : ["Role_Admin"] et pour l'atteindre role[0]
        
        de maniere plus traditionnelle:
        
        let selected = [];

        for(let r of this.selectedUser.roles){
          selected.push(r.authority);
        }*/

        this.selectedRole = this.selectedUser.roles.map(r => r.authority)
        console.log(this.selectedRole)
        this.advertPerUser();
      } catch (error) {
        console.log(error);
        throw error;
      }
    },
    async advertPerUser() {
      if (this.selectedUser === undefined) return;

      try {
        var response = await userService.getAdvertPerUser(this.selectedUser.user.id);
        console.log(response);
        this.advertOfSelectedUser = response.data;
      } catch (error) {
        console.log(error);
        throw error;
      }
    },
    async onClickDetails(id) {
      console.log(id);
      try {
        await this.showUser(id);
        this.modalDetails = true;
      } catch (error) {
        console.log(error);
      }
    },
    async deleteUser() {
      try {
        var response = await userService.deleteUser(this.selectedUser.user.id);
        console.log(response);
        this.$toast.success("Success ! User deleted");
      } catch (error) {
        console.log(error);
        this.$toast.error("Error ! User has not been deleted");
      }
      this.getAllUsers();
      this.modalDelete = false;
    },
    async onClickDelete(id) {
      console.log(id);
      try {
        await this.showUser(id);
        this.modalDelete = true;
      } catch (error) {
        console.log(error);
      }
    },
    roleToString(roles) {
      return roles.map((role) => role.authority).join(", ");
    },
    async updateUser() {
      try {
        this.selectedUser.roles = this.selectedRole.map(str => { 
          return {authority:str}
         });
         console.log(this.selectedUser)
        var response = await userService.updateUser(this.selectedUser.user);
        console.log(response);
      } catch (error) {
        console.log(error);
      }
      this.getAllUsers();
    },
    async getAllRoles(){
      try{
        var response = await roleService.getAllRoles();
        console.log(response)
        this.roles=response.data;
        console.log(this.roles)
      }catch (error) {
        console.log(error);
      }
    }, 
    async onClickPagination(value){
      try{
        this.page.offset = this.page.max * (value-1);
        this.valuePagination = value;
        await this.getAllUsers()
      }catch (error) {
        console.log(error);
      }
    }
  },
};
</script>

<style></style>
