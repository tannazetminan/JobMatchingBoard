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
    updateWorker(sid, data){
        return  http.put(`/workers/${sid}`, data)
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

    postNewJob(clientId, data){
        return http.post(`/jobs/create?clientId=${clientId}`, data)
    }

    //endpoints for message 
    getAllUserMessages(userId) {
        return http.get(`/messages/user/${userId}/all`);
    }

    getAllWorkerMessages(workerId) {
    return http.get(`/messages/worker/${workerId}`);
    }

    sendMessage(messageData) {
        return http.post(`/messages/send`, messageData);
    }

    searchWorkers(query) {
        return http.get(`/workers/search`, { params: { q: query } });
    }

 

    
    
}

export default new FetchDataService();
