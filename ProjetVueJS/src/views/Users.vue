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
                  <th></th>
                </template>

                <template v-slot:default="row">
                  <td class="username">
                    {{ row.item.username }}
                  </td>
                  <td class="role">
                    {{ row.item.role }}
                  </td>
                </template>
              </base-table>
            </div>

            <div class="card-footer d-flex justify-content-end">
              <base-pagination total="30"></base-pagination>
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
</template>

<script>
import userService from "../services/UserService";

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
    };
  },
  mounted: function () {
    console.log("Before HTML Page");
    this.getAllUsers();
  },
  methods: {
    async getAllUsers() {
      try {
        var response = await userService.getAllUsers();
        console.log(response);
        this.users = response.data;
      } catch (error) {
        console.log(error);
      }
    },
    async createUser() {
      try {
        var response = await userService.createUser(this.model);
        console.log(response);
        this.$toast.success("Success ! User created");
        this.getAllUsers();
      } catch (error) {
        console.log(error);
        this.$toast.error("Error ! User has not been created");
      }
    },
  },
};
</script>

<style></style>
