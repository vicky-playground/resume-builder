<template>
  <v-app>
    <v-app-bar app elevate-on-scroll color="white" flat>
      <v-container class="px-0 py-0 fill-height justify-start ml-10">
        <theNavigator />
      </v-container>
      <v-spacer></v-spacer>

      <v-menu offset-y v-if="isLogin">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            class="mr-2 justify-end white--text"
            color="blue darken-2"
            large
            depressed
            v-bind="attrs"
            v-on="on"
          >
            我的選單
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            v-for="(item, index) in items"
            :key="index"
            link
            @click="routerTo(item.link)"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-btn
        class="mr-2 justify-end white--text"
        large
        depressed
        color="blue darken-2"
        @click="routerTo('/signup')"
        v-if="!isLogin"
      >
        帳號註冊
        <v-icon right dark>
          mdi-login
        </v-icon>
      </v-btn>
    </v-app-bar>
    <v-main class="grey lighten-3">
      <v-container fluid>
        <router-view />
      </v-container>
    </v-main>
    <v-footer color="primary" padless>
      <v-row justify="center" no-gutters>
        <v-col class="grey darken-4 py-1 text-center white--text" cols="12">
          <a
            @click="routerTo('/privacy')"
            class="white--text"
            style="text-decoration: none;"
            >隱私權政策</a
          >｜
          <a
            @click="routerTo('/terms')"
            class="white--text"
            style="text-decoration: none;"
            >使用條款</a
          >｜
          <a
            @click="routerTo('/faq')"
            class="white--text"
            style="text-decoration: none;"
            >常見問題</a
          >
        </v-col>
        <v-col class="grey darken-4 py-1 text-center white--text" cols="12">
          Copyright ©{{ new Date().getFullYear() }} — ACE RESUME 履歷存摺. All
          rights reserved.
        </v-col>
      </v-row>
    </v-footer>
  </v-app>
</template>

<script>
// @ is an alias to /src
import theNavigator from "@/components/theNavigator.vue";
export default {
  created: function() {},
  components: {
    theNavigator
  },
  data: () => ({
    items: [
      { title: "基本資料", link: "/setting" },
      { title: "我的履歷表", link: "/resume-list" },
      { title: "個人網站", link: "/website" },
      { title: "登出", link: "/logout" }
    ]
  }),
  computed: {
    isLogin() {
      return this.$store.state.isLogin;
    }
  },
  watch: {
    isLogin(newCount, oldCount) {}
  },
  methods: {
    routerTo(path) {
      if (path === "/logout") {
        this.$store.commit("logout");
        alert("登出成功");
        if (this.$router.currentRoute.path !== "/") {
          this.$router.push("/");
        }
      } else if (this.$router.currentRoute.path !== path) {
        this.$router.push({ path });
      }
    }
  }
};
</script>
