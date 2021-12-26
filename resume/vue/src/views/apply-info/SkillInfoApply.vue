<template>
  <v-row justify="center">
    <v-col cols="12" md="10" lg="9">
      <theStepper step="4"></theStepper>
      <v-expansion-panels v-model="panel" multiple class="mt-4">
        <v-expansion-panel>
          <v-expansion-panel-header color="blue">
            <template v-slot:actions>
              <v-icon color="white">$expand</v-icon>
            </template>
            <span class="white--text text-h6">專業技能</span>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row no-gutters class="mt-6" justify="center">
              <v-col cols="10" md="10" lg="10">
                <v-alert outlined type="error" prominent border="left">
                  <h3 justify="center" class="my-green">
                    請勾選
                    <strong>{{ allow }}</strong> 項，欲顯示在履歷表中的項目
                  </h3>
                  您目前已勾選 {{ selectedCount }} 項，若有不足，將以空白呈顯。
                </v-alert>
              </v-col>

              <v-col cols="10" md="10" lg="10" class="pa-0 mb-2">
                <v-card elevation="1" class="mx-auto pa-0">
                  <v-card-title class="blue lighten-4 mt-8 mb-3 pa-1 pl-4">
                    <p
                      class="v-input--selection-controls v-input--checkbox v-input__control"
                      style="font-size:16px; line-height:20px;"
                    >
                      專業技能
                    </p>
                  </v-card-title>
                  <v-row class="mx-auto mt-2 px-10 pa-0">
                    <v-col
                      cols="12"
                      md="12"
                      lg="12"
                      class="pa-0 mt-0"
                      v-for="(skill, index) in skills"
                      :key="index"
                    >
                      <v-checkbox
                        :label="skill.skillName + ' &amp; ' + skill.skillDesc"
                        color="red darken-3"
                        :value="skill.id"
                        v-model="skillSelected"
                      ></v-checkbox>
                    </v-col>
                  </v-row>
                </v-card>
              </v-col>

              <v-col cols="10" md="10" lg="10" class="pa-0 mb-2">
                <v-card elevation="1" class="mx-auto pa-0">
                  <v-card-title class="blue lighten-4 mt-8 mb-3 pa-1 pl-4">
                    <p
                      class="v-input--selection-controls v-input--checkbox v-input__control"
                      style="font-size:16px; line-height:20px;"
                    >
                      語言能力
                    </p>
                  </v-card-title>
                  <v-row class="mx-auto mt-2 px-10 pa-0">
                    <v-col
                      cols="12"
                      md="12"
                      lg="12"
                      class="pa-0 mt-0"
                      v-for="(language, index) in languages"
                      :key="index"
                    >
                      <v-checkbox
                        :label="
                          language.language +
                            ' &amp; 聽:' +
                            language.listening +
                            ', 說:' +
                            language.speaking +
                            ', 讀:' +
                            language.reading +
                            ', 寫:' +
                            language.writing
                        "
                        color="red darken-3"
                        :value="language.id"
                        v-model="languageSelected"
                      ></v-checkbox>
                    </v-col>
                  </v-row>
                </v-card>
              </v-col>

              <v-col cols="10" md="10" lg="10" class="pa-0 mb-2">
                <v-card elevation="1" class="mx-auto pa-0">
                  <v-card-title class="blue lighten-4 mt-8 mb-3 pa-1 pl-4">
                    <p
                      class="v-input--selection-controls v-input--checkbox v-input__control"
                      style="font-size:16px; line-height:20px;"
                    >
                      證照
                    </p>
                  </v-card-title>
                  <v-row class="mx-auto mt-2 px-10 pa-0">
                    <v-col
                      cols="12"
                      md="12"
                      lg="12"
                      class="pa-0 mt-0"
                      v-for="(license, index) in licenses"
                      :key="index"
                    >
                      <v-checkbox
                        :label="license.lsName"
                        color="red darken-3"
                        :value="license.sn"
                        v-model="licenseSelected"
                      ></v-checkbox>
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
                  :to="'/experienceinfo-apply?resumeId=' + this.resume.id"
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
      return (
        this.skillSelected.length +
        this.languageSelected.length +
        this.licenseSelected.length
      );
    }
  },
  created: function() {
    http.get("/skill").then(response => {
      this.skills = response.data;
    });
    http.get("/language").then(response => {
      this.languages = response.data;
    });
    http.get("/license").then(response => {
      this.licenses = response.data;
    });

    http
      .get("/resume/" + this.$route.query.resumeId)
      .then(response => {
        this.resume = response.data;
      })
      .then(() => {
        http.get("/template/" + this.resume.templateID).then(response => {
          this.template = response.data;
          this.allow =
            this.template.skill +
            this.template.licence +
            this.template.language;
        });
      })
      .then(() => {
        http.get("/resume/" + this.resume.id + "/relation").then(response => {
          this.skillSelected = response.data.skill;
          this.languageSelected = response.data.language;
          this.licenseSelected = response.data.license;
        });
      });
  },
  data: () => ({
    panel: [0],
    skills: [],
    languages: [],
    licenses: [],
    skillSelected: [],
    languageSelected: [],
    licenseSelected: [],
    template: {},
    allow: 0,
    resume: {}
  }),
  methods: {
    nextStep() {
      console.log(this.skillSelected);
      console.log(this.languageSelected);
      console.log(this.licenseSelected);

      if (this.selectedCount > this.allow) {
        alert("數量超出限制");
      } else {
        http
          .put("/resume/" + this.resume.id + "/skill", this.skillSelected)
          .then(response => {
            if (response.data == true) {
              http
                .put(
                  "/resume/" + this.resume.id + "/language",
                  this.languageSelected
                )
                .then(response2 => {
                  if (response2.data == true) {
                    http
                      .put(
                        "/resume/" + this.resume.id + "/license",
                        this.licenseSelected
                      )
                      .then(response3 => {
                        if (response3.data == true) {
                          alert("成功");
                          this.$router.push(
                            "/autobiography-apply?resumeId=" + this.resume.id
                          );
                        } else {
                          alert("操作失敗");
                        }
                      });
                  } else {
                    alert("操作失敗");
                  }
                });
            } else {
              alert("操作失敗");
            }
          });
      }
    },
    count() {
      this.selectedCount =
        this.skillSelected.length +
        this.languageSelected.length +
        this.licenseSelected.length;
    }
  }
};
</script>
