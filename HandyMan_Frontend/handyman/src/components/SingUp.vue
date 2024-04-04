<template>
  <div class = "container-singup">
      <img src="images/workers2.jpg" class="img-workers" />
      
      <div class="container-form">
          <div class ="container-grettings">
          <h2 class ="grettings">Wellcome to Handyman</h2>
          <img src="images/waving-hand.png" class="img-hands" />
      </div>
     <div class= "container-type-user ">
          <button @click="userType = 'worker'" class="type-user ">I am worker</button>
          <button @click="userType = 'empresa'" class="type-user2">I'm hiring </button>
      </div>
      
      <form v-if="userType === 'worker'" @submit.prevent="submitWorkerForm">
        <div class="names">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" v-model="worker.firstname" placeholder="Enter your first name" />
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" v-model="worker.lastname" placeholder="Enter your last name" />
      </div>
      <div>
        <label for="email">Email:</label>
        <input type="text" id="email" v-model="worker.email" placeholder="Enter your email" />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="worker.password" placeholder="Enter your password" />
      </div>
      <div>
        <label for="location">Location:</label>
        <input type="text" id="location" v-model="worker.location" placeholder="Enter your location" />
      </div>
      <div>
        <label for="description">Description:</label>
        <textarea v-model="worker.description" placeholder="Describe your profile"></textarea>
      </div>
      <div class="form-group">
        <label for="category">Category:</label>
        <select id="category" v-model="worker.category">
          <option value="" disabled>Select category</option>
          <option value="carpentry">Carpentry</option>
          <option value="plumbing">Plumbing</option>
          <option value="electrical">Electrical</option>
          <option value="masonry">Masonry</option>
          <option value="gardening">Gardening</option>
        </select>
      </div>
      <div>
        <button type="submit">Sign up</button>
      </div>
    
    </form>

      <form v-else-if="userType === 'empresa'" @submit.prevent="submitUserForm">
      <div>
        <label for="firtsname">First Name:</label>
        <input type="text" id="firstname"  v-model="user.firstname" placeholder="Enter your first name" />
      </div>
      <div>
        <label for="lastname">Last Name:</label>
        <input type="text" id="lastname" v-model="user.lastname" placeholder="Enter your last name" />
      </div>
      <div>
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="user.email" placeholder="Enter your email" />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="user.password" placeholder="Enter your password" />
      </div>
      <div>
        <label for="credit">Credit:</label>
        <input type="text" id="credit" v-model="user.credit" placeholder="Enter your credit" />
      </div>
      <div>
          <button type="submit">Sign up </button>
      </div>
    </form>



  </div>

  </div>
</template>

<script>
import FetchDataService from "../services/FetchDataService";
import bcrypt from 'bcryptjs';


// Define the capitalizeFirstLetter function outside of the component
function capitalizeFirstLetter(string) {
return string.charAt(0).toUpperCase() + string.slice(1);
}

export default {
name: "SignUp",
data() {
  return {
    userType: null,
    worker: {
      username: "",
      firstname: "",
      lastname: "",
      email: "",
      password: "",
      location: "",
      description: "",
      category: ""
    },
    user: {
      username: "",
      firstname: "",
      lastname: "",        
      password: "",
      email: "",
      credit: 0 
    },
    successMessage: null,
    errorMessage: null
  };
},
methods: {
  async submitWorkerForm() {
    // Combine firstName and lastName to create the username
    // this.worker.username = "worker_" + capitalizeFirstLetter(this.worker.firstname) + capitalizeFirstLetter(this.worker.lastname);
    this.worker.username = capitalizeFirstLetter(this.worker.firstname) + "." + capitalizeFirstLetter(this.worker.lastname);


    // Hash the password using bcrypt
    const hashedPassword = await bcrypt.hash(this.worker.password, 10);

    // Set the hashed password to the worker object
    this.worker.password = hashedPassword;

    FetchDataService.createWorker(this.worker)
      .then(response => {
        console.log("Worker created successfully:", response.data);
        this.successMessage = "Worker created successfully!";
        this.errorMessage = null;
        // Redirect to the home page after a successful form submission
        this.$router.push('/login');
      })
      .catch(error => {
        console.error("Error creating worker:", error);
        this.errorMessage = "Error creating worker. Please try again.";
        this.successMessage = null;
      });
  },
  async submitUserForm() {
    // Combine firstName and lastName to create the username
    // this.user.username = "user_" + capitalizeFirstLetter(this.user.firstname) + capitalizeFirstLetter(this.user.lastname);
    this.user.username = capitalizeFirstLetter(this.user.firstname) + "." + capitalizeFirstLetter(this.user.lastname);


    // Hash the password using bcrypt
    const hashedPassword = await bcrypt.hash(this.user.password, 10);

    // Set the hashed password to the user object
    this.user.password = hashedPassword;
    
    FetchDataService.createUser(this.user)
      .then(response => {
        console.log("User created successfully:", response.data);
        this.successMessage = "User created successfully!";
        this.errorMessage = null;
        // Redirect to the home page after a successful form submission
        this.$router.push('/login');
      })
      .catch(error => {
        console.error("Error creating user:", error);
        this.errorMessage = "Error creating user. Please try again.";
        this.successMessage = null;
      });
  }
}
};

</script>



<style scoped>
.container-singup {
  display: flex;
  align-items: center;
  justify-content: center;
}
.img-workers{
  float:left;
  margin-left: 5rem;
  width: 50%;
  margin-bottom: 20%;
  height: 600px;

}

form{

width: 80%;
height: 650px;
border-radius: 0.5rem;
margin:auto;
text-align: left;

}


label{
margin-left: 2rem;
display: block;
color:black;
margin-top: 8px;
text-align: left;

}

input, textarea, select{
margin-top: 5px;
width: 80%;
padding: 0.8rem;
border-radius: 0.5rem;
box-sizing: border-box; 
margin-left: 2rem;
border-color: #7de20a;
}

select{
  padding: 0.5rem;
}

button {
padding: 0.8rem ;
background-color: #e27713;
color: white;
border: none;
border-radius: 4px;
cursor: pointer;
margin-top: 20px;
margin-left: 2rem;
font-weight: bold;
width: 80%;


}
.container-form{
 /* background-color: #e27713;*/
  width: 40%;
  text-align: center;
  /*background-color: #df8d1b;*/
  height: 1000px;
}
.container-grettings{
 /*background-color: #7de20a;*/
  margin-top: 10px;
  display: inline-block;
  margin-right: 5px;
}
.grettings{
  display: inline-block;
}
.img-hands{
 width: 60px;
height: 60px;
margin: auto;
margin-top: 15px;
display:inline
}
.message{
  margin: auto;
  margin-left: 7rem;
  color:#5b7404
}
.nav-link{
  font-size: 20px;

  padding: 0.5rem;
  margin-top: 15px;
  color:#5b7404;
  font-weight: bold;
  text-align: center;
  margin-left: 5rem;
}
textarea{
  height: 100px;
}
.names.label{
  display: inline;
}
.container-type-user{
  text-align: center;
  margin-right: 40px;
}
.type-user, .type-user2{
  width: 180px;
  height: 100px;
  background-color: blue;
  display: inline-block;
  margin-bottom: 10px;
  margin-right: 10px;
 
}
.type-user2{
  margin-bottom: 0px;
  margin-top: 10px;
}
</style>