import axios from 'axios';

const endpoint = '/user';

/*const config = {
  headers: { Authorization: `Bearer ${token}` }
};*/

const UserService = {}

const url = "http://localhost:8080";

UserService.login = (username, password) => {

  axios.post(url+"/api/login", { username: username, password: password })
    .then(response => {
      console.log(response);
      const token = response.data.access_token;
      localStorage.setItem('user-token', token);
    })
    .catch(error => {
      console.log(error)
    })
}

UserService.list = () => {
  return axios.get(endpoint);
}

export default UserService;