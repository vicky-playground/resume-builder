<template>
  <v-row justify="center">
    <v-col cols="12" md="10" lg="9">
      <theStepper step="3"></theStepper>
      <v-expansion-panels v-model="panel" multiple class="mt-4" v-if="confirm">
        <v-expansion-panel>
          <v-expansion-panel-header color="blue" hide-actions>
            <v-row>
              <v-col><span class="white--text text-h6">工作經歷</span></v-col>
              <v-col class="d-flex justify-end">
                <v-btn
                  text
                  color="white"
                  @click.native.stop="routeTo('/experience')"
                >
                  <v-icon left>
                    mdi-plus-circle
                  </v-icon>
                  新增工作經歷
                </v-btn>
              </v-col>
            </v-row>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row
              class="mt-4"
              justify="center"
              v-for="(experience, index) in experiences"
              :key="index"
            >
              <v-col cols="12" md="10" lg="8">
                <v-card elevation="2" outlined class="my-0 py-0"
                  ><v-card-title>{{ experience.position }}</v-card-title>
                  <v-card-subtitle class="pt-1"
                    >{{ experience.companyName
                    }}<span class="float-right"
                      >{{ experience.startDate }}~{{ experience.endDate }}</span
                    ></v-card-subtitle
                  >
                  <v-divider class="mx-4 mb-1"></v-divider>
                  <v-card-text class="py-1">{{
                    experience.experienceDesc
                  }}</v-card-text>
                  <v-card-text
                    class="py-1"
                    v-if="
                      experience.ability.split(',').filter(s => s.length > 0)
                        .length > 0
                    "
                  >
                    <v-chip
                      v-for="(ability, index) in experience.ability.split(',')"
                      :key="index"
                      color="blue"
                      text-color="white"
                      class="mr-2"
                      >{{ ability }}</v-chip
                    >
                  </v-card-text>
                  <v-card-actions>
                    <v-btn icon color="primary" @click="edit(experience.sn)">
                      <v-icon>mdi-square-edit-outline</v-icon>
                    </v-btn>
                    <v-btn icon color="red" @click="remove(experience.sn)">
                      <v-icon>mdi-delete-forever</v-icon>
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>

            <v-row justify="center" class="mb-2 mt-6">
              <v-col cols="6" md="5" lg="4">
                <v-btn
                  depressed
                  large
                  block
                  color="primary"
                  to="/education-list"
                  >上一步</v-btn
                >
              </v-col>
              <v-col cols="6" md="5" lg="4">
                <v-btn depressed large block color="primary" to="/skill"
                  >下一步</v-btn
                >
              </v-col>
            </v-row>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
      <!-- 填寫工作經歷注意事項 -->
      <v-card class="mx-auto my-5 pa-0" v-if="!confirm">
        <v-row class="ma-0 py-5" justify="center">
          <v-col cols="9" md="4" lg="4" class="px-5 pt-5">
            <div justify="end">
              <v-responsive>
                <v-img src="../../../assets/job.svg" max-height="350" contain>
                </v-img>
              </v-responsive>
            </div>
          </v-col>
          <v-col cols="9" md="5" lg="5" class="px-5 pt-0">
            <div justify="start" class="my-4">
              <h3 justify="center">工作經歷（Work Experience）</h3>
              <p class="subtitle-2 my-2">
                建議由最新、較近期的工作經歷向後排序。工作實力會隨著經歷的增加而提升，最新的工作經歷往往能概括之前的收穫。而面試官在閱讀履歷時，通常都是由前往後、由上往下閱讀，因此在面試官注意力最集中時，放上近期的成果，也能避免自己的工作能力被眾多的履歷埋沒。
              </p>
              <p class="subtitle-2 my-2">
                將工作經歷由新放到舊，不但可以用最近的工作成就加深面試官對你的印象，也更能完整呈現<span
                  class="primary--text px-2"
                  ><strong>你「現在」在職場上的價值。</strong></span
                >
              </p>
              <p class="subtitle-2 my-2">
                履歷表的最大用途，就在於幫自己「行銷」，讓自己看起來適合應徵的職位，因此有時候適時地隱藏一些不必要的資訊是可被接受的。其中，最常見的案例，就像是做不到3個月就離職，甚至不到幾天的工作經歷，就不太建議一一列在個人履歷上
              </p>
              <p class="subtitle-2 my-2">
                對於剛畢業的社會新鮮人，如：打工/實習、社團/組織/活動、產學合作…等在學經歷，都可以填進去喔！
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
                  >我瞭解了，開始填寫工作經歷！</v-btn
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
import theStepper from "../../../components/theStepper";
import theDialog from "../../../components/theDialog";
import http from "../../../utils/http";
export default {
  components: {
    theStepper,
    theDialog
  },
  created: function() {
    this.init();
  },
  data: () => ({
    panel: [0],
    experiences: [],
    confirm: false
  }),
  methods: {
    init() {
      http.get("/experience").then(response => {
        console.log(response);
        this.experiences = response.data;
      });
    },
    edit(id) {
      this.$router.push("/experience?id=" + id);
    },
    remove(id) {
      http.delete("/experience/" + id).then(response => {
        if (response.data === true) {
          this.init();
        } else {
          alert("刪除失敗");
        }
      });
    },
    routeTo(path) {
      this.$router.push(path);
    }
  }
};
</script>
