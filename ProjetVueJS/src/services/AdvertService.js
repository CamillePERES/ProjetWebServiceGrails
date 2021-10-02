import axios from 'axios';

//const endpoint = '/user';

/*const config = {
  headers: { Authorization: `Bearer ${token}` }
};*/

const AdvertService = {}

const url = "http://localhost:8080/api";

AdvertService.getAllAdverts = () => {
    return axios.get(url+"/advert")
}

AdvertService.getAdvert = (id) => {
    return axios.get(url + "/advert/" + id)
}

AdvertService.createAdvert = (model) => {
  return axios.post(url+"/advert", model)
}

AdvertService.pagination = (max, offset) => {
  return axios.post(url+"/advert/search", {max: max, offset: offset})
}

AdvertService.deleteAdvert = (id) => {
  return axios.delete(url+"/advert/" + id)
}

export default AdvertService;