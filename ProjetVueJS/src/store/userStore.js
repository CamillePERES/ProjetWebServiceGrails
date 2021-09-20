import { createStore } from 'vuex'
import axios from 'axios';

export default createStore({
    state: {
        username:'',
        password:'',
        url: "http://localhost:8080",
    },
    actions: {
        login(username, password) {
            axios.post(this.url + "/api/login", { username: username, password: password })
                .then(response => {
                    console.log(response);
                    const token = response.data.access_token;
                    localStorage.setItem('user-token', token);
                })
                .catch(error => {
                    console.log(error)
                })
        },
        userLogged() {
            axios.get(this.url + "/api/user/showUserLogged")
                .then(response => {
                    console.log(response);
                    this.username = response.data.username;
                    this.password = response.data.password;

                })
        },
        isAuthentified() {
            var auth = localStorage.getItem('user-token');
            console.log(auth);
            return auth != undefined && auth != null;
        },
        logout() {
            this.$router.push('/login');
            return localStorage.removeItem('user-token')
        }
    }
})
