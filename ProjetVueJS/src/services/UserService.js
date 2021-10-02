import axios from 'axios';
import { BehaviorSubject } from 'rxjs'

const endpoint = '/user';
const url = "http://localhost:8080/api";

const UserService = {}

const store = {
  profile: undefined
}
const profileSubject = new BehaviorSubject(store.profile);


UserService.login = (username, password, callback) => {

  axios.post(url + "/login", { username: username, password: password })
    .then(response => {
      console.log(response);
      const token = response.data.access_token;
      localStorage.setItem('user-token', token);
      UserService.userLogged();
      callback(true);
    })
    .catch(error => {
      console.log(error)
      callback(false);
    })
}

UserService.isAuthentified = () => {
  var auth = localStorage.getItem('user-token');
  console.log(auth);
  var isAuth = auth !== undefined && auth !== null;
  if (isAuth && (store.profile === null || store.profile === undefined)) {
    UserService.userLogged();
  }
  return isAuth;
}

UserService.userLogged = () => {

  axios.get(url + "/user/showUserLogged")
    .then(response => {
      console.log(response);
      store.profile = response.data;
      profileSubject.next(store.profile)
    })
}

UserService.getProfile = () => {
  return profileSubject.asObservable();
}

UserService.logout = () => {
  return localStorage.removeItem('user-token')
}

UserService.getAllUsers = () => {
  return axios.get(url + "/user/all");
}

UserService.getUser = (id) => {
  return axios.get(url + "/user/" + id)
}

UserService.createUser = (model) => {
  return axios.post(url+"/user", model)
}

UserService.deleteUser = (id) => {
  return axios.delete(url+"/user/"+ id)
}

UserService.getAdvertPerUser = (id) => {
  return axios.get(url+"/advert/user/"+id)
}

UserService.updateUser = (selectedUser) => {
  return axios.put(url+"/user/"+selectedUser.id, selectedUser)
}

UserService.pagination = (max, offset) => {
  return axios.post(url + "/user/search", { max: max, offset: offset })
}

UserService.list = () => {
  return axios.get(endpoint);
}

export default UserService;