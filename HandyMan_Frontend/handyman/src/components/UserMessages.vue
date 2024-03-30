<template>
    <div class="container-cards">
        
        <div class="cards-description">
           
            <div class ="personal-data">
                <img src="images/people.png" class="profile" />  
                <p class="data-description">Welcome {{ this.fullName }}</p>
            </div>
            <div class ="container-form">
                <div class="ctn-title">
                    <h2 class=" title ">New message</h2>
                </div>
                <form @submit.prevent="submitForm">
                    <div class="container-lbl">
                        <label for="description">To: </label>
                        <input type="text" id="email"  />
                    </div>
                    <div class="container-lbl">
                        <label for="description">Text:</label>
                        <textarea></textarea>
                    </div>
                    <div class=" container-btn">
                        <button type="submit">Send</button>
                    </div>
                </form>
            </div>          
        </div>
    <div class="container">
      <div class="message-list">
        <div v-for="message in messages" :key="message.id" class="message">
          <div class="message-header">
            <img src="../../public/images/people.png" class="avatar" alt="Avatar">
            <div class="sender-info">
              <p class="sender-name">{{ message.worker.username }} 
                 <span class="timestamp">{{ formatDate(message.timestamp) }}</span>
              </p>

            </div>
          </div>
          <div class="message-content">
            <p>{{ message.content }} {{ message.content }}{{ message.content }}</p>
          </div>
        </div>
      </div>
    </div>
    </div>
</template>
  
  <script>
  import FetchDataService from "../services/FetchDataService";
  
  export default {
      name: "userMessage",
  
      data() {
          return {
              messages: []
          };
      },
  
      methods: {
          fetchMessages() {
              let id = localStorage.getItem('userId')
              let sender;
              let fullName;
              this.fullName = localStorage.getItem('fullName')
              console.log(fullName);
              console.log(sender);
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
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 20px;
    background-color: #f9f9f9;
  }
  
  .message-header {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
  }
  
  .avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
  }
  
  .sender-info {
    display: flex;
    flex-direction: column;
  }
  
  .sender-name {
    font-weight: bold;
    margin: 0;
  }
  
  .timestamp {
    color: #888;
    margin-left: 40px;
    margin-right: 0px;
    max-width: auto;
    width: auto;
  }
  
  .message-content {
    line-height: 1.6;
  }
  .container-cards{
      width: 95%;
      margin: auto;
      overflow: auto;
      /*background-color: aqua;*/
      display: flex;
  }
  
  .input-search{
      margin: auto;
     
      text-align: left;
      margin-bottom: 25px;
  }
  input{
      width: 80%;
      height: 30px;
      border-radius: 0.8rem;
      margin-left: 25px;
      background-size: 20px; 
      background-repeat: no-repeat;
      margin-top: 20px;
      
  }
  .profile{
      display: block;
      margin-top: 25px;
      height: 150px;
      width: 150px;
      
  }
  .cards-description{
    text-align: center;
    width: 20%;
    height: 850px;
    align-items: flex-start;
    border-radius: 0.5rem;
    
  
  }
  
  .card-jobs{
      width: 70%;
      text-align: center;
      margin-left: 100px;  
  
  }
  
  
  .job{
      margin-left: 20px;
      margin-bottom: 20px;
      width: 80%;
      border-radius: 0.5rem;
      text-align: left;
      background-color: rgb(229, 236, 238);
      padding: 5px;
      font-size: 20px;
      
  }
  
  .desc-job{
      margin-left: 30px;
      font-size: 18px;
  }
  
  .cards{
   
      background-color: rgb(121, 168, 194);
      align-items: flex-start;
      
     
  }
  .personal-data{
     
      background-color: rgb(230, 239, 247);
      border-radius: 0.5rem;
      padding: 5px;
      font-size: 18px;
      margin-top: 15px;
  }
  .data-description{
      margin-bottom: 15px;
      padding: 2px;
      font-size: 20px;
     
  }
  .setting{
      height: 350px;
      margin-bottom: 50px;
      background-color: rgb(230, 239, 247);
      border-radius: 0.5rem;
      margin-top: 15px;
      padding: 5px;
      font-size: 20px;
      text-align: left;
   
  }
  
  
  .title-jobs{
  text-align: left;
  }
  .fetchData{
      margin-left: 30px;
      margin-bottom: 40px;
      font-size: 18px;
      background-color: transparent;
      border: none;
      color: rgb(60, 172, 15); 
      cursor: pointer;
      font-size: inherit;
      padding: 0; 
      font-weight: bold;
      font-size: 25px;
  }
  .edit{
      width: 30px;
      height: 40px;
  }
  .edit-preferences{
      display: flex
  }
  h2{
      margin-top: 15px;
      font-size: 25px;
      width: 90%;
      text-align: center;
  }
  
  .desc-job{
      font-size: 20px;
  }
  .desc-jobs1{
      font-size: 20px;
     text-align: right;
     margin-right: 15px;
     color:rgb(85, 80, 76)
  
  }
  .container-apply-btn{
      text-align: right;
  }
  .apply-btn{
      margin-left: 30px;
      margin-bottom: 20px;
      font-size: 18px;
      background-color: transparent;
      border: none;
      color: rgb(214, 132, 65); 
      cursor: pointer;
      font-size: inherit;
      padding: 0; 
      font-weight: bold;
      font-size: 20px;
      margin-right: 15px;
      
      
  }
.container-form{
    background-color: #dde6ee;
    height: 450px;
    border-radius: 0.5rem; 
    font-size: 20px;
    margin: auto;
    margin-top: 10px;
    
  }
form{
padding: 2px;
width: 80%;
height: 350px;
border-radius: 0.5rem;
margin: auto;
text-align: center;
background-color: #205d96;

}
.ctn-title{
    margin-top: 25px; 
    padding: 0.5px;
}
.title{
    margin-top: 25px;
    padding: 0.5px;
}
input{

width: 90%;
padding: 0.5rem;
border-radius: 4px;
box-sizing: border-box; 
border-color: #55970a;
margin:auto;
margin-top:2px;

}

button {
  padding: 0.5rem 1rem;
  background-color: #e27713;
  color: black;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
  margin-right: 5px;
  font-weight: bold;
 
  
  
}
textarea{
  height: 100px;
  width:90%;
 
  border-radius: 4px;
    box-sizing: border-box; 
    margin-left: 2rem;
    border-color: #7de20a;
    margin:auto;
    margin-top:2px;
}


label{

display: inline-block;
color:black;
text-align: left;
margin:auto;
margin-top:2px;
margin-bottom: 2px;
color: blakc

}
.container-lbl{
    text-align: left;
    margin-left: 15px;
    margin-bottom: 2px;
    margin-top:10px;
}
  </style>