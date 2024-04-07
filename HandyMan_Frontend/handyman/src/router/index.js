import { createWebHistory, createRouter } from "vue-router";
import UserLogin from "../components/UserLogin.vue";
import HomePage from "../components/HomePage.vue";
import SingUp from "../components/SignUp.vue";
import DisplayWorkers from "../components/DisplayWorkers.vue";
import WorkerDetails from "../components/WorkerDetails.vue";
import UserDetails from "../components/UserDetails.vue"
import DisplayJobs from "../components/DisplayJobs.vue";
import SelectedWorker from "../components/SelectedWorker.vue"
import DisplayMessages from "../components/DisplayMessages.vue"


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
    name: "DisplayMessages",
    component: DisplayMessages,
    path: "/messages"
  },

  {
    name: "SelectedWorker",
    component: SelectedWorker,
    path: "/selectedworker/:workerId"
  }
 
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
