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

    getjobByWorkerId(sid){
        return http.get(`/workers/${sid}/jobs`)
    }
    
    getAllUserMessages(userId) {
        return http.get(`/messages/user/${userId}`);
    }

    getAllWorkerMessages(workerId) {
    return http.get(`/messages/worker/${workerId}`);
    }

    sendMessage(messageData) {
        return http.post("/messages/send", messageData);
    }

    completeJob(jobId) {
        return http.post(`/jobs/${jobId}/complete`);
    }

    setJobRating(jobId, rating) {
        return http.post(`/jobs/${jobId}/rating`, {rating});
      }
}

export default new FetchDataService();
