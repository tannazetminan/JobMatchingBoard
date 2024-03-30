import http from "../http-common.js";

class FetchDataService {
    getAllWorkers() {
        return http.get("/workers");
    }
    //create user/worker
    createWorker(workerData) {
        return http.post("/workers", workerData);
    }

    createUser(userData) {
        return http.post("/users", userData); 
    }
    //endpoints  for worker 
    getWorkerById(sid){
        return http.get(`/workers/${sid}`)
    }
   
    //endpoints fo user
   
    getUserById(sid){
        return http.get(`/users/${sid}`)
    }
    
    //endpoints for jobs
    getAllJobs() {
        return http.get("/jobs"); 
    }
    getjobBySkill(skill){
        return http.get(`/jobs/skills/${skill}`)
    }

    getjobByWorkerId(sid){
        return http.get(`/workers/${sid}/jobs`)
    }

    completeJob(jobId) {
        return http.post(`/jobs/${jobId}/complete`);
    }

    setJobRating(jobId, rating) {
        return http.post(`/jobs/${jobId}/rating`, {rating});
    }
    getJobByUserId(userId){
        return http.get(`/jobs/user/${userId}`);
    }

    //endpoints for message 
    getAllUserMessages(userId) {
        return http.get(`/messages/user/${userId}`);
    }

    getAllWorkerMessages(workerId) {
    return http.get(`/messages/worker/${workerId}`);
    }

    sendMessage(messageData) {
        return http.post("/messages/send", messageData);
    }

 

    
    
}

export default new FetchDataService();
