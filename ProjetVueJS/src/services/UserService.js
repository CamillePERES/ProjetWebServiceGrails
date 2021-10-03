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
      const roles = response.data.roles; 
      if(roles.length > 0){
        localStorage.setItem('roles', roles[0]);
      }
      localStorage.setItem('user-token', token);
      UserService.userLogged();
      callback(true);
    })
    .catch(error => {
      console.log(error)
      callback(false);
    })
}

UserService.isAuthentified = async () => {
  /*var auth = localStorage.getItem('user-token');
  var exp = localStorage.getItem('expires-in');
  console.log(auth, exp);
  var isAuth = auth !== undefined && auth !== null;
  if (isAuth && (store.profile === null || store.profile === undefined)) {
    UserService.userLogged();
  }
  return isAuth;*/
  return await UserService.userLogged();
}

UserService.getRole = () => {
  return localStorage.getItem('roles')
}

UserService.userLogged = async () => {
  try{
    const response = await axios.get(url + "/user/showUserLogged");
    console.log(response);
    store.profile = response.data;
    profileSubject.next(store.profile)
    return true;
  }
  catch(error){
      console.log(error);
      UserService.logout();
      return false;
  }
}

UserService.getProfile = () => {
  return profileSubject.asObservable();
}

UserService.logout = () => {
  localStorage.removeItem('user-token')
  localStorage.removeItem('roles')
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