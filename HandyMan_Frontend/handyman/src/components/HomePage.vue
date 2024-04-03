<template>
  <div class="container-home">
    <div class="container-img">
      <img src="images/background_page.jpg" class="img_back" />
    </div>
    <div class = "container-searchForm">


      <form @submit.prevent="search">
      <div class="form-group">
        <label><div  class="subtitle-worker"  style="font-size: xxx-large; color: seagreen; margin-top: 70px;">Search</div></label>
        <label for="search" class ="label_search">Worker's Name:</label>
        <input type="text" id="search" v-model="searchTerm" placeholder="Search workers...">
      </div>
      <!-- <div class="form-group">
        <label for="category">Category:</label>
        <select id="category" v-model="selectedCategory">
          <option value="" disabled>Select category</option>
          <option value="carpentry">Carpentry</option>
          <option value="plumbing">Plumbing</option>
          <option value="electrical">Electrical</option>
          <option value="Masonry">Masonry</option>
          <option value="Gardening">Gardening</option>
        </select>
      </div> -->
      <div class="form-group">
        <label for="rating">Rating:</label>
        <select id="rating" v-model="selectedRating">
          <option value="">Select rating</option>
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select>
        </div>
      <button type="submit">Search</button>
      <button type="button" @click="resetFilters">Reset filters</button>
    </form>

      <h1 class="subtitle-worker">Top Workers</h1>
      <div class="cards">
        <div v-for="worker in topWorkers" :key="worker.id" class="card-worker">
          <!-- <img :src="`images/${worker.profileImage}`" class="profile" /> -->
          <img src="images/user.png" class="profile" />
          <p>{{ worker.id }}</p>
          <p>{{ worker.username }}</p>
          <p>{{ worker.description }}</p>
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
          <p>Availability: {{ worker.availability }}</p>
          <button type="button" class="button-profile">See more</button>
        </div>
      </div>
    </div>
  </div>

</template>


<script>
import FetchDataService from "../services/FetchDataService";


export default {

  name: "HomePage",

  data() {
    return {
      topWorkers: [],
      searchTerm: '',
      selectedRating: ''
    };
  },


  methods: {
    fetchTopWorkers() {
      FetchDataService.getAllWorkers() // Assuming this method fetches all workers
        .then((response) => {
          // Sort workers by averageRating in descending order
          const sortedWorkers = response.data.sort((a, b) => b.averageRating - a.averageRating);
          // Select the top 3 highest-rated workers
          this.topWorkers = sortedWorkers.slice(0, 3);
        })
        .catch((error) => {
          console.error("Error fetching workers:", error);
        });
    },
    search() {
  // Fetch all workers
  FetchDataService.getAllWorkers()
    .then((response) => {
      // Store all workers
      const allWorkers = response.data;
      // Apply filtering logic to all workers
      const filteredWorkers = allWorkers.filter(worker => {
        const nameMatch = worker.username.toLowerCase().includes(this.searchTerm.toLowerCase());
        const ratingMatch = !this.selectedRating || worker.averageRating === parseInt(this.selectedRating);
        return nameMatch && ratingMatch;
      });
      // Update the topWorkers array with filtered workers
      this.topWorkers = filteredWorkers;
    })
    .catch((error) => {
      console.error("Error fetching workers:", error);
    });
  },

    filterWorkers() {
      // Filter workers based on search criteria
      this.topWorkers = this.topWorkers.filter(worker => {
        const nameMatch = worker.username.toLowerCase().includes(this.searchTerm.toLowerCase());
        const ratingMatch = !this.selectedRating || worker.averageRating >= parseInt(this.selectedRating);
        return nameMatch && ratingMatch;
      });
    },

    resetFilters() {
      this.fetchTopWorkers();

    }

  },
  mounted() {
    this.fetchTopWorkers();
  },
};
</script>



<style scoped >
.container-home{
    width: 100%;
    text-align: center;
    margin-top: 50px;
}

.container-img {
  width: 80%; 
  height: 200px; 
  height: 400px;
  margin: auto;
}
.container-searchForm{
  margin-top: 50px;
  margin-left: 200px;
}
form{

  width: 20%;
  height: 380px;
  border-radius: 0.5rem;
  float: left;
  margin-right: 30px;
  margin-top: 10px;
  
}
.img_back{

 width: 100%; 
  height: 100%; 
  object-fit:cover; 
  object-position: center  28%; 

}
label{
margin-left: 2rem;
display: inline-block;
color:rgb(28, 29, 29)

}

input, select{

  width: 80%;
  padding: 0.5rem;
  border-radius: 4px;
  box-sizing: border-box; 
  margin-left: 2rem;
  border-color: #7de20a;
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

.form-group {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 15px; 
  
}

.label_search{
  margin-top: 25px;
}
.filters{
  text-align: left;
  margin-left: 35px;
  color:white
}

.cards{
  margin: auto;
  text-align: center;
  width: 70%;
  display: grid;
  grid-gap: 10px;
  margin-left: 10px;
  grid-template-columns: repeat(3, minmax(250px, 1fr));
 
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
  margin-bottom: 50px;
}
.profile{
  width: 100px;
  height: 100px;
  margin: auto;
  margin-top: 15px;
}

.rating{
  max-width: 80px;
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