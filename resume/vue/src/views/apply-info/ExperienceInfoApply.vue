<template>
  <v-row justify="center">
    <v-col cols="12" md="10" lg="9">
      <theStepper step="3"></theStepper>
      <v-expansion-panels v-model="panel" multiple class="mt-4">
        <v-expansion-panel>
          <v-expansion-panel-header color="blue">
            <template v-slot:actions>
              <v-icon color="white">$expand</v-icon>
            </template>
            <span class="white--text text-h6">工作經歷</span>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row no-gutters class="mt-6" justify="center">
              <v-col cols="10" md="10" lg="10">
                <v-alert outlined type="error" prominent border="left">
                  <h3 justify="center" class="my-green">
                    請勾選
                    <strong>{{ template.experience }}</strong>
                    項，欲顯示在履歷表中的項目
                  </h3>
                  您目前已勾選 {{ selectedCount }} 項，若有不足，將以空白呈顯。
                </v-alert>
              </v-col>

              <v-col cols="10" md="10" lg="10" class="pa-0 mb-2">
                <v-card
                  elevation="1"
                  class="mx-auto pa-0"
                  v-for="(experience, index) in experiences"
                  :key="index"
                >
                  <v-card-title class="blue lighten-4 mt-8 mb-3 pa-1 pl-4">
                    <v-checkbox
                      class="white--text"
                      :label="experience.companyName"
                      color="red"
                      :value="experience.sn"
                      v-model="selected"
                    ></v-checkbox>
                  </v-card-title>
                  <v-row class="mx-auto mt-5 px-10 pa-0">
                    <v-col cols="12" md="12" lg="12" class="pa-0 mt-0">
                      <p>{{ experience.position }}</p>
                    </v-col>
                    <v-col cols="12" md="6" lg="6" class="pa-0 mt-0">
                      <p>開始日期：{{ experience.startDate }}</p>
                    </v-col>
                    <v-col cols="12" md="6" lg="6" class="pa-0 mt-0">
                      <p>結束日期：{{ experience.endDate }}</p>
                    </v-col>
                    <v-col cols="12" md="12" lg="12" class="pa-0 mt-0">
                      <p class="text-justify">
                        {{ experience.experienceDesc }}
                        <!--                        參與公司網站 Web 以及 App 相關的 UI / UX-->
                        <!--                        設計開發。根據公司商業目標上的需求，參與產品設計，協助定義、改善使用者流程及體驗。設計具有一致性，兼具美感與易用性的使用者介面。理解團隊開發優先順序，與產品團隊一同提供最佳使用體驗的-->
                        <!--                        UI/UX 改善方案。與 PM、設計師-->
                        <!--                        以及工程師溝通協作，在時程內交付最高水準的設計以及相關文件。維護並持續優化產品設計規範。-->
                      </p>
                    </v-col>
                  </v-row>
                </v-card>
              </v-col>
            </v-row>

            <v-row justify="center" class="mb-2">
              <v-col cols="6" md="5" lg="4">
                <v-btn
                  depressed
                  large
                  block
                  color="primary"
                  :to="'/educationinfo-apply?resumeId=' + this.resume.id"
                  >回上一頁</v-btn
                >
              </v-col>
              <v-col cols="6" md="5" lg="4">
                <v-btn depressed large block color="primary" @click="nextStep"
                  >儲存，下一步</v-btn
                >
              </v-col>
            </v-row>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-col>
  </v-row>
</template>

<script>
import theStepper from "../../components/theStepperApply";
import theDialog from "../../components/theDialog";
import http from "../../utils/http";
export default {
  components: {
    theStepper,
    theDialog
  },
  computed: {
    selectedCount() {
      return this.selected.length;
    }
  },
  created: function() {
    http
      .get("/resume/" + this.$route.query.resumeId)
      .then(response => {
        this.resume = response.data;
      })
      .then(() => {
        http.get("/template/" + this.resume.templateID).then(response => {
          this.template = response.data;
        });
      })
      .then(() => {
        http.get("/resume/" + this.resume.id + "/relation").then(response => {
          this.selected = response.data.experience;
        });
      });

    http.get("/experience").then(response => {
      this.experiences = response.data;
    });
  },
  data: () => ({
    panel: [0],
    experiences: [],
    selected: [],
    template: {},
    resume: {}
  }),
  methods: {
    nextStep() {
      console.log(this.selected);
      if (this.selected.length > this.template.experience) {
        alert("數量超出限制");
      } else {
        http
          .put("/resume/" + this.resume.id + "/experience", this.selected)
          .then(response => {
            if (response.data == true) {
              alert("成功");
              this.$router.push("/skillinfo-apply?resumeId=" + this.resume.id);
            } else {
              alert("操作失敗");
            }
          });
      }
    }
  }
};
</script>
