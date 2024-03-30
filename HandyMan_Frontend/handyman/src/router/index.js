import { createWebHistory, createRouter } from "vue-router";
import UserLogin from "../components/UserLogin.vue";
//import WorkerLogin from "../components/WorkerLogin";
import HomePage from "../components/HomePage.vue";
import SingUp from "../components/SingUp.vue";
import DisplayWorkers from "../components/DisplayWorkers.vue";
import WorkerDetails from "../components/WorkerDetails.vue";
import UserDetails from "../components/UserDetails.vue"
import DisplayJobs from "../components/DisplayJobs.vue";
import UserMessages from "../components/UserMessages.vue";
import WorkerMessages from "../components/WorkerMessages.vue"



const routes = [
  {
    name: "homePage",
    component: HomePage,
    path: "/"
  },
  
 {
    name: "UserLogin",
    component: UserLogin,
    path: "/login"
  },

  {
    name: "WorkerDetails",
    component: WorkerDetails,
    path: "/workerdetails"
  },

  {
    name: "userDetails",
    component: UserDetails,
    path: "/userdetails"
  },

  {
    name: "SignUp",
    component: SingUp,
    path: "/singup"
  },
  {
    name: "DisplayWorkers",
    component: DisplayWorkers,
    path: "/workers"
  },
  {
    name: "DisplayJobs",
    component: DisplayJobs,
    path: "/jobs"
  },

  {
    name: "UserMessages",
    component: UserMessages,
    path: "/usermessages"
  },

  {
    name: "WorkerMessages",
    component: WorkerMessages,
    path: "/workermessages"
  }
 
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
