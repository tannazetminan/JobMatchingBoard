<template>
    <div class="container-cards">
        
        <div class="cards-description">
           
            <div class ="personal-data">
                <img src="images/people.png" class="profile" />  
                <p class="data-description">{{ this.fullName }}</p>
                <p class="data-description">{{ user.email }}</p>
            </div>
            <div class ="container-form">
                <div class="ctn-title">
                    <h2 class=" title ">Post a Job</h2>
                </div>
                <form @submit.prevent="submitForm">
                    <div class="container-lbl">

                      <select v-model="newJob.title" required placeholder="Title" >
                        <option value="" disabled>Select category</option>
                        <option value="Carpentry">Carpentry</option>
                        <option value="Plumbing">Plumbing</option>
                        <option value="Masonry">Masonry</option>
                        <option value="Gardening">Gardening</option>
                        <option value="Electrical">Electrical</option>
                      </select>
                      <!-- <input type="text" id="email" v-model="newJob.title" required="true" placeholder="Title"/> -->
                        
                    </div>
                    <div class="container-lbl">
                     
                        <textarea v-model="newJob.description" required="true" placeholder="Description of the job"></textarea>
                        
                    </div>
                    <div class="container-lbl">
                      
                        <input type="text" id="budget" v-model="newJob.budget" required="true" placeholder ="Budget" />
                        <span v-if="!isValidBudget" class="error-message">{{ errorMessage2 }}</span>
                        
                    </div>
                    <div class=" container-btn">
                        <button type="submit" @click = "handleSubmit">Send</button>
                        <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

                    </div>
                </form>
            </div>          
        </div>
    <div class="card-jobs">   

      <div class="title-jobs">       
        
        <button @click="markAsUnCompleted" class="fetchData">Recent Jobs</button>
        <button @click="markAsCompleted" class="fetchData">Completed Jobs</button>
       

      </div>
      
      <div v-if="completedJobs.length">
        <div v-for="(job, index) in completedJobs" :key="index" class="job">
          <!-- <p class="desc-jobs1">Posted: {{ date }} </p> -->
          <p class="desc-job" style="font-weight: bold;">{{ job.title }} </p>
          <p class="desc-job">{{ message }}{{ job.description }} {{ message2 }} </p>
          <p class="desc-job"><span style="font-weight: bold;">Worker's Name: </span>{{ job.worker.username }} </p>
          <p class="desc-job"><strong>Budget: </strong>        
            <span>{{ job.budget }}</span>
          </p>
          <p class="desc-job"><strong>Job ID: </strong>{{ job.id }}  </p>
          <p class="desc-job">
            <star-rating v-model:rating="job.rating"
              star-size="35"	
              show-rating=False
              animate=true
              @update:rating="setRating(job.id, $event)">
            </star-rating>   
          </p>
        </div>
      </div>
  
      <div v-if="uncompletedJobs.length">
        <div v-for="(job, index) in uncompletedJobs" :key="index" class="job">
          <p class="desc-job" style="font-weight: bold;">{{ job.title }} </p>
          <p class="desc-job">{{ job.description }} </p>
          <p class="desc-job"><strong>Budget: </strong>        
            <span>{{ job.budget }}</span>
          </p>
          <p class="desc-job"><strong>Job ID: </strong>{{ job.id }}  </p>
          <!--<p class="desc-job"><strong>Rate: </strong>
                       <span v-if="job.rating !== null">{{ job.rating }}</span>
            <span v-else>{{ rating }}</span>
          </p> -->
           <p class="desc-job"><star-rating v-model:rating="job.rating"
              star-size="35"	
              show-rating=False
              animate=true
              @update:rating="setRating(job.id, $event)">
            </star-rating>  
            </p>
          

          <p class="desc-job"> 
            <button @click="markAsCompleted(job.id)" v-if="job.worker !=null" class="button-profile">Mark as Completed</button>    
          </p>    
        </div>
      </div>
    </div>
  </div>
</template>
  
