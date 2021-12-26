import Vue from "vue";
import Vuex from "vuex";
import Vuelidate from "vuelidate";

Vue.use(Vuex);
Vue.use(Vuelidate);

const store = new Vuex.Store({
  state: {
    isLogin: false,
    user: {
      jwt: "",
      id: "",
      account: ""
    }
  },
  mutations: {
    login(state, user) {
      state.user = user;
      state.isLogin = true;
      localStorage.setItem("user", JSON.stringify(state.user));
    },
    logout(state) {
      state.user = {};
      state.isLogin = false;
      localStorage.removeItem("user");
    }
  }
});

let user = localStorage.getItem("user");
if (user) {
  user = JSON.parse(user);
  store.commit("login", user);
}

export default store;
