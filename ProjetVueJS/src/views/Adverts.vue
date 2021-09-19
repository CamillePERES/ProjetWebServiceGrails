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
                :data="adverts"
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
import advertService from "../services/AdvertService";

export default {
  name: "adverts",
  components: {},
  data: function () {
    return {
      adverts: [],
      title:"Adverts list"
    };
  },
  mounted: function() {
    console.log("Before HTML Page");
    this.getAllAdverts();
  },
  methods: {
    async getAllAdverts() {
      try {
        var response = await advertService.getAllAdvert();
        console.log(response);
        this.adverts = response.data;
      } catch (error) {
        console.log(error);
      }
    }
  }
};
</script>

<style></style>
