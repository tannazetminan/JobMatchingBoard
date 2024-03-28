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

    getWorkerById(sid){
        return http.get(`/workers/${sid}`)
    }

    getAllJobs() {
        return http.get("/jobs"); 
    }

    getjobBySkill(skill){
        return http.get(`/jobs/skills/${skill}`)
    }

    
}

export default new FetchDataService();
