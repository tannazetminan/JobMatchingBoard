<template>
    <div class = "container-login">
        <img src="images/workers.jpg" class="img-workers" />
        
        <div class="container-form">
            <div class ="container-grettings">
            <h2 class ="grettings">Wellcome to Handyman</h2>
            <img src="images/waving-hand.png" class="img-hands" />
        </div>
        <form @submit.prevent="login">
            <div>
                <label for = "email">EMAIL: </label>
                <input type=" text" id="email" placeholder="Enter your email" v-model="userLogin.email" />
            </div>
            <div>
                <label for = "Password">PASSWORD: </label>
                <input type="password" id="password" placeholder="Enter your password"   v-model="userLogin.password"/>
            </div>
            <div>
                <button type="submit">Login</button>
            </div>
        </form>
       
        <router-link to="/singup" class="nav-link"> Don't  have an  account? Sign up</router-link>

    </div>
  
    </div>
    </template>
    
<script>
import LoginService from "../services/LoginService";
import router from "../router/index.js";

export default {
   
    name:"UserLogin",

    data(){
        return{
            userLogin:{
                email: " ",
                password:''

            }
        }
    },

    methods:{
        login(){
            LoginService.login(this.userLogin)
            .then(response =>{
                let user= response.data;
                console.log(user)
                console.log(response)
                router.push("/workers")
                
            })
            .catch (error =>{
                if (error.response) {
                console.log(error.response.data);
                console.log(error.response.status);
       
        } })
        }
    }
    
}
    </script>

<style scoped>


.container-login {
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
  margin-top: 5rem;
  text-align: left;
 
  
  
}

label{
margin-left: 2rem;
display: inline-block;
color:black;
margin-top: 15px;
text-align: left;

}

input{
  margin-top: 5px;
  width: 80%;
  padding: 0.8rem;
  border-radius: 0.5rem;
  box-sizing: border-box; 
  margin-left: 2rem;
  border-color: #7de20a;
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
    width: 40%;
    text-align: center;
}
.container-grettings{
    margin-top: 50px;
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
</style>