<template>
  <v-row justify="center">
    <v-col cols="12" md="10" lg="9">
      <theStepper step="4"></theStepper>
      <v-expansion-panels v-model="panel" multiple class="mt-4" v-if="confirm">
        <v-expansion-panel>
          <v-expansion-panel-header color="blue">
            <template v-slot:actions>
              <v-icon color="white">$expand</v-icon>
            </template>
            <span class="white--text text-h6">專業技能</span>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row justify="center" class="mt-4 mb-0 py-0">
              <v-col
                cols="12"
                md="11"
                lg="11"
                class="d-flex justify-end py-0 px-0"
              >
                <v-btn text color="primary" @click="addSkill">
                  <v-icon left>mdi-plus-circle</v-icon>新增專長
                </v-btn>
              </v-col>
            </v-row>
            <v-row
              justify="center"
              class="mt-0"
              v-for="(skill, index) in skills"
              v-bind:key="index"
            >
              <v-col cols="12" md="3" lg="3">
                <v-text-field
                  :label="'專長名稱 ' + (index + 1)"
                  outlined
                  dense
                  hide-details
                  v-model="skill.skillName"
                >
                  <template v-slot:prepend>
                    <v-icon @click="removeSkill(index)" color="red">
                      mdi-close
                    </v-icon>
                  </template>
                </v-text-field>
              </v-col>
              <v-col cols="12" md="8" lg="8">
                <v-textarea
                  :label="'專長描述 ' + (index + 1)"
                  outlined
                  rows="3"
                  auto-grow
                  no-resize
                  maxLength="100"
                  counter="100"
                  v-model="skill.skillDesc"
                ></v-textarea>
              </v-col>
            </v-row>
          </v-expansion-panel-content>
        </v-expansion-panel>
        <v-expansion-panel>
          <v-expansion-panel-header color="blue">
            <template v-slot:actions>
              <v-icon color="white">$expand</v-icon>
            </template>
            <span class="white--text text-h6">語言能力</span>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row justify="center" class="mt-4 mb-0 py-0">
              <v-col
                cols="12"
                md="11"
                lg="11"
                class="d-flex justify-end py-0 px-0"
              >
                <v-btn text color="primary" @click="addLanguage">
                  <v-icon left>mdi-plus-circle</v-icon>新增語言能力
                </v-btn>
              </v-col>
            </v-row>
            <v-row
              justify="center"
              class="mt-0"
              v-for="(language, index) in languages"
              v-bind:key="index"
            >
              <v-col cols="12" md="3" lg="3">
                <v-text-field
                  :label="'語言類型 ' + (index + 1)"
                  outlined
                  dense
                  hide-details
                  v-model="language.language"
                >
                  <template v-slot:prepend>
                    <v-icon @click="removeLanguage(index)" color="red">
                      mdi-close
                    </v-icon>
                  </template>
                </v-text-field>
              </v-col>
              <v-col cols="6" md="2" lg="2">
                <v-select
                  label="聽"
                  dense
                  outlined
                  :items="languageLevels"
                  v-model="language.listening"
                ></v-select>
              </v-col>
              <v-col cols="6" md="2" lg="2">
                <v-select
                  label="說"
                  dense
                  outlined
                  :items="languageLevels"
                  v-model="language.speaking"
                ></v-select>
              </v-col>
              <v-col cols="6" md="2" lg="2">
                <v-select
                  label="讀"
                  dense
                  outlined
                  :items="languageLevels"
                  v-model="language.reading"
                ></v-select>
              </v-col>
              <v-col cols="6" md="2" lg="2">
                <v-select
                  label="寫"
                  dense
                  outlined
                  :items="languageLevels"
                  v-model="language.writing"
                ></v-select>
              </v-col>
            </v-row>
          </v-expansion-panel-content>
        </v-expansion-panel>
        <v-expansion-panel>
          <v-expansion-panel-header color="blue">
            <template v-slot:actions>
              <v-icon color="white">$expand</v-icon>
            </template>
            <span class="white--text text-h6">證照</span>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row class="mt-4" justify="center">
              <v-col cols="12" md="10" lg="8">
                <v-text-field
                  label="證照名稱"
                  outlined
                  dense
                  v-model="license"
                  v-on:keyup.enter="onLicenseEnter()"
                  maxLength="20"
                  counter="20"
                  hint="輸入完畢按下【Enter】鍵完成新增"
                  persistent-hint
                ></v-text-field>
                <v-chip
                  v-for="(item, index) in licenses"
                  v-bind:key="index"
                  close
                  color="blue"
                  text-color="white"
                  class="mr-2 mb-2"
                  @click:close="close(index)"
                >
                  {{ item.lsName }}
                </v-chip>
              </v-col>
            </v-row>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
      <v-row justify="center" class="my-6" v-if="confirm">
        <v-col cols="6" md="5" lg="4">
          <v-btn depressed large block color="primary" to="/experience-list"
            >上一步</v-btn
          >
        </v-col>
        <v-col cols="6" md="5" lg="4">
          <v-btn depressed large block color="primary" @click="nextStep"
            >儲存，下一步</v-btn
          >
        </v-col>
      </v-row>
      <!-- 填寫專業技能注意事項 -->
      <v-card class="mx-auto my-5 pa-0" v-if="!confirm">
        <v-row class="ma-0 py-5" justify="center">
          <v-col cols="9" md="4" lg="4" class="px-5 pt-5">
            <div justify="end">
              <v-responsive>
                <v-img
                  src="../../assets/certification1.svg"
                  max-height="350"
                  contain
                >
                </v-img>
              </v-responsive>
            </div>
          </v-col>
          <v-col cols="9" md="5" lg="5" class="px-5 pt-0">
            <div justify="start" class="my-4">
              <h3 justify="center">專業技能（Skill）</h3>
              <p class="subtitle-2 my-2">
                建議主要填寫跟職務相關的專業技能，或是語言能力，寫完後再寫生其他跟職務「非」相關的專業技能。
              </p>
            </div>
            <v-row justify="start" class="mb-2">
              <v-col cols="10" md="5" lg="5">
                <v-btn
                  depressed
                  large
                  block
                  color="primary"
                  @click="confirm = true"
                  >我瞭解了，開始填寫專業技能！</v-btn
                >
              </v-col>
            </v-row>
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
export default {
  components: {
    theStepper,
    theDialog
  },
  created: function() {
    http.get("/language").then(response => {
      if (response.data.length > 0) {
        this.languages = response.data;
      } else {
        this.languages.push({
          id: null,
          language: "",
          listening: "",
          speaking: "",
          reading: "",
          writing: ""
        });
      }
    });

    http.get("/skill").then(response => {
      if (response.data.length > 0) {
        this.skills = response.data;
      } else {
        this.skills.push({
          id: null,
          skillName: "",
          skillDesc: ""
        });
      }
    });

    http.get("/license").then(response => {
      if (response.data.length > 0) {
        this.licenses = response.data;
      }
    });
  },
  data: () => ({
    panel: [0, 1, 2],
    languages: [],
    license: "",
    licenses: [],
    skills: [],
    languageLevels: [
      { text: "精通", value: "精通" },
      { text: "中等", value: "中等" },
      { text: "略懂", value: "略懂" },
      { text: "不會", value: "不會" }
    ],
    confirm: false
  }),
  methods: {
    onLicenseEnter() {
      if (this.license.length > 0) {
        this.licenses.push({
          lsName: this.license
        });
        this.license = "";
      }
    },
    close(index) {
      this.licenses.splice(index, 1);
    },
    addLanguage() {
      this.languages.push({
        id: null,
        language: "",
        listening: "",
        speaking: "",
        reading: "",
        writing: ""
      });
    },
    removeLanguage(index) {
      this.languages.splice(index, 1);
    },
    addSkill() {
      this.skills.push({
        id: null,
        skillName: "",
        skillDesc: ""
      });
    },
    removeSkill(index) {
      this.skills.splice(index, 1);
    },
    nextStep() {
      http.put("/skill/replace", this.skills).then(response => {
        console.log(response);
      });

      http.put("/language/replace", this.languages).then(response => {
        console.log(response);
      });

      http.put("/license/replace", this.licenses).then(response => {
        console.log(response);
      });

      this.$router.push("/autobiography");
    }
  }
};
</script>

<style>
/*span {*/
/*  color: dodgerblue;*/
/*}*/
</style>
