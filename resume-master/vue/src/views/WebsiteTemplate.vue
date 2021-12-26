<template>
  <v-row justify="center" class="white">
    <v-col justify="center" cols="12" class="pa-0 justify-center text-center">
      <v-col
        v-bind:style="{ 'background-image': 'url(' + background + ')' }"
        col="12"
        lg="12"
        sm="12"
        class="my-1 d-flex justify-center text-center"
      >
        <v-avatar class="profile my-5" color="grey" size="225" circle>
          <v-img :src="image"> </v-img>
        </v-avatar>
      </v-col>
      <!-- 基本資料 -->
      <v-col
        justify="center"
        cols="12"
        class="ma-0 py-5 justify-center text-center grey lighten-4"
      >
        <v-col
          col="12"
          lg="12"
          sm="12"
          class="my-1 d-flex justify-center text-center"
        >
          <h2 class="d-flex justify-center text-center my-2">
            基本資料
          </h2>
        </v-col>
        <v-col
          cols="10"
          justify="center"
          class="ma-0 d-flex flex-wrap mx-auto justify-center text-center"
        >
          <v-col col="12" lg="4" sm="4" class="my-1">
            <v-icon class="mr-1">mdi-account</v-icon>姓名：{{ user.name }}
          </v-col>
          <v-col col="12" lg="4" sm="4" class="my-1">
            <v-icon class="mr-1">mdi-calendar-month</v-icon>生日：{{
              user.birthday
            }}
          </v-col>
          <v-col col="12" lg="4" sm="4" class="my-1">
            性別：{{ user.sex }}
          </v-col>
          <v-col col="12" lg="4" sm="4" class="my-1">
            <v-icon class="mr-1">mdi-phone</v-icon>電話：{{ user.phone }}
          </v-col>
          <v-col col="12" lg="4" sm="4" class="my-1">
            <v-icon class="mr-1">mdi-email</v-icon>E-mail：{{ user.email }}
          </v-col>
          <v-col col="12" lg="4" sm="4" class="my-1">
            <v-icon class="mr-1">mdi-home-city</v-icon>聯絡地址：{{
              user.address
            }}
          </v-col>
        </v-col>
      </v-col>

      <!-- 自我簡介 & 個人連結 -->
      <v-col
        justify="center"
        cols="12"
        class="ma-0 py-5 justify-center text-center white"
      >
        <v-col
          col="12"
          lg="12"
          sm="12"
          class="my-1 d-flex justify-center text-center"
        >
          <h2 class="d-flex justify-center text-center my-2">
            自我簡介
          </h2>
        </v-col>
        <v-col
          cols="8"
          justify="center"
          class="ma-0 d-flex justify-center mx-auto text-center"
        >
          <h4>
            {{ user.introduction }}
          </h4>
        </v-col>
        <v-col cols="10" class="ma-0 d-flex justify-center mx-auto text-center"
          ><v-divider inset></v-divider
        ></v-col>
        <v-col
          cols="10"
          justify="center"
          class="ma-0 d-flex flex-wrap mx-auto justify-center text-center"
        >
          <v-col
            col="12"
            lg="3"
            sm="3"
            class="my-1 text--blue"
            v-for="(item, index) in user.links"
            :key="index"
          >
            <v-icon color="blue" class="mr-1" v-if="item.platform == 'FB'"
              >mdi-facebook</v-icon
            >
            <a
              href="#"
              class="text-decoration-none"
              v-if="item.platform == 'FB'"
              >{{ item.url }}</a
            >
            <v-icon color="blue" class="mr-1" v-if="item.platform == 'Linkedin'"
              >mdi-linkedin</v-icon
            >
            <a
              href="#"
              class="text-decoration-none"
              v-if="item.platform == 'Linkedin'"
              >{{ item.url }}</a
            >
            <v-icon color="blue" class="mr-1" v-if="item.platform == 'IG'"
              >mdi-instagram</v-icon
            >
            <a
              :href="'https://www.instagram.com/' + item.url"
              class="text-decoration-none"
              target="_blank"
              v-if="item.platform == 'IG'"
              >{{ item.url }}</a
            >
            <v-icon color="blue" class="mr-1" v-if="item.platform == '個人網站'"
              >mdi-web</v-icon
            >
            <a
              :href="item.url"
              class="text-decoration-none"
              target="_blank"
              v-if="item.platform == '個人網站'"
              >{{ item.url }}</a
            >
          </v-col>
        </v-col>
      </v-col>

      <!-- 教育 -->
      <v-col
        justify="center"
        cols="12"
        class="ma-0 py-5 justify-center text-center grey lighten-4"
      >
        <v-col
          col="12"
          lg="12"
          sm="12"
          class="my-1 d-flex justify-center text-center"
        >
          <h2 class="d-flex justify-center text-center my-2">
            教育經歷
          </h2>
        </v-col>
        <v-col
          cols="10"
          justify="center"
          class="ma-0 d-flex flex-wrap mx-auto justify-center text-center"
        >
          <v-timeline align-top :dense="$vuetify.breakpoint.smAndDown">
            <v-timeline-item
              v-for="(edu, i) in edus"
              :key="i"
              :color="edu.color"
              icon="mdi-book-variant"
              fill-dot
              small
            >
              <v-card :color="edu.color" dark width="800">
                <v-card-title class="pa-5 pt-3 pl-5" v-text="edu.schoolName">
                </v-card-title>
                <v-card-subtitle
                  class="primary--text white pa-1 pt-2 pl-3 text-left"
                  v-text="edu.major"
                ></v-card-subtitle>
                <v-card-text
                  class="white grey--text pa-1 pt-2 pl-3 text-left"
                  v-text="edu.year"
                >
                </v-card-text>
              </v-card>
            </v-timeline-item>
          </v-timeline>
        </v-col>
      </v-col>

      <!-- 工作經歷 -->
      <v-col
        justify="center"
        cols="12"
        class="ma-0 py-5 justify-center text-center white lighten-4"
      >
        <v-col
          col="12"
          lg="12"
          sm="12"
          class="my-1 d-flex justify-center text-center"
        >
          <h2 class="d-flex justify-center text-center my-2">
            工作經歷
          </h2>
        </v-col>
        <v-col
          cols="10"
          justify="center"
          class="ma-0 d-flex flex-wrap mx-auto justify-center text-center"
        >
          <v-timeline align-top :dense="$vuetify.breakpoint.smAndDown">
            <v-timeline-item
              v-for="(job, i) in jobs"
              :key="i"
              color="blue-grey"
              icon="mdi-star"
              fill-dot
              small
            >
              <v-card :color="job.color" dark width="1000">
                <v-card-title class="pa-5 pt-3 pl-5" v-text="job.position">
                </v-card-title>
                <v-card-subtitle
                  class="blue-grey--text white pa-1 pt-2 pl-3 text-left"
                  v-text="job.companyName"
                ></v-card-subtitle>
                <v-card-text
                  class="white black--text pa-1 pt-2 pl-3 text-left"
                  v-text="job.experienceDesc"
                >
                </v-card-text>
              </v-card>
            </v-timeline-item>
          </v-timeline>
        </v-col>
      </v-col>
    </v-col>
  </v-row>
