<template>
  <v-row justify="center" class="mt-12">
    <v-col cols="12" md="7" lg="8">
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
              <h2 justify="center" class="my-green">建立履歷</h2>
              <div class="subtitle-2 mt-2">請輸入您的履歷名稱</div>
            </div>
            <v-sheet max-width="500">
              <form>
                <v-text-field
                  maxLength="20"
                  counter="20"
                  outlined
                  required
                  label="履歷名稱"
                  prepend-inner-icon="mdi-account-outline"
                  hide-details="auto"
                  placeholder="履歷名稱"
                  v-model="resumeName"
                ></v-text-field>
                <v-btn
                  depressed
                  block
                  large
                  class="mr-4"
                  color="primary"
                  @click="create"
                >
                  建立履歷
                </v-btn>
                <div class="text-center my-5">
                  <v-btn
                    text
                    x-large
                    class="py-1 text-decoration-none"
                    @click="resumeList"
                    v-if="!this.$route.query.hide"
                  >
                    選擇已建立的履歷！
                  </v-btn>
                </div>
                <div class="text-center my-5"></div>
              </form>
            </v-sheet>
          </v-col>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import theDialog from "../../components/theDialog";
import http from "../../utils/http";
export default {
  components: {
    theDialog
  },
  watch: {},
  created: function() {
    this.templateId = this.$route.query.templateId;
  },
  data: () => ({
    resumeName: "",
    templateId: null
  }),
  methods: {
    create() {
      let data = { resumeName: this.resumeName };
      if (this.templateId > 0) {
        data["templateID"] = this.templateId;
      }
      http.post("/resume", data).then(response => {
        if (response.data != 0) {
          alert("建立成功");
          if (this.templateId > 0) {
            this.$router.push({
              path: "/apply-info",
              query: { resumeId: response.data }
            });
          } else if (this.$route.query.hide) {
            this.$router.push({
              path: "/resume-list"
            });
          } else {
            this.$router.push({
              path: "/template-list",
              query: { resumeId: response.data }
            });
          }
        } else {
          alert("建立失敗");
        }
      });
    },
    resumeList() {
      let data = { path: "/resume-list" };
      if (this.templateId > 0) {
        data["query"] = { templateId: this.templateId };
      }
      this.$router.push(data);
    }
  }
};
</script>
