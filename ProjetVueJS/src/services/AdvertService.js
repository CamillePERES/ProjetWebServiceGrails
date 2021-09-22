import axios from 'axios';

//const endpoint = '/user';

/*const config = {
  headers: { Authorization: `Bearer ${token}` }
};*/

const AdvertService = {}

const url = "http://localhost:8080/api/advert";

AdvertService.getAllAdvert = () => {

    return axios.get(url)
}

AdvertService.getAdvert = (id) => {
    return axios.get(url + "/" + id)
}

export default AdvertService;