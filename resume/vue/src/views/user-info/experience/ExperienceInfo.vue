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
            <v-row class="mt-6" justify="center">
              <v-col cols="12" md="10" lg="8">
                <v-text-field
                  label="公司名稱"
                  outlined
                  dense
                  v-model="experience.companyName"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col cols="12" md="10" lg="8">
                <v-text-field
                  label="職務名稱"
                  outlined
                  dense
                  v-model="experience.position"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col cols="6" md="5" lg="4">
                <v-menu
                  ref="menu"
                  v-model="menu"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      label="就職開始日"
                      v-model="experience.startDate"
                      outlined
                      dense
                      prepend-inner-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    ref="picker"
                    type="month"
                    v-model="experience.startDate"
                    :max="new Date().toISOString().substr(0, 10)"
                    min="1950-01-01"
                    @change="pickStartDate"
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="6" md="5" lg="4">
                <v-menu
                  ref="menu2"
                  v-model="menu2"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      label="就職結束日"
                      v-model="experience.endDate"
                      outlined
                      dense
                      prepend-inner-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    ref="picker2"
                    type="month"
                    v-model="experience.endDate"
                    min="1950-01-01"
                    @change="pickEndDate"
                  ></v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
            <v-row no-gutters justify="center">
              <v-col cols="12" md="10" lg="8" class="d-flex justify-end">
                <theDialog>
                  <template v-slot:btn>
                    <v-icon color="blue">mdi-chat-question</v-icon>看看範例
                  </template>
                  <template v-slot:text>
                    目前在國內旅遊公司服務，負責製作旅遊團活動物製作、企業戶旅遊手冊編排與設計、旅遊產品DM設計、異業合作活動攝影、公司網站美術設計、旅展場地布置:廣告物布置、展場監工。<br />
                    使用者介面設計:<br />
                    <ul>
                      <li>RWD 切版，動態效果處理的開發經驗</li>
                      <li>
                        優化網站介面，簡化顧客瀏覽流程，增加易讀性及網站美感。
                      </li>
                      <li>HTML & CSS 開發設計</li>
                      <li>Axure RP 製作 Wireframe</li>
                      <li>Figma Mockup & Prototype</li>
                      <li>Adobe Photoshop繪圖</li>
                    </ul>
                    使用者體驗設計:<br />
                    <ul>
                      <li>定義設計目的</li>
                      <li>
                        能運用同理心地圖/人物誌/顧客旅程地圖/使用者故事等進行UX分析
                      </li>
                      <li>易用性測試</li>
                      <li>使用者訪談</li>
                    </ul>
                  </template>
                </theDialog>
              </v-col>
            </v-row>
            <v-row no-gutters justify="center">
              <v-col cols="12" md="10" lg="8">
                <v-textarea
                  label="工作描述"
                  outlined
                  hint="假如你應徵的職位很看重某項工作技能，而你在某間公司得到的經驗，剛好可以佐證你具有相關能力，足以應付未來的挑戰。這時你必須把它放進工作經歷內並詳細描述說明。"
                  v-model="experience.experienceDesc"
                  rows="5"
                  auto-grow
                  no-resize
                  maxLength="100"
                  counter="100"
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col cols="12" md="10" lg="8">
                <v-text-field
                  label="工作技能"
                  outlined
                  dense
                  v-model="ability"
                  v-on:keyup.enter="onAbilityEnter()"
                  maxLength="20"
                  counter="20"
                  hint="輸入完畢按下【Enter】鍵完成新增"
                  persistent-hint
                ></v-text-field>
                <v-chip
                  v-for="(item, index) in abilities"
                  v-bind:key="index"
                  close
                  color="blue"
                  text-color="white"
                  class="mr-2 mb-2"
                  @click:close="close(item)"
                >
                  {{ item }}
                </v-chip>
              </v-col>
            </v-row>
            <v-row justify="center" class="mb-2">
              <v-col cols="6" md="5" lg="4">
                <v-btn
                  depressed
                  large
                  block
                  color="primary"
                  to="/experience-list"
                  >回上一頁</v-btn
                >
              </v-col>
              <v-col cols="6" md="5" lg="4">
                <v-btn depressed large block color="primary" @click="nextStep"
                  >儲存</v-btn
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
import theStepper from "../../../components/theStepper";
import theDialog from "../../../components/theDialog";
import http from "../../../utils/http";
export default {
  components: {
    theStepper,
    theDialog
  },
  watch: {
    menu(val) {
      val && setTimeout(() => (this.$refs.picker.activePicker = "YEAR"));
    },
    menu2(val) {
      val && setTimeout(() => (this.$refs.picker2.activePicker = "YEAR"));
    }
  },
  created: function() {
    let id = this.$route.query.id;
    if (id) {
      http.get("/experience/" + id).then(response => {
        //console.log(response.data)
        this.experience = response.data;
        if (response.data.ability && response.data.ability.length > 0) {
          this.abilities = response.data.ability.split(",");
        }
      });
    }
  },
  data: () => ({
    panel: [0],
    menu: false,
    menu2: false,
    experience: {},
    ability: "",
    abilities: []
  }),
  methods: {
    pickStartDate(date) {
      this.$refs.menu.save(date);
    },
    pickEndDate(date) {
      this.$refs.menu2.save(date);
    },
    nextStep() {
      this.experience.ability = this.abilities.join(",");
      console.log(this.experience);
      if (this.experience.sn) {
        http.put("/experience", this.experience).then(response => {
          console.log(response);
          if (response.data === true) {
            this.$router.push("/experience-list");
            //alert("更新成功");
          } else {
            alert("更新失敗");
          }
        });
      } else {
        http.post("/experience", this.experience).then(response => {
          console.log(response);
          if (response.data === true) {
            this.$router.push("/experience-list");
            //alert("新增成功");
          } else {
            alert("新增失敗");
          }
        });
      }
    },
    onAbilityEnter() {
      if (this.ability.length > 0) {
        this.abilities.push(this.ability);
        this.ability = "";
      }
    },
    close(str) {
      this.abilities = this.abilities.filter(item => item !== str);
    }
  }
};
</script>

<style scoped>
/*span {*/
/*  color: dodgerblue;*/
/*}*/
</style>
