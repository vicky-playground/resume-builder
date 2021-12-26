import axios from "axios";
import store from "../store";
import router from "../router";

const instance = axios.create({
  baseURL: process.env.VUE_APP_BACKEND_URL,
  headers: {
    "Content-Type": "application/json;charset=UTF-8",
    "Cache-Control": "no-cache",
    Accept: "application/json"
  }
});

instance.interceptors.request.use(
  config => {
    let user = localStorage.getItem("user");
    if (user) {
      user = JSON.parse(user);
      if (user.expiration >= Date.now()) {
        config.headers.Authorization = user.jwt;
      } else {
        store.commit("logout");
      }
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

instance.interceptors.response.use(
  response => {
    console.log("ajax ok");
    return response;
  },
  error => {
    if (error.response) {
      if (error.response.status === 401 || error.response.status === 403) {
        store.commit("logout");
        return router.push({ path: "/login" });
      }
    }

    console.log("ajax error");
    return Promise.reject(error);
  }
);

export default instance;
