<template>

    <div class="container-cards">
        
        <div class="cards-description">
           
            <div class ="personal-data">
                <img src="images/people.png" class="profile" />  
                <p class=" data-description">{{ worker.username }}</p>
                <p class="data-description">{{ worker.description }}</p>
            </div>


            <div class="setting">
                <div class = "edit-preferences">
                    <h2>Preferences</h2>
                <img src="images/editar.png" class="edit" /> 
                </div>
                
                <p><strong>Location:</strong> {{ worker.location }}</p>
                <p><strong>Availability:</strong> {{ worker.workingHours }}</p>
                <p><strong>Preferred communication:</strong> {{ worker.preferredCommunication }}</p>
                <p><strong>Phone:</strong> {{ worker.phone }}</p>
                <p><strong>Email:</strong> {{ worker.email }}</p>

              
            </div>
        </div>

        <div class="card-jobs">
            <div class=" input-search">
                <input  tye="text" placeholder="Search..."/>
            </div>
            <div class="title-jobs">
                <button @click="fetchJobs" class="fetchData">Best Matches</button>
                <button @click="fetchRecentJobs" class="fetchData">Recent Jobs</button>
            </div>          
                
            <div v-if="jobs.length" >
                    <div v-for="(job, index) in jobs" :key="index" class="job">
                        <div v-if="jobs.length && displayNewJobs">
                            <p class="desc-jobs1">Posted: {{ date }} </p>
                            <p class="desc-job"> {{ message }}{{ job.description }} {{ message2 }}</p>
                            <p class="desc-job"><strong>Budged:</strong> ${{ job.budget }}</p>
                            <div class="container-apply-btn">
                                <button class="apply-btn">Apply Now</button>
                            </div>
                            
                        </div>
                        <div v-else-if="jobs.length && displayOldJobs">
                            <p class="desc-job"> {{ message }}{{ job.description }} {{ message2 }} </p>
                            <p class="desc-job"><strong>Rate: </strong>
                                <span v-if="job.rating !== null">{{ job.rating }}</span>
                                <span v-else>{{ rating }}</span>
                            </p>
                            <p class="desc-job"><strong>Status: </strong>
                                <span>{{ job.is_completed ? 'Completed' : 'In progress' }}</span>
                            </p>
                           
                        </div>
                           
                        
                        
                    </div>
               
            </div>
        </div>
    </div>

</template>

<script>

import FetchDataServices from  '../services/FetchDataService'

export default{
    name: "WorkerDetails",

    data(){
        return{
            worker: {
                username:"",
                description:"",
                skills: []
            },
            jobs:[],
            date: "",
            random_number: 0, 
            displayNewjobs: true,
            displayOldJobs: false,
            rating: "Rating no available", 
            status: "In progess",
            message: "We are seeking a professional  for ",
            message2: " . It's required knowledge of construction materials and carpentry techniques.Ability to interpret blueprints and follow instructions.Teamwork skills and ability to meet deadlines"


        }

    },

    methods:{

        retrieveWorker(){
            this.displayNewJobs = true;
            this.displayOldJobs = false;

            const id = localStorage.getItem('sid')
            console.log("SID:" + id)
            FetchDataServices.getWorkerById(id)
            .then(response =>{
                this.worker = response.data
                console.log("skills")
                console.log(this.worker.skills)
                this.fetchJobs()
               
            })
            .catch(error=>{
                console.log(error)
            })
            
        },

        fetchJobs() {
            this.worker.skills.forEach(skill => {

            const todayDate= new Date()

            this.random_number = Math.floor(Math.random() * 3) + 1; 
            console.log(this.random_number)

            todayDate.setDate(todayDate.getDate() - this.random_number);
            console.log(todayDate)

            const newDate = todayDate.toLocaleDateString();
           
            FetchDataServices.getjobBySkill(skill.name)
                .then(response => {
                    this.jobs = response.data;
                    this.date= newDate;
                    this.displayOldJobs=false
                    this.displayNewJobs=true
                    
                 
                })
                .catch(error => {
                    console.error(error);
                });
            });
        },
        fetchRecentJobs(){
            const id = localStorage.getItem('sid')
            FetchDataServices.getjobByWorkerId(id)
            .then(response=>{
                this.jobs = response.data
                this.displayNewJobs=false
                this.displayOldJobs =true
                
                console.log(this.jobs)
               
            })
            .catch(error => {
                    console.error(error);
                })

        }

    },
    mounted(){
        this.retrieveWorker()
        
        
       
    }



}



</script>

<style scoped >
.container-cards{
    width: 90%;
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
    /*background-image: url('../images/search.png');*/
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
  height: 750px;
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
    /*background-color: brown;*/
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

</style>