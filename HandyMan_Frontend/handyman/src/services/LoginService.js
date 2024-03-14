import http from "../http-common.js";

class LoginService{
    login(data){
        return http.post("/users/login", data)
    }
}

export default new LoginService();