<template>
    <div class = "nav-container ">
        
        <nav class="navbar">
            <div class="container">
                <router-link to="/" class="brand">
                    <span class="text-2xl font-semibold">HandyMan</span>
                </router-link>

                <ul class="nav-links">
                    <li>
                        <router-link to="/" class="nav-link">Home</router-link>
                    </li>
                    <li>
                        <router-link to="/singup" class="nav-link">Sign up</router-link>
                    </li>
                   
                    <li>
                        <router-link to="/login" class="nav-link">Login</router-link>
                    </li>
                    <li>
                        <router-link to="/workers" class="nav-link">Workers</router-link>
                    </li>
                </ul>
            </div>
         </nav>
       
    </div>


</template>


<script>


export default{
    
    name:"NavBar"
}
</script>

<style>
.navbar {

  border-radius: 0.5rem;
  padding: 1rem;
  margin: auto;
  width: 90%;
}

.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.brand {
  text-decoration: none;
  color: #079129;
  font-size: 35px;

}

.nav-links {
  list-style: none;
  display: flex;
  align-items: center;
}

.nav-link {
  text-decoration: none;
  color: #0a0a0a;
  margin-left: 1rem;
  font-size: 25px;
  margin-right: 2rem;
  
  
}

.nav-link:hover {
  color: #ec991c;
}


</style>