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
                    v-if="this.authorization?.advert.create"
                    type="primary"
                    @click="modalOpen = true"
                    size="sm"

                    >Create advert</base-button
                  >
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
                  <th>Details</th>
                  <th v-if="this.authorization?.advert.delete">Delete</th>
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
                  <td class="details">
                    <base-button
                      @click="onClickDetails(row.item.id)"
                      type="primary"
                      size="sm"
                      >Show details</base-button
                    >
                  </td>
                  <td class="delete" v-if="this.authorization?.advert.delete">
                    <base-button
                      @click="onClickDelete(row.item.id)"
                      type="primary"
                      size="sm"
                      >Delete advert</base-button
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

            <modal v-model:show="modalOpen">
              <template v-slot:header>
                <h5 class="modal-title" id="exampleModalLabel">New advert</h5>
              </template>
              <div class="row">
                <div class="col-lg-6">
                  <base-input
                    alternative=""
                    label="Title"
                    placeholder="title"
                    input-classes="form-control-alternative"
                    v-model="model.title"
                  />
                </div>
                <div class="col-lg-6">
                  <base-input
                    alternative=""
                    label="Description"
                    placeholder="description"
                    input-classes="form-control-alternative"
                    v-model="model.description"
                  />
                </div>
                <div class="col-lg-6">
                  <base-input
                    alternative=""
                    label="Price"
                    placeholder="price"
                    input-classes="form-control-alternative"
                    v-model="model.price"
                  />
                </div>
                <div class="col-lg-6">
                  <select v-model="selected">
                    <option
                      v-for="option in users"
                      v-bind:value="option.user.id"
                      v-bind:key="option.user.id"
                    >
                      {{ option.user.username }}
                    </option>
                  </select>
                </div>
              </div>
              <template v-slot:footer>
                <base-button type="secondary" @click="modalOpen = false"
                  >Close</base-button
                >
                <base-button v-on:click="createAdvert" type="primary"
                  >Save advert</base-button
                >
              </template>
            </modal>

            <modal v-model:show="modalDetails">
              <template v-slot:header>
                <h5 class="modal-title" id="exampleModalLabel">
                  Details of the advert: {{ selectedAdvert?.title }}
                </h5>
              </template>
              <div class="row">
                <div class="col-12">
                  <label for="name"> Title </label>
                </div>
                <div class="col-12">
                  <base-input
                    v-if="selectedAdvert"
                    v-model="selectedAdvert.title"
                  />
                </div>
                <div class="col-12">
                  <label for="name"> Description </label>
                </div>
                <div class="col-12">
                  <base-input
                    v-if="selectedAdvert"
                    v-model="selectedAdvert.description"
                  />
                </div>
                <div class="col-12">
                  <label for="name"> Price </label>
                </div>
                <div class="col-12">
                  <base-input
                    v-if="selectedAdvert"
                    v-model="selectedAdvert.price"
                  />
                </div>
                <div class="col-12">
                  <label for="name"> Picture </label>
                </div>
                <div class="col-12">
                  <div class="form-group">
                    <input type="file" @change="upload($event)" multiple />
                  </div>
                </div>
                <div class="col-12">
                  <div class="row">
                  <div v-for="pictures in selectedAdvert?.pictures" class="col-md-4 my-1" v-bind:key="pictures.id">
                      <img
                        :src="getUrlPictures(pictures.id)"
                        class="card-img-top"
                      />         
                      <base-button @click="deletePicture(pictures.id)">Delete</base-button>   
                  </div>
                </div>
                </div>
              </div>
              <template v-slot:footer>
                <base-button type="secondary" v-on:click="updateAdvert"
                  >Update</base-button
                >
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
              <div class="row">Do you want to delete this advert?</div>
              <template v-slot:footer>
                <base-button v-on:click="deleteAdvert" type="primary"
                  >Yes</base-button
                >
                <base-button type="primary" @click="modalDelete = false"
                  >No</base-button
                >
              </template>
            </modal>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import advertService from "../services/AdvertService";
import userService from "../services/UserService";
import pictureService from "../services/PictureService";
import authorizationService from "../services/AuthorizeService";

