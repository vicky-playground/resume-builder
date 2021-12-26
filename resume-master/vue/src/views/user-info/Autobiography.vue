<template>
  <v-row justify="center">
    <v-col cols="12" md="10" lg="9">
      <theStepper step="5"></theStepper>
      <v-expansion-panels
        v-model="panel"
        multiple
        class="mt-4"
        v-if="confirm && !pickInfo"
      >
        <v-expansion-panel>
          <v-expansion-panel-header color="blue">
            <template v-slot:actions>
              <v-icon color="white">$expand</v-icon>
            </template>
            <span class="white--text text-h6">自傳</span>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-row class="mt-6" no-gutters justify="center">
              <v-col cols="12" md="10" lg="8" class="d-flex justify-end">
                <theDialog>
                  <template v-slot:btn>
                    <v-icon color="blue">mdi-chat-question</v-icon>看看範例
                  </template>
                  <template v-slot:text>
                    我是典型的射手座，熱愛美食，對異國文化與藝術感興趣，面對事物總抱持樂觀，喜愛四處旅行，藉此汲取自己的美術靈感。<br />
                    在學時就讀輔仁大學
                    應用美術系，因其是一所國際綜合大學，時常要與各種不同文化、背景的同學相處及合作，在學期間除學習系上課程內容，培養專業技能及設計工具，更積極去參與國內相關藝術設計競賽。<br />
                    第一份工作從事至今，讓我更加深刻要從事設計相關工作是一點點的累積培養，主要負責工作是公司內部美術設計，包括活動物品設計、DM設計、場地布置，為精進自己的網站美術設計品質，另外學會HTML、CSS、JaveScript前端程式語言，並於2019年參加台灣國際平面設計展銀獎，感謝公司的栽培與肯定，在美術設計及相關領域上我仍期許自己能更精進一步。
                  </template>
                </theDialog>
              </v-col>
            </v-row>
            <v-row no-gutters justify="center">
              <v-col cols="12" md="10" lg="8">
                <v-textarea
                  label="自傳(中文)"
                  outlined
                  rows="6"
                  auto-grow
                  no-resize
                  maxLength="1000"
                  counter="1000"
                  v-model="autobiography.chinese"
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row class="mt-6" no-gutters justify="center">
              <v-col cols="12" md="10" lg="8" class="d-flex justify-end">
                <theDialog>
                  <template v-slot:btn>
                    <v-icon color="blue">mdi-chat-question</v-icon>看看範例
                  </template>
                  <template v-slot:text>
                    Dear Hiring Manager,<br />
                    I am looking for a position as a UIUX designer. With
                    extensive experience in designing and implementing web-based
                    user interfaces, I am well suited to this position.<br />
                    For over one year I have been a designer at XYZ company. I’m
                    not only responsible for graphic designs but also use HTML,
                    CSS, and JavaScript to direct and design front-end projects
                    from concept to completion. I have experience managing web
                    and interactive communication projects from initial
                    consultation through successful launch and have a proven
                    ability to handle various projects at the same time and
                    complete them by given deadline.<br />
                    Some highlights of my experience include:<br />
                    <ul>
                      <li>
                        Collaborated with developers and designers using
                        Subversion for version control
                      </li>
                      <li>
                        Completed UI redesign of website using usability case
                        studies, wireframes, and prototypes to create a fully
                        functional web application
                      </li>
                      <li>
                        Designed and developed interactive prototypes and
                        mockups using HTML5, CSS3, and jQuery for future
                        enhancements of the website, then implemented them on
                        the live web platform
                      </li>
                      <li>
                        Worked with the development team to correct any UI
                        problems or HTML/CSS issues
                      </li>
                    </ul>
                  </template>
                </theDialog>
              </v-col>
            </v-row>
            <v-row no-gutters justify="center">
              <v-col cols="12" md="10" lg="8">
                <v-textarea
                  label="自傳(英文)"
                  outlined
                  rows="6"
                  auto-grow
                  no-resize
                  maxLength="2000"
                  counter="2000"
                  v-model="autobiography.english"
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row justify="center" class="mb-2">
              <v-col cols="3" md="3" lg="3">
                <v-btn depressed large block color="primary" to="/skill"
                  >上一步</v-btn
                >
              </v-col>
              <v-col cols="3" md="3" lg="3">
                <v-btn depressed large block color="primary" @click="save"
                  >儲存</v-btn
                >
              </v-col>
              <v-col cols="3" md="3" lg="3">
                <v-btn
                  depressed
                  large
                  block
                  color="primary"
                  @click="pickInfo = true"
                  >挑選履歷版型</v-btn
                >
              </v-col>
            </v-row>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
      <!-- 填寫自傳注意事項 -->
      <v-card class="mx-auto my-5 pa-0" v-if="!confirm">
        <v-row class="ma-0 py-5" justify="center">
          <v-col cols="9" md="4" lg="4" class="px-5 pt-5">
            <div justify="end">
              <v-responsive>
                <v-img src="../../assets/aboutme.svg" max-height="350" contain>
                </v-img>
              </v-responsive>
            </div>
          </v-col>
          <v-col cols="9" md="5" lg="5" class="px-5 pt-0">
            <div justify="start" class="my-4">
              <h3 justify="center">自傳（Autobiography）</h3>

              <!--              <ul>-->
              <!--                <li>分段描述：自我介紹、特殊表現、自我期許，並圍繞著職務的要求來說明唷！</li>-->
              <!--                <li>藉由自我介紹讓雇主快速地對您有良好印象，在此處展現自己所累積的相關年資與經驗，以及與應徵職務有關的強項。-->
              <!--                  例如：你應徵的職位是 UI 設計師，可以先簡單介紹過往任職公司的業務狀況，藉此突顯該公司的行業地位，再進一步說明你個人主要負責哪些設計工作，而對於這些項目，同事及客戶有沒有什麼評價及迴響等等，最後總結關於目前應徵的職位，你比其他候選人優秀的地方。-->
              <!--                </li>-->
              <!--                <li>善用數字描述您的特殊表現，若有團隊合作經驗則可以特別説明。-->
              <!--                  例如：在領導網站內容的視覺設計過程中，提升設計時程的效率 70%，並與行銷團隊共同負責業務廣告提案，包含版面設計構成、社群圖文經營與圖案發想。同時與工程師透過 A/B 測試優化網站與產品的視覺、用戶體驗流程，大幅降低 25% 跳出率並提升 13% 轉換。-->
              <!--                </li>-->
              <!--                <li>在自我期許這個段落中主要是用來結束自傳的，總結過去經歷帶給自己的心得或技能後，闡述您對應徵公司和職位的渴望，以及如何將您的經歷、技能與該職務結合，達成該公司對該職務的期許。-->
              <!--                  例如：過往的學習經歷都是在高壓的環境下磨練技能產出作品，使我有紮實的技術和強大的抗壓性。我認為設計是行銷的媒介，一個產品或文案的出現，是在回覆市場上尚未被滿足的部分，在設計裡我們透過創造形象帶出功能來與消費者溝通，讓設計成為溝通的主體。我希望能活用自身的專長與公司合作探索更多可能性，在設計這條路上不要侷限自己，持續做出讓別人眼睛一亮並留下印象的設計。在未來的職場生涯，期許自己能持續保有細膩的觀察及設計的熱情，利用好的作品來傳達產品的核心價值給用戶。</li>-->
              <!--              </ul>-->

              <p class="subtitle-2 my-2">
                主要目的在於說服人資主管為什麼該選擇你進入面試階段，和另外一項求職文件『求職信』有異曲同工之妙。
              </p>
              <p class="subtitle-2 my-2">
                建議求職者於撰寫自傳時，可以多展現自己適合該職缺/公司的個人特質及工作價值觀，同時利用自己的個人興趣及背景經歷做背書；此外，可以附上自己的職涯規劃。
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
                  >我瞭解了，開始填寫自傳！</v-btn
                >
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-card>

      <!-- 開始套用履歷 -->
      <v-card class="mx-auto my-5 pa-0" v-if="pickInfo">
        <v-row class="ma-0 py-5" justify="center">
          <v-col cols="9" md="9" lg="9" class="px-5 pt-5">
            <div justify="center">
              <v-responsive>
                <v-img
                  src="../../assets/certification.svg"
                  max-height="300"
                  contain
                >
                </v-img>
              </v-responsive>
            </div>
          </v-col>
          <v-col cols="9" md="9" lg="9" class="px-5 pt-0">
            <div justify="center" class="my-4">
              <p class="subtitle-2 my-2 text-center">
                已經完成了履歷撰寫，現在讓我們來挑選要套用的版型吧！
              </p>
            </div>
            <v-row justify="center" class="mb-2">
              <v-col cols="10" md="5" lg="5">
                <v-btn depressed large block color="primary" to="/resume"
                  >立即挑選履歷版型！</v-btn
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
  created() {
    http.get("/user").then(response => {
      this.autobiography.chinese = response.data.bioChn;
      this.autobiography.english = response.data.bioEng;
    });
  },
  data: () => ({
    panel: [0],
    autobiography: {
      chinese: "",
      english: ""
    },
    confirm: false,
    pickInfo: false
  }),
  methods: {
    save() {
      console.log(this.autobiography);
      http.put("/user/autobiography", this.autobiography).then(response => {
        console.log(response);
        if (response.data === true) {
          alert("更新成功");
        } else {
          alert("更新失敗");
        }
      });
    }
  }
};
</script>

<style scoped></style>
