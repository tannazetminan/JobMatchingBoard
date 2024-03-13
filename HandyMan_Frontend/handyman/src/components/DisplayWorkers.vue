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
      <img :src="'images/' + worker.image" class="profile" />
      <p>{{ worker.name }}</p>
      <p>{{ worker.job }}</p>
      <div class="rating-container">
        <img src="images/star.png" class="rating" /><span>{{ worker.rating }}</span>
      </div>
      <p>Availability: {{ worker.availability }}</p>  
      <button type="submit" class="button-profile">See more</button>        
    </div>
  </div>
   </div>

</template>

<script>


export default{
    
    name:"DisplayWorkers",
    data() {
    return {
      workers: [
        { id: 1, name: 'Jon Smith', job: 'Plumber', rating: 4.5, availability: '12/03/2020', image: 'people.png' },
        { id: 2, name: 'Jane Doe', job: 'Electrician', rating: 4.8, availability: '14/03/2020', image: 'user.png' },
        { id: 3, name: 'Emily Johnson', job: 'Carpenter', rating: 4.2, availability: '16/03/2020', image: 'user.png' },
        { id: 4, name: 'Michael Brown', job: 'Mason', rating: 4.0, availability: '18/03/2020', image: 'people.png' },
        { id: 5, name: 'Sophia Williams', job: 'Gardener', rating: 4.6, availability: '20/03/2020', image: 'people.png' }
        
      ]
    }
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
  grid-template-columns: repeat(5, minmax(250px, 1fr));
  text-align: center;


}
.card-worker{
  box-sizing: border-box;
  border-radius: 0.5rem;
  display: flex;
  flex-direction: column;
  text-align: center;
  transition: transform 0.3s ease;
  max-width:80%;
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
  width: 18px;
  height: 18px;
  margin: auto;
  margin-top: 15px;
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