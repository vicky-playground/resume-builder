<template>
  <v-row justify="center">
    <v-col cols="10" md="10" lg="10">
      <v-card>
        <v-tabs color="primary" left class="pa-5">
          <v-tab><h3>我的履歷</h3></v-tab>
          <v-tab-item>
            <v-row dense fluid>
              <v-col
                class="mt-5"
                v-for="(item, index) in resumeList"
                :key="index"
                cols="3"
                md="3"
                lg="3"
              >
                <v-card class="ma-1" v-if="index !== resumeList.length - 1">
                  <v-dialog width="535px">
                    <template v-slot:activator="{ on, attrs }">
                      <v-img
                        v-bind="attrs"
                        v-on="on"
                        :src="item.image"
                        :lazy-:src="item.image"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="350px"
                        aspect-ratio="1"
                      >
                        <v-card-title v-text="item.resumeName"></v-card-title>
                      </v-img>
                    </template>
                    <v-card>
                      <v-img
                        class="white--text align-end"
                        height="700px"
                        width="535px"
                        :src="item.image"
                        :lazy-:src="item.image"
                      >
                      </v-img>
                    </v-card>
                  </v-dialog>
                  <v-card-actions>
                    <v-spacer></v-spacer>

                    <v-btn icon @click="remove(item.id)">
                      <v-icon title="刪除履歷" alt="刪除履歷"
                        >mdi-trash-can-outline</v-icon
                      >
                    </v-btn>

                    <v-btn icon @click="apply(item.id)">
                      <v-icon title="套用履歷" alt="套用履歷"
                        >mdi-bookmark-box-multiple-outline</v-icon
                      >
                    </v-btn>
                    <v-btn icon @click="download(item.id)">
                      <v-icon title="下載履歷" alt="下載履歷"
                        >mdi-file-download-outline</v-icon
                      >
                    </v-btn>
                  </v-card-actions>
                </v-card>
                <v-card class="ma-1" v-else height="400px" flat tile>
                  <v-btn
                    :to="'/resume?hide=true'"
                    color="primary"
                    icon
                    x-large
                    outlined
                  >
                    <v-icon title="建立履歷" alt="建立履歷"
                      >mdi-text-box-plus-outline</v-icon
                    >
                  </v-btn>
                </v-card>
              </v-col>
            </v-row>
          </v-tab-item>
        </v-tabs>
      </v-card>
    </v-col>

    <v-overlay :value="overlay">
      <v-progress-circular
        size="64"
        color="primary"
        indeterminate
      ></v-progress-circular>
    </v-overlay>
  </v-row>
</template>

<script>
import http from "../../utils/http";
import axios from "axios";
export default {
  watch: {},
  created: function() {
    this.templateId = this.$route.query.templateId;
    this.getResume();
  },
  data: () => ({
    resumeList: [],
    templateId: 0,
    overlay: false
  }),
  methods: {
    getResume() {
      http.get("/resume").then(response => {
        this.resumeList = response.data;
        for (let i = 0; i < this.resumeList.length; i++) {
          if (this.resumeList[i].templateID == 0) {
            this.resumeList[i][
              "image"
            ] = require("../../assets/resume_template/resume5.png");
          } else {
            this.resumeList[i][
              "image"
            ] = require("../../assets/resume_template/resume" +
              this.resumeList[i].templateID +
              ".png");
          }
        }

        this.resumeList.push({});
      });
    },
    remove(resumeId) {
      http.delete("/resume/" + resumeId).then(response => {
        if (response.data == true) {
          alert("刪除成功");
          this.getResume();
        } else {
          alert("刪除失敗");
        }
      });
    },
    apply(resumeId) {
      if (this.templateId > 0) {
        http
          .put("/resume/".concat(resumeId, "/template/", this.templateId))
          .then(response => {
            if (response.data == true) {
              this.$router.push({ path: "/apply-info", query: { resumeId } });
            } else {
              alert("操作失敗");
            }
          });
      } else {
        this.$router.push({ path: "/template-list", query: { resumeId } });
      }
    },
    download(resumeId) {
      this.overlay = true;
      let user = localStorage.getItem("user");
      user = JSON.parse(user);

      axios
        .request({
          responseType: "blob",
          url: process.env.VUE_APP_BACKEND_URL + "/resume/download/" + resumeId,
          method: "post",
          headers: {
            accept: "application/json",
            "Accept-Language": "en-US,en;q=0.8",
            "Content-Type": `multipart/form-data;`,
            Authorization: user.jwt
          }
        })
        .then(response => {
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", "resume.pdf");
          document.body.appendChild(link);
          link.click();
        })
        .finally(() => {
          this.overlay = false;
        });
    }
  }
};
</script>
