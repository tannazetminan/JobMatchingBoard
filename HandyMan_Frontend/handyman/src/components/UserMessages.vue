<template>
    <div>
      <h2>Messages for User</h2>
      <ul>
        <li v-for="message in messages" :key="message.id">
          <p>{{ message.content }}</p>
          <p>Sent by: {{ message.worker.name }}</p>
        </li>
      </ul>
    </div>
</template>

<script>
import FetchDataService from "../services/FetchDataService";

function getUserIdFromLocalStorage() {
    const userId = localStorage.getItem('userId');
    if (!userId) {
        console.error("User ID is not available in localStorage.");
        return null;
    }
    return userId;
}


export default {
    data() {
        return {
            messages: []
        };
    },
    mounted() {
        this.fetchMessagesForUser();
    },
    methods: {
        fetchMessagesForUser() {
            const userId = getUserIdFromLocalStorage();
            if (!userId) {
                console.error("User ID is not available in localStorage.");
                return;
            }
            
            FetchDataService.getAllUserMessages(userId)
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