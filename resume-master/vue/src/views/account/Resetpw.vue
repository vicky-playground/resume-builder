<template>
  <v-row justify="center" class="mt-12">
    <v-col cols="12" md="7" sm="8">
      <v-card class="mx-auto my-0 pa-0" max-width="400">
        <v-row class="ma-0">
          <v-col cols="12" md="12" class="px-5 pt-5">
            <div justify="center" class="align-center text-center">
              <v-responsive>
                <v-img src="../../assets/resetpw.svg" max-height="150" contain>
                </v-img>
              </v-responsive>
            </div>
          </v-col>

          <v-col cols="12" md="12" class="px-5 pt-0">
            <div class="my-4 align-center text-center">
              <h2 justify="center">重設密碼</h2>
              <div class="subtitle-2 mt-2">請重新設定您的密碼</div>
            </div>
            <v-sheet max-width="500">
              <form>
                <v-text-field
                  v-model="password"
                  :type="'password'"
                  :counter="10"
                  outlined
                  required
                  label="請輸入新密碼"
                  prepend-inner-icon="mdi-lock-outline"
                  hide-details="auto"
                  placeholder="請輸入新密碼"
                  :error-messages="passwordErrors"
                  @input="$v.password.$touch()"
                  @blur="$v.password.$touch()"
                ></v-text-field>

                <v-text-field
                  v-model="password1"
                  :type="'password'"
                  :counter="10"
                  outlined
                  required
                  label="再次輸入密碼"
                  prepend-inner-icon="mdi-lock-outline"
                  hide-details="auto"
                  placeholder="再次輸入密碼"
                ></v-text-field>

                <v-btn
                  depressed
                  block
                  large
                  class="mr-4"
                  color="primary"
                  @click="submit"
                >
                  送出
                </v-btn>
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
    password: { required, maxLength: maxLength(10) },
    password1: { required, maxLength: maxLength(10) },
    minLength: minLength(4)
  },
  created: function() {
    // http.post() TODO check token
  },
  data() {
    return {
      password: "",
      password1: ""
    };
  },
  computed: {
    passwordErrors() {
      const errors = [];
      if (!this.$v.password.$dirty) return errors;
      !this.$v.password.maxLength && errors.push("密碼為4-10個英文數字組合");
      !this.$v.password.required && errors.push("請輸入密碼");
      return errors;
    }
  },
  methods: {
    submit() {
      let token = this.$route.query.token;

      if (
        this.password.length > 0 &&
        this.password === this.password1 &&
        token
      ) {
        http
          .post("/user/reset-password", { token, password: this.password })
          .then(response => {
            console.log(response);
            if (response.data === true) {
              alert("修改成功");
              this.$router.push("/login");
            } else {
              alert("發生錯誤!");
            }
          })
          .catch(error => {
            alert("發生錯誤");
            //alert(error.response.data.message);
            console.log(error.response.data.message);
          });
      } else {
        alert("密碼輸入有誤");
      }
    }
  }
};
</script>
