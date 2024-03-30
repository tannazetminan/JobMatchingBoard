<template>
  <div class="container">
    <h2>Messages for User</h2>
    <div class="message-list">
      <div v-for="message in messages" :key="message.id" class="message">
        <div class="message-header">
          <p>Sent by: {{ message.sender_name }}</p>
          <p>Date: {{ formatDate(message.timestamp) }}</p>
        </div>
        <div class="message-content">
          <p>{{ message.content }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FetchDataService from "../services/FetchDataService";

export default {
    name: "DisplayMessage",

    data() {
        return {
            messages: []
        };
    },

    methods: {
        fetchMessages() {
            let id = localStorage.getItem('userId')
            let fullName ;
            this.fullName = localStorage.getItem('fullName')
            console.log(fullName);
            let userType = localStorage.getItem('userType');
            if (userType == "user") {
                FetchDataService.getAllUserMessages(id)
                .then(response => {
                    this.messages = response.data;
                })
                .catch(error => {
                    console.error("Error fetching messages:", error);
                });
            } else if (userType == "worker") {
                console.error("User ID is not available in localStorage.");
                return;
            }          
        },
        formatDate(timestamp) {
            return new Date(timestamp).toLocaleString();
        },
    },

    mounted() {
        // Check if token exists in local storage
        if (localStorage.getItem('token')) {
        this.fetchMessages();
        }
    }
};
</script>


<style scoped>
.container {
  width: 80%;
  margin: auto;
  padding: 20px;
}

.message-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.message {
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 20px;
  background-color: #f9f9f9;
}

.message-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.message-content {
  line-height: 1.6;
}
</style>