<template>
  <v-row justify="center" class="mt-4">
    <v-col cols="10" md="3" lg="3" sm="12" class="pa-0">
      <v-card class="mx-auto my-0 pa-0 blue">
        <v-img height="100%">
          <v-row align="center" class="fill-height pa-0 ma-0">
            <v-col
              align-self="center"
              align="center"
              class="ma-0 pa-0"
              cols="12"
            >
              <v-avatar class="profile my-5" color="grey" size="225" circle>
                <v-img :src="image"> </v-img>
              </v-avatar>
            </v-col>
            <v-col class="pa-0 ma-0">
              <v-list-item color="rgba(0, 0, 0, .4)" dark>
                <v-list-item-content>
                  <v-list-item-title class="text-h6 text-center">
                    <h4>{{ user.name }}</h4>
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-col>
            <v-col cols="12" md="12" lg="12" sm="12" class="white pa-5 ma-0">
              <v-btn
                label="File input"
                x-large
                block
                outlined
                color="blue"
                class="ma-2 pa-2 white--text"
                @click="upload"
              >
                上傳頭像
                <v-icon right light> mdi-image-area </v-icon>
              </v-btn>
              <input
                ref="uploader"
                class="d-none"
                type="file"
                accept="image/*"
                @change="onFileChanged"
              />
            </v-col>
          </v-row>
        </v-img>
      </v-card>
    </v-col>
    <v-col cols="10" md="7" sm="7" class="pa-0 mx-5">
      <v-card class="mx-auto my-0 pa-5">
        <h2 justify="left">帳號設定</h2>
        <v-divider class="my-5"></v-divider>
        <v-row class="mt-5">
          <v-col cols="12" md="5" lg="5">
            <v-text-field
              label="聯絡電話"
              outlined
              dense
              v-model="user.phone"
              disabled
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="7" lg="7">
            <v-text-field
              label="註冊email"
              outlined
              dense
              v-model="user.account"
              disabled
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="3" lg="3">
            <v-btn
              label="變更密碼"
              x-large
              color="blue"
              class="ma-2 pa-5 white--text"
              to="/forgetpw"
            >
              變更密碼
              <v-icon right light> mdi-lock-outline </v-icon>
            </v-btn>
          </v-col>
          <v-col cols="12" md="3" lg="4">
            <v-btn
              label="修改履歷資料"
              x-large
              color="blue"
              class="ma-2 pa-5 white--text"
              to="/user-info"
            >
              修改履歷資料
              <v-icon right light> mdi-pencil-outline </v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import theStepper from "../../components/theStepper";
import theDialog from "../../components/theDialog";
import http from "../../utils/http";
import axios from "axios";
export default {
  components: {
    theStepper,
    theDialog
  },
  watch: {},
  created: function() {
    http.get("/user").then(response => {
      this.user = response.data;
    });

    this.getImage();
  },
  data: () => ({
    user: {},
    image: require("../../assets/avatar_default.jpg"),
    selectedFile: null
  }),
  methods: {
    upload() {
      this.$refs.uploader.click();
    },
    onFileChanged(e) {
      this.selectedFile = e.target.files[0];

      if (this.selectedFile != null) {
        let user = localStorage.getItem("user");
        user = JSON.parse(user);

        let data = new FormData();
        data.append("image", this.selectedFile);
        axios
          .post(process.env.VUE_APP_BACKEND_URL + "/user/image/upload", data, {
            headers: {
              accept: "application/json",
              "Accept-Language": "en-US,en;q=0.8",
              "Content-Type": `multipart/form-data;`,
              Authorization: user.jwt
            }
          })
          .then(response => {
            if (response.data == true) {
              alert("上傳成功");
              this.image = URL.createObjectURL(this.selectedFile);
            } else {
              alert("上傳失敗");
            }
          })
          .catch(error => {
            alert("上傳失敗");
          })
          .finally(() => {
            this.selectedFile = null;
          });
      }
    },
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
            this.image = require("../../assets/avatar_default.jpg");
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
