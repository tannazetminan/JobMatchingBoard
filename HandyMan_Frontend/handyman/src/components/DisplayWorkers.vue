<template>   
  <div class="container-worker">
    <div class="categories">
        <span> Carpentry</span>
        <span>Plumbing </span>
        <span>Electrical </span>
        <span>Masonry</span>
        <span>Gardening </span>
    </div>

  <div>
      <h1 class="title">Hire the best skilled workers </h1>
  </div>
    <div class="cards">
      <div v-for="worker in workers" :key="worker.id" class="card-worker">
        <img src="images/people.png"  class="profile" />
        <p><strong>id:</strong> {{ worker.id }}</p>

        <p style="font-size: large;">{{ worker.username }}</p>
        <p style="font-weight: normal;">{{ worker.description }}</p>
        <p style="text-align: left; margin-left: 10px;">
          <img src="../../public/images/phone.png" width="25px" height="25px" style="margin-bottom: -7px;"/> {{ worker.phone }}<br/><br/>
          <img src="../../public/images/email.png" width="30px" height="30px" style="margin-bottom: -10px; padding-right: 2px; margin-left: -2px;"/>{{ worker.email }}
      </p>
        
        <!-- <div class="rating-container">
          <img src="images/star.png" class="rating" /><span>{{ worker.averageRating }}</span>
        </div> -->
        <div v-if="worker.averageRating === 5"> 
          <img src="images/5star.png" class="rating" />
        </div>
        <div v-if="worker.averageRating === 4"> 
            <img src="images/4star.png" class="rating" />
        </div>
        <div v-if="worker.averageRating === 3"> 
            <img src="images/3star.png" class="rating" />
        </div>
        <div v-if="worker.averageRating === 2"> 
            <img src="images/2star.png" class="rating" />
        </div>
        <div v-if="worker.averageRating=== 1"> 
            <img src="images/1star.png" class="rating" />
        </div>
        <button type="submit" class="button-profile" @click="sendData(worker.id)">See more</button>        
      </div>
    </div>
  </div>

</template>



<script>
import FetchDataService from "../services/FetchDataService";

export default{  
    name:"DisplayWorkers",

    data() {
    return {
      workers: [],
      id:1
      
    }
    
  },

    methods:{
        fetchWorkers(){
            FetchDataService.getAllWorkers()
            .then(response =>{
                this.workers =response.data
                console.log(response)               
            })
            .catch (error =>{
                if (error.response) {
            console.log(error.response.data);
            console.log(error.response.status);
        }} );
      },

      sendData(workerId){
       
          console.log("mira lo que tengo aca", workerId)
          this.$router.push({name:"SelectedWorker", params: { workerId: workerId }})
        
      }
    },
    mounted() {
    this.fetchWorkers();
    
  },
 
}
</script>

<style>
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
  text-align: center;
  width: 70%;
  display: grid;
  grid-gap: 10px;
  margin-top: 10rem;
  grid-template-columns: repeat(4, minmax(250px, 1fr));
  text-align: center;


}
.card-worker{
  box-sizing: border-box;
  border-radius: 0.5rem;
  display: flex;
  flex-direction: column;
  text-align: center;
  transition: transform 0.3s ease;
  max-width:98%;
  font-weight: bold;
  background-color:white;
  margin-left: 20px;
  border: 1px solid rgb(31, 142, 175);
  line-height: 1; 
}
.profile{
  width: 100px;
  height: 100px;
  margin: auto;
  margin-top: 15px;
}

.rating{
  max-width: 110px;
  height: 25px;
  margin: auto;
  margin-top: 15px;
  margin-bottom: 15px;
  display:inline
}
.rating-container img,
.rating-container span {
    display: inline-block;
    margin-right: 5px;
    
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


</style>