<template>
  <v-row justify="center" class="mt-12">
    <v-col cols="12" md="7" sm="8">
      <v-card class="mx-auto my-0 pa-0" max-width="400">
        <v-row class="ma-0">
          <v-col cols="12" md="12" class="px-5 pt-5">
            <div justify="center" class="align-center text-center">
              <v-responsive>
                <v-img src="../../assets/mail.svg" max-height="150" contain>
                </v-img>
              </v-responsive>
            </div>
          </v-col>

          <v-col cols="12" md="12" class="px-5 pt-0">
            <div class="my-4 align-center text-center">
              <h2 justify="center">忘記密碼</h2>
              <div class="subtitle-2 mt-2">
                請填寫您的註冊 E-mail，我們將寄送重置密碼連結給您
              </div>
            </div>
            <v-sheet max-width="400">
              <form>
                <v-text-field
                  v-model="email"
                  outlined
                  required
                  label="E-mail"
                  prepend-inner-icon="mdi-email-outline"
                  hide-details="auto"
                  placeholder="請輸入註冊 E-mail"
                  :error-messages="emailErrors"
                  @input="$v.email.$touch()"
                  @blur="$v.email.$touch()"
                ></v-text-field>

                <v-btn
                  depressed
                  block
                  large
                  class="mt-4"
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
import { required, email } from "vuelidate/lib/validators";
export default {
  validations: {
    email: { required, email }
  },
  data() {
    return {
      email: ""
    };
  },
  computed: {
    emailErrors() {
      const errors = [];
      if (!this.$v.email.$dirty) return errors;
      !this.$v.email.email && errors.push("請輸入註冊E-mail");
      !this.$v.email.required && errors.push("請輸入註冊E-mail");
      return errors;
    }
  },
  methods: {
    submit() {
      if (this.$v.email.email && this.email.length > 0) {
        http
          .post("/user/send-token?email=" + this.email)
          .then(response => {
            console.log(response);
            if (response.data === true) {
              alert("發送成功，請到您的電子郵箱使用連結重置密碼。");
            } else {
              alert("發生錯誤!");
            }
          })
          .catch(error => {
            alert("發生錯誤");
            //alert(error.response.data.message);
            console.log(error.response.data.message);
          });
      }
    }
  }
};
</script>
