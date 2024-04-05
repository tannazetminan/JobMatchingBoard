<template>

    <div class="container-cards">
        
        <div class="cards-description">
           
            <div class ="personal-data">
                <img src="/images/people.png" class="profile" />  
                <p class=" data-description">{{ worker.username }}</p>
                <p class="data-description">{{ worker.description }}</p>
            </div>


            <div class="setting">
                <div class = "edit-preferences">
                    <h2>Personal Details</h2>
                </div>
                
                <div >
                    <p><strong>Location:</strong> {{ worker.location }}</p>
                    <p><strong>Availability:</strong> {{ worker.workingHours }}</p>
                    <p><strong>Preferred communication:</strong> {{ worker.preferredCommunication }}</p>
                    <p><strong>Phone:</strong> {{ worker.phone }}</p>
                    <p><strong>Email:</strong> {{ worker.email }}</p>
                </div>
           
            </div>
        </div>

        <div class="card-pf">
            <div class=" profile-desc">
                
                    <h3>Profile</h3>
               
                <div class ="desc">
                <p>As a versatile professional with a diverse skill set, I excel in various trades including plumbing, carpentry, and gardening. With technical expertise in tasks such as installing and repairing pipes, constructing furniture, and maintaining landscapes, I bring reliability and precision to every project. </p>
                </div>
                <h3> Technical Skills</h3>
                <ul>
                    <li v-for="(skill, index) in worker.skills" :key="index">{{ skill.name }}</li>
                </ul>
                <h3> Soft Skills</h3>
                <ul>
                    <li>Customer service skills</li>
                    <li>Effective communication with clients and colleagues</li>
                    <li>Teamwork</li>
                    <li>Adaptability to different work environments</li>
                    <li>Problem-solving</li>
                    <li>Time management and organization</li>
                    
                </ul>


               
            </div>
          
            
        </div>
    </div>

</template>

<script>

import FetchDataServices from  '../services/FetchDataService'

export default{
    name: "SelectedWorker",

    data(){
        return{
            newLogin: false,
            worker: {
                username:"",
                description:"",
                skills: []
            },
            jobs:[],
            date: "",
            random_number: 0, 
            showForm: false ,
            worker_settings:{
                location:"",
                availability: "",
                communication:"",
                phone: "",
                email: ""
            },
           idWorker: 0


        }

    },

    methods:{

        retrieveWorker(workerId){
            FetchDataServices.getWorkerById(workerId)
            .then(response =>{
                this.worker = response.data
                console.log(this.worker.skills)
               
            })
            .catch(error=>{
                console.log(error)
            })
            
        },   

    },
    mounted(){
        const workerId = this.$route.params.workerId;
        this.retrieveWorker(workerId)
      
    }
}



</script>

<style scoped >
.container-cards{
    width: 90%;
    margin: auto;
    overflow: auto;
    display: flex;
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
  height: fit-content;
  align-items: flex-start;
  border-radius: 0.5rem;
  
}

.card-pf{
    width: 70%;
    text-align: left;
    margin-left: 100px;  
 

}


.profile-desc{
    margin-left: 20px;
    margin-bottom: 20px;
    width: 80%;
    border-radius: 0.5rem;
    text-align: left;
    background-color: white;
    padding: 5px;
    font-size: 24px;
 
    
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


h2{
    margin-top: 15px;
    font-size: 25px;
    text-align: center;
}

.title{
    margin-top: 25px;
    padding: 0.5px;
}
.title-pr{
    text-align: left;
    
}

.desc{
    text-align: justify;
}


</style>