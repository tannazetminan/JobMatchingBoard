import { createWebHistory, createRouter } from "vue-router";
import UserLogin from "../components/UserLogin.vue";
import WorkerLogin from "../components/WorkerLogin";
import HomePage from "../components/HomePage.vue";
import SingUp from "../components/SingUp.vue";
import DisplayWorkers from "../components/DisplayWorkers.vue";

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
    name: "WorkerLogin",
    component: WorkerLogin,
    path: "/workerlogin"
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
  }
 
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
