<template>
  <v-row justify="center">
    <v-col cols="12" md="10" lg="9">
      <theStepper step="2"></theStepper>
      <v-expansion-panels v-model="panel" multiple class="mt-4" v-if="confirm">
        <v-expansion-panel>
          <v-expansion-panel-header color="blue" hide-actions>
            <v-row>
              <v-col><span class="white--text text-h6">學歷</span></v-col>
              <v-col class="d-flex justify-end">
                <v-btn
                  text
                  color="white"
                  @click.native.stop="routeTo('/education')"
                >
                  <v-icon left>
                    mdi-plus-circle
                  </v-icon>
                  新增學歷
                </v-btn>
              </v-col>
            </v-row>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row
              class="mt-4"
              justify="center"
              v-for="(education, index) in educations"
              :key="index"
            >
              <v-col cols="12" md="10" lg="8">
                <v-card elevation="2" outlined class="my-0 py-0"
                  ><v-card-title
                    >{{ education.schoolName }}({{
                      education.country
                    }})</v-card-title
                  >
                  <v-card-subtitle class="pb-0"
                    >{{ education.major }}｜{{ education.level
                    }}{{ education.status
                    }}<span class="float-right"
                      >{{ education.startDate }}~{{ education.endDate }}</span
                    ></v-card-subtitle
                  >
                  <v-card-actions>
                    <v-btn icon color="primary" @click="edit(education.id)">
                      <v-icon>mdi-square-edit-outline</v-icon>
                    </v-btn>
                    <v-btn icon color="red" @click="remove(education.id)">
                      <v-icon>mdi-delete-forever</v-icon>
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>

            <v-row justify="center" class="mb-2 mt-6">
              <v-col cols="6" md="5" lg="4">
                <v-btn depressed large block color="primary" to="/user-info"
                  >上一步</v-btn
                >
              </v-col>
              <v-col cols="6" md="5" lg="4">
                <v-btn
                  depressed
                  large
                  block
                  color="primary"
                  to="/experience-list"
                  >下一步</v-btn
                >
              </v-col>
            </v-row>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
      <!-- 填寫學歷注意事項 -->
      <v-card class="mx-auto my-5 pa-0" v-if="!confirm">
        <v-row class="ma-0 py-5" justify="center">
          <v-col cols="9" md="4" lg="4" class="px-5 pt-5">
            <div justify="end">
              <v-responsive>
                <v-img
                  src="../../../assets/education.svg"
                  max-height="350"
                  contain
                >
                </v-img>
              </v-responsive>
            </div>
          </v-col>
          <v-col cols="9" md="5" lg="5" class="px-5 pt-0">
            <div justify="start" class="my-4">
              <h3 justify="center">學歷（Education）</h3>
              <p class="subtitle-2 my-2">
                建議填寫較高的2至3項即可，並由順序最高、最近的往前推（研究所、大學、高中），最好可以註明修業起迄期間與特殊的學習事項。
              </p>
              <p class="subtitle-2 my-2">
                另外，若是有肄業的學歷則可視情況放進去，例如：你肄業的大學是屬於台大、成大、清大、交大、政大五校之一或是中字輩的大學，會建議還是把這段肄業學歷加上去，因為這會讓人明白，至少你擁有能力考上這種等級的大學。
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
                  >我瞭解了，開始填寫學歷！</v-btn
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
    educations: [],
    confirm: false
  }),
  methods: {
    init() {
      http.get("/education").then(response => {
        console.log(response);
        this.educations = response.data;
      });
    },
    edit(id) {
      this.$router.push("/education?id=" + id);
    },
    remove(id) {
      // TODO 警告視窗
      http.delete("/education/" + id).then(response => {
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