</template>

<script>
import http from "../utils/http";
import axios from "axios";

export default {
  created: function() {
    this.getImage();

    http.get("/user").then(response => {
      this.user = response.data;
    });

    http.get("/education").then(response => {
      this.edus = response.data;
      this.edus.forEach(edu => {
        edu["color"] = "blue darken-2";
        edu["year"] = edu.startDate + "~" + edu.endDate;
      });
    });

    http.get("/experience").then(response => {
      this.jobs = response.data;
      this.jobs.forEach(job => {
        job["color"] = "blue-grey";
      });
    });
  },
  data: () => ({
    user: {},
    background: require("../assets/webistebanner.png"),
    image: require("../assets/avatar_default.jpg"),
    edus: [],
    jobs: []
  }),
  methods: {
    getImage() {
      let user = localStorage.getItem("user");
      user = JSON.parse(user);
      axios
        .get(process.env.VUE_APP_BACKEND_URL + "/user/image", {
          responseType: "arraybuffer",
          headers: {
            Authorization: user.jwt
          }
        })
        .then(response => {
          if (response.data.byteLength === 0) {
            this.image = require("../assets/avatar_default.jpg");
          } else {
            this.image =
              "data:image/jpeg;base64," +
              btoa(
                new Uint8Array(response.data).reduce(
                  (data, byte) => data + String.fromCharCode(byte),
                  ""
                )
              );
          }
        });
    }
  }
};
</script>
