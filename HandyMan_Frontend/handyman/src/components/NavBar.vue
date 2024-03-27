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
            <li >
                <router-link to="/workers" class="nav-link">Workers</router-link>
            </li>
            <li >
                <router-link to="/jobs" class="nav-link">Jobs</router-link>
            </li>
            <li  v-if="!isLoggedIn" >
                <router-link to="/singup" class="nav-link">Sign up</router-link>
            </li>
            
            <li v-if="!isLoggedIn">
                <router-link to="/login" class="nav-link">Hiring Login</router-link>
            </li>
            <li v-if="!isLoggedIn">
                <router-link to="/workerlogin" class="nav-link">Worker Login</router-link>
            </li>
            <li v-if="isLoggedIn">
                <router-link to="/" class="nav-link"><a href="#" @click="logout">Logout</a></router-link>
            </li>
            <!-- <li><router-link to="/" class="nav-link">Home</router-link></li>
            <li v-if="!isLoggedIn" class="nav-link"><router-link to="/SignUp">Sign up</router-link></li>
            <li v-if="!isLoggedIn" class="nav-link"><router-link to="/Login">Hiring Login</router-link></li>
            <li v-if="!isLoggedIn" class="nav-link"><router-link to="/WorkerLogin">Worker Login</router-link></li>
            <li v-if="isLoggedIn" class="nav-link"><router-link to="/Workers">Workers</router-link></li>
            <li v-if="isLoggedIn" class="nav-link"><a href="#" @click="logout">Logout</a></li> -->

        </ul>
      </div>
    </nav>      
  </div>

</template>



<script>
export default {
  data() {
    return {
      isLoggedIn: false
    };
  },
  mounted() {
    // Check if token exists in local storage
    if (localStorage.getItem('token')) {
      this.isLoggedIn = true;
    }
  },
  methods: {
    logout() {
      // Remove token from local storage
      localStorage.removeItem('token');
      
      // Update isLoggedIn state
      this.isLoggedIn = false;
      
      // Redirect to home page or login page
      this.$router.push('/');
    }
  }
};
</script>

<!-- <script>
export default {
  data() {
    return {
      isLoggedIn: false
    };
  },
  created() {
    this.isLoggedIn = localStorage.getItem('token') !== null; // Check if token exists in localStorage
  },
  watch: {
    isLoggedIn(newValue) {
      if (!newValue) {
        this.$router.push('/'); // Redirect to home page or login page if user logs out
      }
    }
  },
  methods: {
    logout() {
      localStorage.removeItem('token'); // Remove token from localStorage
      this.isLoggedIn = false; // Update isLoggedIn state
    }
  }
};
</script> -->


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