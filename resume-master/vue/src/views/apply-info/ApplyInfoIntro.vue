<template>
  <v-row justify="center">
    <v-col cols="10" md="10" lg="10">
      <!-- 套用履歷注意事項 -->
      <v-card class="mx-auto my-5 pa-0">
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
              <h3 justify="center">您選擇的履歷版顯示內容及數量如下：</h3>
              <p class="subtitle-2 my-2">
                此版型設計簡潔乾淨，分為左右欄，以綠、黃、灰為主要顏色適合應徵工程師、程式設計師…等使用。
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
                      >基本資料：8項</v-list-item-title
                    >
                    <v-list-item-title class="subtitle-2 my-1"
                      >教育：2項</v-list-item-title
                    >
                    <v-list-item-title class="subtitle-2 my-1"
                      >工作經歷：3項</v-list-item-title
                    >
                    <v-list-item-title class="subtitle-2 my-1"
                      >專業技能：4項</v-list-item-title
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
                  to="/apply-info"
                  >重新選擇版型</v-btn
                >
              </v-col>
              <v-col cols="12" md="6" lg="6">
                <v-btn depressed large block color="primary" @click="nextStep"
                  >開始套用版型</v-btn
                >
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-card>

      <!-- 填寫基本資料注意事項 -->
      <v-card class="mx-auto my-5 pa-0">
        <v-row class="ma-0 py-5" justify="center">
          <v-col cols="9" md="4" lg="4" class="px-5 pt-5">
            <div justify="end">
              <v-responsive>
                <v-img
                  src="../../assets/interview.svg"
                  max-height="350"
                  contain
                >
                </v-img>
              </v-responsive>
            </div>
          </v-col>
          <v-col cols="9" md="5" lg="5" class="px-5 pt-0">
            <div justify="start" class="my-4">
              <h3 justify="center">個人基本資料（Personal information）</h3>
              <p class="subtitle-2 my-2">
                個人基本資料（Personal
                information），就是一個人的表徵，出方便僱主查閱。記得列上聯絡方式，如：聯絡電話、E-mail，不然我想請你，也連絡不到你阿。
              </p>
              <p class="subtitle-2 my-2">
                在台灣求職，在履歷表上附上照片是必要的，照片的選擇可以根據履歷表的類型、風格做相應地調整。一般個人履歷表的照片只要畫面乾淨清晰，人像大小適當即可，照片的選擇要依循以下
                3 個要點：
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
                    <v-list-item-title class="subtitle-2 my-1"
                      >臉部大小比例適當的正面照 (自拍照除外)</v-list-item-title
                    >
                    <v-list-item-title class="subtitle-2 my-1"
                      >無閑雜人等的個人照</v-list-item-title
                    >
                    <v-list-item-title class="subtitle-2 my-1"
                      >背景顏色以淺色為主</v-list-item-title
                    >
                  </v-list-item-content>
                </v-list-item>
              </v-alert>
            </div>
            <v-row justify="start" class="mb-2">
              <v-col cols="10" md="5" lg="5">
                <v-btn depressed large block color="primary" @click="nextStep"
                  >我瞭解了，開始填寫基本資料！</v-btn
                >
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-card>

      <!-- 填寫學歷注意事項 -->
      <v-card class="mx-auto my-5 pa-0">
        <v-row class="ma-0 py-5" justify="center">
          <v-col cols="9" md="4" lg="4" class="px-5 pt-5">
            <div justify="end">
              <v-responsive>
                <v-img
                  src="../../assets/education.svg"
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
                <v-btn depressed large block color="primary" @click="nextStep"
                  >我瞭解了，開始填寫學歷！</v-btn
                >
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-card>

      <!-- 填寫工作經歷注意事項 -->
      <v-card class="mx-auto my-5 pa-0">
        <v-row class="ma-0 py-5" justify="center">
          <v-col cols="9" md="4" lg="4" class="px-5 pt-5">
            <div justify="end">
              <v-responsive>
                <v-img src="../../assets/job.svg" max-height="350" contain>
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
                <v-btn depressed large block color="primary" @click="nextStep"
                  >我瞭解了，開始填寫工作經歷！</v-btn
                >
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-card>

      <!-- 填寫專業技能注意事項 -->
      <v-card class="mx-auto my-5 pa-0">
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
                <v-btn depressed large block color="primary" @click="nextStep"
                  >我瞭解了，開始填寫專業技能！</v-btn
                >
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-card>

      <!-- 填寫自傳注意事項 -->
      <v-card class="mx-auto my-5 pa-0">
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
              <p class="subtitle-2 my-2">
                主要目的在於說服人資主管為什麼該選擇你進入面試階段，和另外一項求職文件『求職信』有異曲同工之妙。
              </p>
              <p class="subtitle-2 my-2">
                建議求職者於撰寫自傳時，可以多展現自己適合該職缺/公司的個人特質及工作價值觀，同時利用自己的個人興趣及背景經歷做背書；此外，可以附上自己的職涯規劃。
              </p>
            </div>
            <v-row justify="start" class="mb-2">
              <v-col cols="10" md="5" lg="5">
                <v-btn depressed large block color="primary" @click="nextStep"
                  >我瞭解了，開始填寫自傳！</v-btn
                >
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-card>

      <!-- 開始套用履歷 -->
      <v-card class="mx-auto my-5 pa-0">
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
                <v-btn depressed large block color="primary" @click="nextStep"
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
import theStepper from "../../components/theStepperApply";
import theDialog from "../../components/theDialog";
export default {
  components: {
    theStepper,
    theDialog
  },
  watch: {},
  created: function() {},
  data: () => ({
    panel: [0]
  }),
  methods: {}
};
</script>

<style scoped>
/*span {*/
/*  color: dodgerblue;*/
/*}*/
</style>
