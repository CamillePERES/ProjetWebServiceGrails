import axios from 'axios';
const url = "http://localhost:8080/api/role";

const RoleService = {}

RoleService.getAllRoles = () =>{
    return axios.get(url);
}

export default RoleService;