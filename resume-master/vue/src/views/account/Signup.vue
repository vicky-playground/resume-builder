<template>
  <v-row justify="center" class="mt-12">
    <v-col cols="12" md="7" sm="8">
      <v-card class="mx-auto my-0 pa-0" max-width="800">
        <v-row class="ma-0">
          <v-col cols="12" md="6" class="px-5">
            <v-row justify="center" class="fill-height ma-0 align-center">
              <v-responsive>
                <v-img src="../../assets/register.svg"> </v-img>
              </v-responsive>
            </v-row>
          </v-col>

          <v-col cols="12" md="6" class="px-5">
            <div class="my-8 align-center text-center">
              <h2 justify="center">註冊帳號</h2>
              <div class="subtitle-2 mt-2">建立新帳號</div>
            </div>
            <v-sheet max-width="500">
              <form>
                <v-text-field
                  v-model="username"
                  maxLength="30"
                  counter="30"
                  outlined
                  required
                  label="帳號"
                  prepend-inner-icon="mdi-account-outline"
                  hide-details="auto"
                  :error-messages="usernameErrors"
                  @input="$v.username.$touch()"
                  @blur="$v.username.$touch()"
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
                  placeholder="密碼(須為4-10個英文組合)"
                  :error-messages="passwordErrors"
                  @input="$v.password.$touch()"
                  @blur="$v.password.$touch()"
                ></v-text-field>

                <div class="d-flex my-2 caption">
                  註冊後，即表示已閱讀且同意
                  <router-link to="/terms" class="text-decoration-none">
                    使用條款
                  </router-link>
                  和
                  <router-link to="/privacy" class="text-decoration-none">
                    隱私政策
                  </router-link>
                </div>

                <v-btn
                  depressed
                  block
                  large
                  class="mr-4"
                  color="primary"
                  @click="signUp"
                >
                  立即註冊
                </v-btn>
                <div class="text-center my-5">
                  <router-link to="/login" class="py-1 text-decoration-none">
                    帳號登入！
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
import { validationMixin } from "vuelidate";
import { required, maxLength, minLength } from "vuelidate/lib/validators";
export default {
  mixins: [validationMixin],
  validations: {
    username: { required, maxLength: maxLength(10) },
    password: { required, maxLength: maxLength(10) },
    minLength: minLength(4)
  },
  data() {
    return {
      username: "",
      password: ""
    };
  },
  computed: {
    usernameErrors() {
      const errors = [];
      // if (!this.$v.username.$dirty) return errors;
      // !this.$v.username.maxLength && errors.push("帳號為10個英文或數字");
      // !this.$v.username.required &&
      //   errors.push("請輸入帳號(須為4-10個英文或數字)");
      return errors;
    },
    passwordErrors() {
      const errors = [];
      if (!this.$v.password.$dirty) return errors;
      !this.$v.password.maxLength && errors.push("密碼為10個英文或數字");
      !this.$v.password.required &&
        errors.push("請輸入密碼(須為4-10個英文組合)");
      return errors;
    }
  },
  methods: {
    signUp() {
      if (this.username && this.password) {
        http
          .post("/user", {
            account: this.username,
            password: this.password
          })
          .then(response => {
            alert("註冊成功");
            this.$router.push("/login");
          })
          .catch(error => {
            // console.log(error.response.data)
            alert("註冊失敗");
            // console.log(error.response.data.message);
          });
      } else {
        alert("請輸入帳密");
      }
    }
  }
};
</script>
