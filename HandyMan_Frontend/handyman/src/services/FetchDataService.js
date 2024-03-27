import http from "../http-common.js";

class FetchDataService {
    getAllWorkers() {
        return http.get("/workers");
    }

    createWorker(workerData) {
        return http.post("/workers", workerData);
    }

    createUser(userData) {
        return http.post("/users", userData); 
    }

    getAllJobs() {
        return http.get("/jobs"); 
    }
}

export default new FetchDataService();