<script>
  import FetchDataServices from '../services/FetchDataService'
  import StarRating from 'vue-star-rating';

  //import HeaderComponent from './HeaderComponent.vue'
  
  export default {
    name: "userDetails",

    components: {
      StarRating,
      //HeaderComponent,
    },

    data() {
      return {
        newLogin: false,
        user: {
          username: "",
          email: "",
          credit: 0,
          fullName: ""
        },
        jobs: [],
        date: "",
        random_number: 0,
        completedJobs: [],
        uncompletedJobs: [],
        clientId: null,
        newJob: {
          title:"",
          description:"",
          budget: ""
        }, 
        isValidBudget: true,
        errorMessage :"",
        errorMessage2 :"",
        rating: "Rating not available",
        message: "We are seeking a professional for ",
        message2: ". It's required knowledge of construction materials and carpentry techniques. Ability to interpret blueprints and follow instructions. Teamwork skills and ability to meet deadlines"
      }

    },

    methods: {
        retrieveUser() {       
          let id = localStorage.getItem('userId')
          let fullName ;
          this.fullName = localStorage.getItem('fullName')
          console.log(fullName);
        
          console.log("SID:" + id)
          FetchDataServices.getUserById(id)
            .then(response => {
              this.user = response.data
              console.log(this.user)
              this.fetchJobs(id)
            })
            .catch(error => {
              console.log(error)
            })
        },
        fetchJobs(id) {      
          FetchDataServices.getJobByUserId(id)
            .then(response => {
              this.jobs = response.data;
              this.markAsUnCompleted()
            })
            .catch(error => {
              if (error.response) {
                console.log(error.response.data);
                console.log(error.response.status);
              }
            });
        },
        markAsCompleted() {
          this.completedJobs = this.jobs.filter(job => job.isCompleted);
          this.uncompletedJobs = []          
        },
        markAsUnCompleted() {
          this.uncompletedJobs = this.jobs.filter(job => !job.isCompleted);
          
          console.log(this.uncompletedJobs)
          this.completedJobs = []
          
        },
        checklogin() {
          console.log("")
          localStorage.setItem('newLogin', false);
          this.newLogin = localStorage.getItem('newLogin');
          console.log(this.newLogin)
          window.location.reload();
        } ,

        handleSubmit(){
          let id = localStorage.getItem('userId')
          //input validation 
          if (this.newJob.title.trim() === '' || this.newJob.description.trim() === '' || this.newJob.budget.trim() === '') {
            this.errorMessage = "All fields are required";
            return; 
           }

    
          if (isNaN(this.newJob.budget)) {
              this.isValidBudget = false;
              this.errorMessage2 = "Budget must be a number";
              return; 
          }
          let newJobPost = {
              title: this.newJob.title,
              description: this.newJob.description,
              budget: this.newJob.budget
          };
      
          FetchDataServices.postNewJob(id, newJobPost)
          .then(response=>{
            console.log(response)
            this.fetchJobs(id);
            this.newJob.title="",
            this.newJob.description="",
            this.newJob.budget=""
           
          this.errorMessage = "";
            
          })
          .catch(error => {
              if (error.response) {
                console.log(error.response.data);
                console.log(error.response.status);
              }
            });

          }, 
        
      setRating(jobId, rating) {
            FetchDataServices.setJobRating(jobId, rating)
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
        this.retrieveUser(); 
        let newLogin = localStorage.getItem('newLogin'); 
        console.log("workerdetails newlogin:" + newLogin);   
        if (newLogin === 'true') { // Check if newLogin is true
          localStorage.setItem('newLogin', false);
          this.checklogin();
        }  
      }, 
   
  }
</script>
  
<style scoped >

  .container-cards{
      width: 95%;
      margin: auto;
      overflow: auto;
      /*background-color: aqua;*/
      display: flex;
  }
  
  .input-search{
      margin: auto;
     
      text-align: left;
      margin-bottom: 25px;
  }
  input{
      width: 80%;
      height: 30px;
      border-radius: 0.8rem;
      margin-left: 25px;
      background-size: 20px; 
      background-repeat: no-repeat;
      margin-top: 20px;
      
  }
  .profile{
      display: block;
      margin-top: 25px;
      height: 150px;
      width: 150px;
      
  }
  .cards-description{
    text-align: center;
    width: 20%;
    height: 850px;
    align-items: flex-start;
    border-radius: 0.5rem;
    
  
  }
  
  .card-jobs{
      width: 70%;
      text-align: center;
      margin-left: 100px;  
  
  }
  
  
  .job{
      margin-left: 20px;
      margin-bottom: 20px;
      width: 80%;
      border-radius: 0.5rem;
      text-align: left;
      background-color: rgb(229, 236, 238);
      padding: 5px;
      font-size: 20px;      
  }
  
  .desc-job{
      margin-left: 30px;
      font-size: 18px;
  }
  
  .cards{   
    background-color: rgb(121, 168, 194);
    align-items: flex-start;    
  }

  .personal-data{
     
      background-color: rgb(230, 239, 247);
      border-radius: 0.5rem;
      padding: 5px;
      font-size: 18px;
      margin-top: 15px;
  }

  .data-description{
      margin-bottom: 15px;
      padding: 2px;
      font-size: 20px;     
  }

  .setting{
    height: 350px;
    margin-bottom: 50px;
    background-color: rgb(230, 239, 247);
    border-radius: 0.5rem;
    margin-top: 15px;
    padding: 5px;
    font-size: 20px;
    text-align: left;  
  }
  
  
  .title-jobs{
    text-align: left;
  }

  .fetchData{
    margin-left: 30px;
    margin-bottom: 40px;
    font-size: 18px;
    background-color: transparent;
    border: none;
    color: rgb(60, 172, 15); 
    cursor: pointer;
    font-size: inherit;
    padding: 0; 
    font-weight: bold;
    font-size: 25px;
  }
  .edit{
      width: 30px;
      height: 40px;
  }
  .edit-preferences{
      display: flex
  }
  h2{
    margin-top: 15px;
    font-size: 25px;
    width: 90%;
    text-align: center;
  }
  
  .desc-job{
    font-size: 20px;
  }
  .desc-jobs1{
    font-size: 20px;
    text-align: right;
    margin-right: 15px;
    color:rgb(85, 80, 76)
  
  }
  .container-apply-btn{
    text-align: right;
  }
  .apply-btn{
    margin-left: 30px;
    margin-bottom: 20px;
    font-size: 18px;
    background-color: transparent;
    border: none;
    color: rgb(214, 132, 65); 
    cursor: pointer;
    font-size: inherit;
    padding: 0; 
    font-weight: bold;
    font-size: 20px;
    margin-right: 15px;       
  }

.container-form{
    background-color: #dde6ee;
    height: 450px;
    border-radius: 0.5rem; 
    font-size: 20px;
    margin: auto;
    margin-top: 10px;
    
  }

  form{
    padding: 2px;
    width: 80%;
    height: 350px;
    border-radius: 0.5rem;
    margin: auto;
    text-align: center;
    background-color: #205d96;

}
.ctn-title{
    margin-top: 25px; 
    padding: 0.5px;
}
.title{
    margin-top: 25px;
    padding: 0.5px;
}
input{
  width: 90%;
  padding: 0.5rem;
  border-radius: 4px;
  box-sizing: border-box; 
  border-color: #55970a;
  margin:auto;
  margin-top:2px;
}

button {
  padding: 0.5rem 1rem;
  background-color: #e27713;
  color: black;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
  margin-right: 5px;
  font-weight: bold;  
}

textarea{
  height: 100px;
  width:90%; 
  border-radius: 4px;
  box-sizing: border-box; 
  margin-left: 2rem;
  border-color: #7de20a;
  margin:auto;
  margin-top:2px;
}


label{

display: inline-block;
color:black;
text-align: left;
margin:auto;
margin-top:2px;
margin-bottom: 2px;
color: black

}
.container-lbl{
  text-align: left;
  margin-left: 15px;
  margin-bottom: 2px;
  margin-top:10px;
}
.error-message{
  color:white;
  font-size: 16px;
}

</style>