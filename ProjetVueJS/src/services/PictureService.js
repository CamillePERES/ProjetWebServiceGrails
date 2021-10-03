import axios from 'axios';

const PictureService = {}

const url = "http://localhost:8080/api";

PictureService.getAllPictures = () => {
    return axios.get(url+"/picture")
}

PictureService.getPicture = (id) => {
    return axios.get(url+"/picture/"+ id)
}

PictureService.addPicture = (featuredImageFile) => {
    return axios.post(url + "/picture", featuredImageFile)
}

PictureService.deletePicture = (id) => {
    return axios.delete(url + "/picture/" + id)
}

export default PictureService;