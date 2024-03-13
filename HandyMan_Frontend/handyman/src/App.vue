<template>
  <div id= "app">
    <div>
      <NavBar/>
      <router-view/>
    </div>
  
  </div>
  </template>
  
  <script>
  import NavBar from './components/NavBar.vue';
  
  export default {
    name: 'App',
    components:{
      NavBar
    }
   
    
  }
  </script>
  
  <style>
  
  </style>./components/NavBar.vue
