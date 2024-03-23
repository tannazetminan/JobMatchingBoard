import http from "../http-common.js";

class FetchDataService{
    getAllWorkers(){
        return http.get("/workers")
    }
}

export default new FetchDataService();