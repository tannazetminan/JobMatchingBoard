<template>
    <div>
      <h2>Messages for Worker</h2>
      <ul>
        <li v-for="message in messages" :key="message.id">
          <p>{{ message.content }}</p>
          <p>Sent by: {{ message.user.name }}</p>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  import FetchDataService from "../services/FetchDataService";

  function getWorkerIdFromLocalStorage() {
    return localStorage.getItem('workerId');
}


  export default {
    data() {
      return {
        messages: []
      };
    },
    mounted() {
      this.fetchMessagesForWorker();
    },
    methods: {
      fetchMessagesForWorker() {
        const workerId = getWorkerIdFromLocalStorage(); // Implement this function to get worker ID from localStorage
        FetchDataService.getAllWorkerMessages(workerId)
          .then(response => {
            this.messages = response.data;
          })
          .catch(error => {
            console.error("Error fetching messages:", error);
          });
      }
    }
  };
  </script>
  
  <style>
  </style>
  