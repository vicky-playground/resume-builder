<template>
  <div class="d-inline-flex mx-5 ">
    <!-- <v-avatar class="mr-10" color="grey darken-1" size="32"></v-avatar> -->
    <div class="mr-10 ml-0">
      <router-link to="/">
        <v-img
          src="../assets/logo.svg"
          height="50"
          max-width="200"
          contain
          content-class
          class="mr-10"
        ></v-img>
      </router-link>
    </div>

    <v-btn
      class="mr-2 font-weight-bold"
      v-for="(item, index) in links"
      :key="index"
      text
      :to="item.link"
      color="black"
      plain
      x-large
    >
      {{ item.text }}
    </v-btn>
  </div>
</template>

<script>
export default {
  data: () => ({
    links: [
      { text: "履歷模版", link: "/template-list" }
      // { text: "履歷", link: "/resume" }
      // { text: "套用資料說明", link: "/apply-info-intro" }
    ]
  }),
  computed: {
    isLogin() {
      return this.$store.state.isLogin;
    }
  },
  watch: {
    isLogin(newCount, oldCount) {
      if (this.$store.state.isLogin) {
        // this.links = [
        //   { text: "帳號設定", link: "/setting" },
        //   { text: "基本資料", link: "/user-info" },
        //   { text: "套用資料說明", link: "/apply-info-intro" },
        //   { text: "套用資料", link: "/apply-info" },
        //   { text: "履歷模版範本", link: "/template-list" }
        // ]
      } else {
        // this.links = [
        //   { text: "帳號設定", link: "/setting" },
        //   { text: "套用資料", link: "/apply-info" },
        //   { text: "履歷模版範本", link: "/template-list" }
        // ]
      }
    }
  },
  methods: {
    logout() {
      this.$store.commit("logout");
      alert("登出成功");
      this.$router.push("/");
    }
  }
};
</script>
