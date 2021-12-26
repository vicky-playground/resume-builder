<template>
  <v-row justify="center">
    <v-col cols="12" md="10" lg="9">
      <theStepper non-linear step="1" v-if="confirm"></theStepper>
      <v-expansion-panels v-model="panel" multiple class="mt-4" v-if="confirm">
        <v-expansion-panel>
          <v-expansion-panel-header color="blue">
            <template v-slot:actions>
              <v-icon color="white">$expand</v-icon>
            </template>
            <span class="white--text text-h6">基本資料</span>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row no-gutters class="mt-6" justify="center">
              <v-col cols="10" md="10" lg="10">
                <v-alert outlined type="error" prominent border="left">
                  <h3 justify="center" class="my-green">
                    請勾選
                    <strong>{{ template.basicInfo }}</strong>
                    項，欲顯示在履歷表中的項目
                  </h3>
                  您目前已勾選 {{ selectedCount }} 項，若有不足，將以空白呈顯。
                </v-alert>
              </v-col>
            </v-row>
            <v-row no-gutters class="mt-6 mx-auto" justify="center">
              <v-col cols="10" md="5" lg="5" class="pa-0 mt-0">
                <v-checkbox
                  :label="'姓名: ' + user.name"
                  color="red darken-3"
                  value="name"
                  v-model="selected"
                ></v-checkbox>
              </v-col>
              <v-col cols="10" md="5" lg="5" class="pa-0 mt-0">
                <v-checkbox
                  :label="'生日: ' + user.birthday"
                  color="red darken-3"
                  value="birthday"
                  v-model="selected"
                ></v-checkbox>
              </v-col>
              <v-col cols="10" md="5" lg="5" class="pa-0 mt-0">
                <v-checkbox
                  :label="'性別: ' + user.sex"
                  color="red darken-3"
                  value="sex"
                  v-model="selected"
                ></v-checkbox>
              </v-col>
              <v-col cols="10" md="5" lg="5" class="pa-0 mt-0">
                <v-checkbox
                  :label="'服役狀況: ' + user.militaryService"
                  color="red darken-3"
                  value="militaryService"
                  v-model="selected"
                ></v-checkbox>
              </v-col>
              <v-col cols="10" md="5" lg="10" class="pa-0 mt-0">
                <v-checkbox
                  :label="'聯絡地址: ' + user.address"
                  color="red darken-3"
                  value="address"
                  v-model="selected"
                ></v-checkbox>
              </v-col>
              <v-col cols="10" md="5" lg="5" class="pa-0 mt-0">
                <v-checkbox
                  :label="'聯絡電話: ' + user.phone"
                  color="red darken-3"
                  value="phone"
                  v-model="selected"
                ></v-checkbox>
              </v-col>
              <v-col cols="10" md="5" lg="5" class="pa-0 mt-0">
                <v-checkbox
                  :label="'e-mail: ' + user.email"
                  color="red darken-3"
                  value="email"
                  v-model="selected"
                ></v-checkbox>
              </v-col>
              <v-col cols="10" md="10" lg="10" class="pa-0 mt-0">
                <v-checkbox
                  :label="'駕照: ' + user.driverLicense"
                  color="red darken-3"
                  value="driverLicense"
                  v-model="selected"
                ></v-checkbox>
              </v-col>
              <v-col
                cols="10"
                md="10"
                lg="10"
                class="pa-0 mt-0"
                justify="start"
              >
                <v-checkbox
                  :label="'特殊身分: ' + user.specialIdentity"
                  color="red darken-3"
                  value="specialIdentity"
                  v-model="selected"
                ></v-checkbox>
              </v-col>
            </v-row>

            <v-row no-gutters class="mt-6" justify="center">
              <v-col cols="10" md="10" lg="10" class="pa-0 mt-0">
                <v-checkbox
                  :label="'自我簡介: ' + user.introduction"
                  color="red darken-3"
                  value="introduction"
                  v-model="selected"
                ></v-checkbox>
              </v-col>
            </v-row>

            <v-row no-gutters class="mt-6" justify="center">
              <v-col cols="10" md="10" lg="10" class="pa-0 mt-0">
                <v-checkbox
                  :label="'個人特色: ' + user.feature"
                  color="red darken-3"
                  value="feature"
                  v-model="selected"
                ></v-checkbox>
              </v-col>
            </v-row>

            <!--            <v-row no-gutters class="mt-6" justify="center">-->
            <!--              <v-col cols="10" md="10" lg="10" class="pa-0 mt-0 mb-2">-->
            <!--                <v-checkbox-->
            <!--                  label="個人連結"-->
            <!--                  color="red darken-3"-->
            <!--                  value="個人連結"-->
            <!--                ></v-checkbox>-->
            <!--              </v-col>-->
            <!--            </v-row>-->
            <v-row justify="center" class="mb-2">
              <v-col cols="5" md="4" lg="4">
                <v-btn depressed large block color="primary" @click="nextStep"
                  >儲存，下一步</v-btn
                >
              </v-col>
            </v-row>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
      <!-- 套用履歷注意事項 -->
      <v-card class="mx-auto my-5 pa-0" v-if="!confirm">
        <v-row class="ma-0 py-5" justify="center">
          <v-col cols="9" md="4" lg="4" class="px-5 pt-5">
            <div justify="end">
              <v-responsive>
                <v-img
                  src="../../assets/resume_template/resume1.png"
                  max-height="350"
                  contain
                >
                </v-img>
              </v-responsive>
            </div>
          </v-col>
          <v-col cols="9" md="5" lg="5" class="px-5 pt-0">
            <div justify="start" class="my-4">
              <h3 justify="center">您選擇的履歷模板類型：專家</h3>
              <p class="subtitle-2 my-2">
                求職履歷格式的撰寫要點在於自身的經歷及成就，唯有借此展現過往職涯中習得的技能與經驗，才能凸顯出求職者的專長及領導能力。
              </p>
              <v-alert
                dense
                color="blue lighten-5"
                prominent
                border="left"
                class="pa-1"
              >
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title class="subtitle-2 my-1 text--blue"
                      >基本資料：{{ template.basicInfo }}項</v-list-item-title
                    >
                    <v-list-item-title class="subtitle-2 my-1"
                      >教育：{{ template.education }}項</v-list-item-title
                    >
                    <v-list-item-title class="subtitle-2 my-1"
                      >工作經歷：{{ template.experience }}項</v-list-item-title
                    >
                    <v-list-item-title class="subtitle-2 my-1"
                      >專業技能：{{
                        template.skill + template.licence + template.language
                      }}項</v-list-item-title
                    >
                  </v-list-item-content>
                </v-list-item>
              </v-alert>
              <p class="subtitle-2 my-2">確定要套用此版型嗎？</p>
            </div>
            <v-row justify="start" class="mb-2">
              <v-col cols="12" md="6" lg="6">
                <v-btn
                  depressed
                  large
                  block
                  outlined
                  color="primary"
                  :to="'/template-list?resumeId=' + this.resume.id"
                  >重新選擇版型</v-btn
                >
              </v-col>
              <v-col cols="12" md="6" lg="6">
                <v-btn
                  depressed
                  large
                  block
                  color="primary"
                  @click="confirm = true"
                  >開始套用版型</v-btn
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
        if (this.resume.basicInfo != null && this.resume.basicInfo.length > 0) {
          this.selected = this.resume.basicInfo.split(",");
        }
      })
      .then(() => {
        http.get("/template/" + this.resume.templateID).then(response => {
          this.template = response.data;
        });
      });
    http.get("/user").then(response => {
      this.user = response.data;
    });
  },
  data: () => ({
    panel: [0],
    user: {},
    selected: [],
    template: {},
    confirm: false,
    resume: {}
  }),
  methods: {
    nextStep() {
      console.log(this.user);
      // console.log(this.selected);
      if (this.selected.length > this.template.basicInfo) {
        alert("數量超出限制");
      } else {
        http
          .put("/resume/" + this.resume.id + "/basic-info", this.selected)
          .then(response => {
            if (response.data == true) {
              alert("成功");
              this.$router.push(
                "/educationinfo-apply?resumeId=" + this.resume.id
              );
            } else {
              alert("操作失敗");
            }
          });
      }
    }
  }
};
</script>