export default {
  name: "adverts",
  components: {},
  data: function () {
    return {
      authorization: undefined,
      adverts: [],
      title: "Adverts list",
      model: {
        title: "",
        description: "",
        price: "",
        user: undefined,
      },
      selected: 0,
      users: [],
      modalOpen: false,
      modalDelete: false,
      modalDetails: false,
      page: {
        max: 10,
        offset: 0,
        total: 0,
        value: 0,
      },
      valuePagination: 1,
      selectedAdvert: undefined,
      featuredImageFile: undefined,
      files: null,
    };
  },
  mounted: function () {
    console.log("Before HTML Page");
    this.getAuthorize();
    this.getAllAdverts();
    if(this.authorization?.advert.create){
      this.getAllUsers();
    }
    //this.getAllPictures();
  },
  methods: {
    getAuthorize(){
      this.authorization = authorizationService.getAuthorization();
    },
    async getAllAdverts() {
      try {
        var response = await advertService.pagination(
          this.page.max,
          this.page.offset
        );
        console.log(response);
        this.page.total = response.data.total;
        console.log(this.page.total);
        this.adverts = response.data.adverts;
      } catch (error) {
        console.log(error);
      }
    },
    async createAdvert() {
      try {
        console.log(this.selected);
        this.model.user = this.users
          .map((e) => e.user)
          .find((x) => x.id === this.selected);
        console.log(this.model.user);
        var response = await advertService.createAdvert(this.model);
        console.log(response);

        this.$toast.success("Success ! Advert created");
        this.model = {
          title: "",
          description: "",
          price: 0,
          user: undefined,
        };
        this.getAllAdverts();
        this.modalOpen = false;
      } catch (error) {
        console.log(error);
        this.$toast.error("Error ! Advert has not been created");
      }
    },
    async getAllUsers() {
      try {
        var response = await userService.getAllUsers();
        console.log(response);
        this.users = response.data;
        console.log("USERS");
        console.log(this.users);
      } catch (error) {
        console.log(error);
      }
    },
    async onClickPagination(value) {
      try {
        this.page.offset = this.page.max * (value - 1);
        this.valuePagination = value;
        await this.getAllAdverts();
      } catch (error) {
        console.log(error);
      }
    },
    async showAdvert(id) {
      try {
        var response = await advertService.getAdvert(id);
        console.log(response);
        this.selectedAdvert = response.data;
        console.log("ADVERT");
        console.log(this.selectedAdvert);
      } catch (error) {
        console.log(error);
      }
    },
    async updateAdvert() {
      try {
        var response = await advertService.updateAdvert(this.selectedAdvert);
        console.log(response);
      } catch (error) {
        console.log(error);
      }
      this.getAllAdverts();
    },
    async getAllPictures() {
      try {
        var response = await pictureService.getAllPictures();
        console.log(response);
      } catch (error) {
        console.log(error);
      }
    },
    async upload(event) {
      try {
        this.featuredImageFile = new FormData();
        this.files = event.target.files;

        if (this.files.lenght == 0) return;

        this.featuredImageFile.append("idAdvert", this.selectedAdvert.id);
        this.featuredImageFile.append("featuredImageFile", this.files[0]);

        var response = await pictureService.addPicture(this.featuredImageFile);

        if(this.selectedAdvert.pictures === undefined){
          this.selectedAdvert.pictures = [];
        }

        this.selectedAdvert.pictures.push(response.data);

        console.log("PICTURE");
        console.log(response);
      } catch (error) {
        console.log(error);
      }
    },

    getUrlPictures(id){
      return "http://localhost:8080/api/picture/" + id;
    },

    async onClickDetails(id) {
      console.log(id);
      try {
        await this.showAdvert(id);
        this.modalDetails = true;
      } catch (error) {
        console.log(error);
      }
    },
    async deleteAdvert() {
      try {
        var response = await advertService.deleteAdvert(this.selectedAdvert.id);
        console.log(response);
        this.$toast.success("Success ! Advert deleted");
      } catch (error) {
        console.log(error);
        this.$toast.error("Error ! Advert has not been deleted");
      }
      this.getAllAdverts();
      this.modalDelete = false;
    },
    async onClickDelete(id) {
      console.log(id);
      try {
        await this.showAdvert(id);
        this.modalDelete = true;
      } catch (error) {
        console.log(error);
      }
    },
    async deletePicture(id){
      try{
          var response = await pictureService.deletePicture(id);
          console.log(response);
          this.showAdvert(this.selectedAdvert.id);
      }catch (error) {
        console.log(error);
      }
    }
  },
};
</script>

<style>
#preview {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
