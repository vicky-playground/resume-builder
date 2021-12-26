<template>
  <v-row justify="center" class="mt-12">
    <v-col cols="12" md="7" sm="8">
      <v-card class="mx-auto my-0 pa-0" max-width="800">
        <v-row class="ma-0">
          <v-col cols="12" md="6" class="px-5">
            <v-row justify="center" class="fill-height ma-0 align-center">
              <v-responsive>
                <v-img src="../../assets/secure_login.svg"> </v-img>
              </v-responsive>
            </v-row>
          </v-col>

          <v-col cols="12" md="6" class="px-5">
            <div class="my-8 align-center text-center">
              <h2 justify="center" class="my-green">登入帳號</h2>
              <div class="subtitle-2 mt-2">請輸入您的帳號/密碼</div>
            </div>
            <v-sheet max-width="500">
              <form>
                <v-text-field
                  v-model="account"
                  maxLength="30"
                  counter="30"
                  outlined
                  required
                  label="帳號"
                  prepend-inner-icon="mdi-account-outline"
                  hide-details="auto"
                  placeholder="請輸入帳號"
                  :error-messages="accountErrors"
                  @input="$v.account.$touch()"
                  @blur="$v.account.$touch()"
                ></v-text-field>
                <v-text-field
                  v-model="password"
                  :type="'password'"
                  maxLength="10"
                  counter="10"
                  outlined
                  required
                  label="密碼"
                  prepend-inner-icon="mdi-lock-outline"
                  hide-details="auto"
                  placeholder="請輸入密碼"
                  :error-messages="passwordErrors"
                  @input="$v.password.$touch()"
                  @blur="$v.password.$touch()"
                ></v-text-field>

                <div class="d-flex justify-space-between">
                  <!--                    <v-checkbox-->
                  <!--                      label="記住我的帳號密碼"-->
                  <!--                      class="mt-0"-->
                  <!--                      required-->
                  <!--                      outlined-->
                  <!--                      @change="$v.checkbox.$touch()"-->
                  <!--                      @blur="$v.checkbox.$touch()"-->
                  <!--                    ></v-checkbox>-->
                  <router-link to="/forgetpw" class="py-1 text-decoration-none">
                    忘記密碼？
                  </router-link>
                </div>

                <v-btn
                  depressed
                  block
                  large
                  class="mr-4"
                  color="primary"
                  @click="login"
                >
                  立即登入
                </v-btn>
                <div class="text-center my-5">
                  <router-link to="/signup" class="py-1 text-decoration-none">
                    立即註冊！
                  </router-link>
                </div>
              </form>
            </v-sheet>
          </v-col>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import http from "../../utils/http";
import axios from "axios";
import { validationMixin } from "vuelidate";
import { required, maxLength, minLength } from "vuelidate/lib/validators";
export default {
  mixins: [validationMixin],
  validations: {
    account: { required },
    password: { required, maxLength: maxLength(10) },
    minLength: minLength(4)
  },
  data() {
    return {
      account: "",
      password: ""
    };
  },
  computed: {
    accountErrors() {
      const errors = [];
      // if (!this.$v.account.$dirty) return errors;
      // !this.$v.account.maxLength && errors.push("帳號為4-10個英文或數字");
      // !this.$v.account.required && errors.push("請輸入帳號");
      return errors;
    },
    passwordErrors() {
      const errors = [];
      if (!this.$v.password.$dirty) return errors;
      !this.$v.password.maxLength && errors.push("密碼為4-10個英文數字組合");
      !this.$v.password.required && errors.push("請輸入密碼");
      return errors;
    }
  },
  methods: {
    login() {
      const data = {
        account: this.account,
        password: this.password
      };
      const formData = new FormData();
      Object.keys(data).forEach(key => {
        formData.append(key, data[key]);
      });
      const config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      };
      axios
        .post(process.env.VUE_APP_BACKEND_URL + "/login", formData, config)
        .then(response => {
          alert("登入成功");
          console.log(response.data);
          this.$store.commit("login", response.data);
          this.$router.push(this.$route.query.redirect || "/setting");
        })
        .catch(() => {
          alert("登入失敗");
        });
    },
    logout() {
      this.$store.commit("logout");
    },

    print() {
      console.log("state: ");
      console.log(this.$store.state);
      console.log("local storage");
      const userData = JSON.parse(localStorage.getItem("user"));
      console.log(userData);
    }
  }
};
</script>

<!-- 自訂 css with vuetify sass -->
<style lang="sass" scoped>
@import '~vuetify/src/styles/styles'

.my-green
	color: map-get($green, 'base')
</style>
