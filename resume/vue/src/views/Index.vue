<template>
  <v-row justify="center" class="white">
    <v-col cols="12" class="pa-0">
      <v-carousel
        hide-delimiter-background
        hide-delimiters
        :show-arrows="false"
        :height="400"
      >
        <v-carousel-item
          v-for="(item, i) in items"
          :key="i"
          :src="item.src"
          reverse-transition="fade-transition"
          transition="fade-transition"
        ></v-carousel-item>
      </v-carousel>
    </v-col>
    <v-col col="9" lg="3" sm="3" class="my-5">
      <v-card class="mx-auto justify-center" max-width="250" :elevation="0">
        <v-img src="../assets/icon01.svg" height="290"></v-img>
        <v-card-title class="d-flex justify-center text-center my-2"
          ><h3>斜槓人生<br />建立多份履歷</h3>
        </v-card-title>
        <v-card-subtitle class="d-flex justify-center text-center pa-0 my-2">
          依據不同職務，建立不同履歷<br />確保每份職務都有專屬的履歷
        </v-card-subtitle>
      </v-card>
    </v-col>
    <v-col col="9" lg="3" sm="3" class="my-5">
      <v-card class="mx-auto" max-width="250" :elevation="0">
        <v-img src="../assets/icon02.svg" height="290"></v-img>
        <v-card-title class="d-flex justify-center text-center my-2"
          ><h3>是履歷表<br />也是個人網站</h3>
        </v-card-title>
        <v-card-subtitle class="d-flex justify-center text-center pa-0 my-2">
          除了是履歷表，也是作品集網站<br />讓你的作品一目瞭然精彩呈現
        </v-card-subtitle>
      </v-card>
    </v-col>
    <v-col col="9" lg="3" sm="3" class="my-5">
      <v-card class="mx-auto justify-center" max-width="250" :elevation="0">
        <v-img src="../assets/icon01.svg" height="290"></v-img>
        <v-card-title class="d-flex justify-center text-center my-2"
          ><h3>多種模版<br />一鍵輕鬆更換</h3>
        </v-card-title>
        <v-card-subtitle class="d-flex justify-center text-center pa-0 my-2">
          一鍵更換履歷模版
        </v-card-subtitle>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import http from "../utils/http";
import axios from "axios";

export default {
  data: () => ({
    username: "",
    password: "",
    src: "",
    show: false,
    items: [
      {
        src: require("../assets/slider01.jpg")
      }
    ]
  }),
  methods: {
    getUser() {
      http.get("/user/all").then(response => {
        console.log(response);
      });
    },
    print() {
      console.log("state: ");
      console.log(this.$store.state);
      console.log("local storage");
      const userData = JSON.parse(localStorage.getItem("user"));
      console.log(userData);
    },
    getImage() {
      axios
        .get(process.env.VUE_APP_BACKEND_URL + "/test/image", {
          responseType: "arraybuffer"
        })
        .then(response => {
          // console.log(response);
          this.src =
            "data:image/jpeg;base64," +
            btoa(
              new Uint8Array(response.data).reduce(
                (data, byte) => data + String.fromCharCode(byte),
                ""
              )
            );
        });
    }
  }
};
</script>
