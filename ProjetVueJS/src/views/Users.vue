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
            <div
              class="card-header border-0"
            >
              <div class="row align-items-center">
                <div class="col">
                  <h3 class="mb-0">
                    {{ title }}
                  </h3>
                </div>
                <div class="col text-right">
                  <base-button type="primary" size="sm">See all</base-button>
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

            <div
              class="card-footer d-flex justify-content-end"
            >
              <base-pagination total="30"></base-pagination>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import userService from "../services/UserService";

export default {
  name: "users",
  components: {},
  data: function () {
    return {
      users: [],
      title:"Users list"
    };
  },
  mounted: function() {
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
    }
  }
};
</script>

<style></style>
