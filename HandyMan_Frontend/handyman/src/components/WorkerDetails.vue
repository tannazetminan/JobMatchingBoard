<template>

    <div class="container-cards">
        
        <div class="cards-description">
           
                <img src="images/people.png" class="profile" />  
        
            <div class ="personal-data">
                <p>{{ worker.username }}</p>
                <p>{{ worker.description }}</p>
            </div>


            <div class="setting">
                <h3>Preferences</h3>
            </div>
        </div>

        <div class="card-jobs">
            <div class=" input-search">
                <input  tye="text" placeholder="Search..."/>
            </div>
            <div class="title-jobs">
                <button @click="fetchJobs">Best Matches</button>
                <button @click="fetchRecentJobs">Recent Jobs</button>
            </div>          
                
            <div v-if="jobs.length" >
                    <div v-for="(job, index) in jobs" :key="index" class="job">
                        <p class="desc-job">Posted:{{ date }} </p>
                        <p class="desc-job">Description: {{ job.description }}</p>
                        <p class="desc-job">Presupuesto: {{ job.budget }}</p>
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
            random_number: 0

        }

    },

    methods:{

        retrieveWorker(){

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
                 
                })
                .catch(error => {
                    console.error(error);
                });
            });
        }

    },
    mounted(){
        this.retrieveWorker()
        
       
    }



}



</script>

<style scoped>
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
    background-size: 20px; /* Tamaño de la imagen */
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
  /*float: left;*/
  width: 20%;
  height: 750px;
  background-color: rgb(84, 99, 114);
  align-items: flex-start;
  border-radius: 0.5rem;
  

}

.card-jobs{
    /*float: left;*/
    width: 70%;
    text-align: center;
    margin-left: 100px;
  
    

}


.job{
 
    margin-left: 20px;
    margin-bottom: 20px;
    border: 1px solid;
    width: 80%;
    border-radius: 0.5rem;
    text-align: left;
    
    
}

.desc-job{
    margin-left: 30px;
    font-size: 18px;
}

.cards{
    
    /*margin-bottom: 80px;*/
    background-color: rgb(40, 85, 110);
    align-items: flex-start;
    
   
}
.personal-data{
    /*background-color: brown;*/
    margin-bottom: 100px;
    margin-bottom: 50px;
}
.setting{
    height: 600px;
    /*background-color: brown;*/
    margin-bottom: 50px;
}

.title-jobs{}


</style>