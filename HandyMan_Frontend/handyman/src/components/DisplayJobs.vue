<template>   
  <div class="container-worker">

    <!--<div class="categories">
      <div style="color: blue;">"WELCOME user {{ this.clientId }}" &nbsp;&nbsp; &nbsp;&nbsp; </div>

      <span> Carpentry</span>
      <span>Plumbing </span>
      <span>Electrical </span>
      <span>Masonry</span>
      <span>Gardening </span>
    </div>-->

    <header-component></header-component>


    <div>
      <h1 class="title">UnCompleted Jobs:</h1>
    </div>

    <div class="cards">
      <div v-for="job in uncompletedJobs" :key="job.id" class="card-worker">
        <div v-if="job.isCompleted === false" class="completedJob" style="font-weight: normal;">            
          <p><span class="bold">Job Category: </span>{{ job.title }}</p>
          <p><span class="bold">Job Description: </span>{{ message }}{{ job.description }} {{ message2 }} </p>
          <p><span class="bold">Client Nme: </span>{{ getClientId(job.client.username) }}</p>
          <div class="rating-container">
              <img src="images/budget.png" class="rating" /><span class="bold">Budget: </span> {{ job.budget }}
          </div>
          <p><span class="bold">Job Id: </span>{{ job.id }}</p>
          <div v-if="job.client.id == this.clientId">
            <star-rating v-model:rating="job.rating"
              star-size="35"	
              show-rating=False
              animate=true
              @update:rating="setRating(job.id, $event)">
            </star-rating>    
            <button @click="markAsCompleted(job.id)" v-if="job.client.id  == clientId" class="button-profile">Mark as Completed</button>    
        </div>  
      
      </div>
    </div>
  </div>

  <div>
    <div><p><br><br><br><br></p></div>
    <h1 class="title">Completed Jobs:</h1>
  </div>

  <div class="cards-completed"> 
    <div v-for="job in completedJobs" :key="job.id" class="card-worker" style="font-weight: normal;">

      <div class="unCompletedJob">
        <img src="images/job.png"  class="profile" />
        <p>{{ job.id }}</p>
        <p><span class="bold">Job Category: </span>{{ job.title }}</p>
        <p><span class="bold">Client: </span>{{ getClientId(job.client.username) }}</p>
        <p><span class="bold">Worker:</span>{{ getWorkerId(job.worker.username) }}</p>
        <div class="middle-div">
          <div class="rating-container">
              <img src="images/budget.png" class="rating" /><span class="bold">Budget: </span> {{ job.budget }}
          </div>
          <div v-if="job.rating === null">
              <p>Ranking not defined yet</p>
          </div>
          <div v-if="job.rating === 5"> 
              <img src="images/5star.png" class="rating" />
          </div>
          <div v-if="job.rating === 4"> 
              <img src="images/4star.png" class="rating" />
          </div>
          <div v-if="job.rating === 3"> 
              <img src="images/3star.png" class="rating" />
          </div>
          <div v-if="job.rating === 2"> 
              <img src="images/2star.png" class="rating" />
          </div>
          <div v-if="job.rating === 1"> 
              <img src="images/1star.png" class="rating" />
          </div>
        </div>    
      </div>
      </div>
    </div>
  </div>
 </template>
 
 <script>
 import FetchDataService from "../services/FetchDataService";
 import StarRating from 'vue-star-rating';
 import HeaderComponent from './HeaderComponent.vue';


 export default{ 
     
     name:"DisplayJobs",

     components: {
        StarRating,
        HeaderComponent
     },

     data() {
     return {
       jobs: [],
       completedJobs: [],
       uncompletedJobs: [],
       clientId : null,
       message: "We are seeking a professional for ",
       message2: ". It's required knowledge of construction materials and carpentry techniques. Ability to interpret blueprints and follow instructions. Teamwork skills and ability to meet deadlines"       
     }
   },
 
     methods:{

       getClientId(client) {
         return typeof client === 'object' && client !== null ? client.id : client;
       },

       getWorkerId(worker) {
         return typeof worker === 'object' && worker !== null ? worker.id : worker;
       },

      fetchJobs(){
            FetchDataService.getAllJobs()
            .then(response =>{
                this.jobs =response.data;
                console.log(response);
                
                // Sort jobs into completed and uncompleted
                this.completedJobs = this.jobs.filter(job => job.isCompleted);
                this.uncompletedJobs = this.jobs.filter(job => !job.isCompleted);
            })
            .catch (error =>{
                if (error.response) {
                  console.log(error.response.data);
                  console.log(error.response.status);
                }
            }
            );
        },
        markAsCompleted(jobId) {
          FetchDataService.completeJob(jobId)
            .then(response => {
              console.log("Job marked as completed:", jobId);
              console.log(response);
              // Refresh the job list after marking as completed
              this.fetchJobs();
            })
            .catch(error => {
              console.error("Error marking job as completed:", error);
            });
        },
        setRating(jobId, rating) {
          FetchDataService.setJobRating(jobId, rating)
        .then(response => {
          console.log("Job rating updated:", jobId, "Rating:", rating);
          console.log(response)
        })
        .catch(error => {
          console.error("Error updating job rating:", error);
        });
        }
    },
    mounted() {
      this.fetchJobs();
      this.clientId = localStorage.getItem('userId')
      console.log("user id:" + this.clientId)     
   },
 }

 </script>
 
 <style scoped>
 h1{
     font-size: 50px;
     margin-left: 9rem;
     color: rgb(1, 97, 1);
     margin-top: 70px;
 }
 
 
 .categories {
   display: flex;
   width: 80%;
   margin-left: 10rem;
   font-size: 20px;
   font-weight: bold;
   color:#e27713;
 }
 
 .categories span {
   margin-right: 15px; 
 }
 
 .cards{
   margin: auto;
   width: 70%;
   display: grid;
   grid-gap: 10px;
   margin-top: 6rem;
   grid-template-columns: repeat(1, minmax(290px, 1fr));
   text-align: center;
   font-size: 18px;
   text-align: justify;
   font-size: 20px;
 }

 .cards-completed{
   margin: auto;
   width: 70%;
   display: grid;
   grid-gap: 10px;
   margin-top: 6rem;
   grid-template-columns: repeat(3, minmax(290px, 1fr));
   text-align: center;
   font-size: 16px;
 }
 
 .card-worker{
   box-sizing: border-box;
   border-radius: 0.5rem;
   display: flex;
   flex-direction: column;
   text-align: justify;
   transition: transform 0.3s ease;
   max-width:80%;
   background-color: rgb(229, 236, 238);
   margin-left: 20px;
   /* border: 1px solid rgb(31, 142, 175); */
   line-height: 1; 
   padding: 20px;
 }
 .profile{
   width: 100px;
   height: 100px;
   margin: auto;
   margin-top: 15px;
 }
 
 .rating{
   max-width: 100px;
   max-height: 21px;
   margin: auto;
   margin-top: 15px;
   display:inline;
   width: auto;
   text-align: center;
 }
 .rating-container img,
 .rating-container span {
     display: inline-block;
     margin-right: 5px;
     margin-top: 0px;
     
 }
 .subtitle-worker{
   text-align: left;
   margin-bottom: 20px;
 }
 
 .button-profile{
   display: inline-block;
   padding: 0.5rem 1rem;
   background-color: #e27713;
   color: rgb(32, 20, 20);
   width: 120px;
   border: none;
   border-radius: 4px;
   cursor: pointer;
   margin: auto;
   margin-bottom: 10px;
 }
 
 .middle-div{
  text-align: left;
 }
 

 .bold{
  font-weight: bold;
 }
 </style>