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
        <form v-if="userType === 'worker'">
            <div class = "names">
                <label for = "username" >First Name: </label>
                <input type=" text" id="username" placeholder="Enter your name"  />
                <label for = "username">Last Name: </label>
                <input type=" text" id="username" placeholder="Enter your name" />
            </div>
            <div>
                <label for = "email">Email: </label>
                <input type=" text" id="userID" placeholder="Enter your email" />
            </div>
            <div>
                <label for = "Password">Password: </label>
                <input type="password" id="password" placeholder="Enter your password" />
            </div>
            <div>
                <label for = "description">Description: </label>
                <textarea placeholder=" Describe your profile"></textarea>

            </div>
            <div class="form-group">
                  <label for="category">Category:</label>
                  <select id="category" v-model="selectedCategory">
                  <option value="" disabled>Select category</option>
                  <option value="carpentry">Carpentry</option>
                  <option value="plumbing">Plumbing </option>
                  <option value="electrical">Electrical</option>
                  <option value="Masonry">Masonry</option>
                  <option value="Gardening">Gardening</option>
                
                  </select>
              </div>
            
            <div>
                <button type="submit">Sign up </button>
            </div>
        </form>

        <form v-else-if="userType === 'empresa'">
        <div>
          <label for="firtsname">First Name:</label>
          <input type="text" id="firstname" placeholder="Enter your first name" />
        </div>
        <div>
          <label for="lastname">Last Name:</label>
          <input type="text" id="lastname" placeholder="Enter your last name" />
        </div>
        <div>
          <label for="email">Email:</label>
          <input type="text" id="email" placeholder="Enter your email" />
        </div>
        <div>
          <label for="password">Password:</label>
          <input type="password" id="password" placeholder="Enter your password" />
        </div>
        <div>
          <label for="credit">Credit:</label>
          <input type="text" id="credit" placeholder="Enter your credit" />
        </div>
        <div>
            <button type="submit">Sign up </button>
        </div>
      </form>



    </div>
  
    </div>
    </template>
    
<script>

export default {
   
    name:"SignUp",

    data() {
    return {
      userType: null 
    };
  }

    
}
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