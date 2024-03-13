<template>
    <div class = "container-singup">
        <img src="images/workers2.jpg" class="img-workers" />
        
        <div class="container-form">
            <div class ="container-grettings">
            <h2 class ="grettings">Wellcome to Handyman</h2>
            <img src="images/waving-hand.png" class="img-hands" />
        </div>
        <form>
            <div class = "names">
                <label for = "username" >First Name: </label>
                <input type=" text" id="username" placeholder="Enter your name"  />
                <label for = "username">Last Name: </label>
                <input type=" text" id="username" placeholder="Enter your name" />
            </div>
            <div>
                <label for = "email">Email: </label>
                <input type=" text" id="studentId" placeholder="Enter your email" />
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


    </div>
  
    </div>
    </template>
    
<script>

export default {
   
    name:"SignUp"
    
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

}

form{

  width: 50%;
  height: 250px;
  border-radius: 0.5rem;
  margin-left: 15rem;
  margin-top: 1rem;
  text-align: left;
 
  
  
}

label{
margin-left: 2rem;
display: inline-block;
color:black;
margin-top: 15px;
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
  padding: 0.8rem;
  
}
.container-form{
   /* background-color: #e27713;*/
    width: 40%;
    text-align: center;
   /* background-color: #7de20a;**/
    height: 800px;
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
    height: 150px;
}
.names.label{
    display: inline;
}
</style>