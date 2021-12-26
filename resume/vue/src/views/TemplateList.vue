<template>
  <v-row justify="center">
    <v-col cols="10" md="10" lg="10">
      <v-card>
        <v-tabs color="primary" left class="pa-5">
          <v-tab><h3>履歷表模版</h3></v-tab>
          <v-tab><h3>網站模版</h3></v-tab>

          <v-tab-item>
            <v-row dense fluid>
              <v-col
                class="mt-5"
                v-for="(item, index) in templates"
                :key="index"
                cols="3"
                md="3"
                lg="3"
              >
                <v-card class="ma-1">
                  <v-dialog width="535px">
                    <template v-slot:activator="{ on, attrs }">
                      <v-img
                        v-bind="attrs"
                        v-on="on"
                        :src="item.image"
                        :lazy-:src="item.image"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="350px"
                        aspect-ratio="1"
                      >
                        <v-card-title
                          v-text="`履歷模版${index + 1}`"
                        ></v-card-title>
                      </v-img>
                    </template>
                    <v-card>
                      <v-img
                        class="white--text align-end"
                        height="700px"
                        width="535px"
                        :src="item.image"
                        :lazy-:src="item.image"
                      >
                      </v-img>
                    </v-card>
                  </v-dialog>
                  <v-card-actions>
                    <v-spacer></v-spacer>

                    <v-dialog width="535px">
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn icon v-bind="attrs" v-on="on">
                          <v-icon title="預覽履歷" alt="預覽履歷"
                            >mdi-eye-outline</v-icon
                          >
                        </v-btn>
                      </template>

                      <v-card>
                        <v-img
                          class="white--text align-end"
                          height="700px"
                          width="535px"
                          :src="item.image"
                          :lazy-:src="item.image"
                        >
                          <!--                          <v-card-title>Top 10 Australian beaches</v-card-title>-->
                        </v-img>
                      </v-card>
                    </v-dialog>

                    <v-btn icon @click="apply(item.id)" :disabled="item.id > 2">
                      <v-icon title="套用履歷" alt="套用履歷"
                        >mdi-bookmark-box-multiple-outline</v-icon
                      >
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>
          </v-tab-item>

          <v-tab-item>
            <v-row dense fluid>
              <v-col
                class="mt-5"
                v-for="(item, index) in websites"
                :key="index"
                cols="3"
                md="3"
                lg="3"
              >
                <v-card class="ma-1">
                  <v-dialog width="535px">
                    <template v-slot:activator="{ on, attrs }">
                      <v-img
                        v-bind="attrs"
                        v-on="on"
                        :src="item.image"
                        :lazy-:src="item.image"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="350px"
                        aspect-ratio="1"
                      >
                        <v-card-title
                          v-text="`網站模版${index + 1}`"
                        ></v-card-title>
                      </v-img>
                    </template>
                    <v-card>
                      <v-img
                        class="white--text align-end"
                        height="700px"
                        width="535px"
                        :src="item.image"
                        :lazy-:src="item.image"
                      >
                      </v-img>
                    </v-card>
                  </v-dialog>
                  <v-card-actions>
                    <v-spacer></v-spacer>

                    <v-dialog width="535px">
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn icon v-bind="attrs" v-on="on">
                          <v-icon title="預覽網站" alt="預覽網站"
                            >mdi-eye-outline</v-icon
                          >
                        </v-btn>
                      </template>

                      <v-card>
                        <v-img
                          class="white--text align-end"
                          height="700px"
                          width="535px"
                          :src="item.image"
                          :lazy-:src="item.image"
                        >
                        </v-img>
                      </v-card>
                    </v-dialog>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>
          </v-tab-item>
        </v-tabs>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import http from "../utils/http";
export default {
  watch: {},
  created: function() {
    this.resumeId = this.$route.query.resumeId;

    http.get("/template").then(response => {
      this.templates = response.data;
      for (let i = 0; i < 12; i++) {
        if (i === 0 || i === 1) {
          this.templates[i][
            "image"
          ] = require("../assets/resume_template/resume" + (i + 1) + ".png");
        } else {
          this.templates.push({
            id: i + 100,
            image: require("../assets/resume_template/resume" +
              (i + 1) +
              ".png")
          });
        }
      }
    });

    for (let i = 0; i < 3; i++) {
      this.websites.push({
        image: require("../assets/web_template/web" + (i + 1) + ".png")
      });
    }
  },
  data: () => ({
    templates: [],
    resumeId: null,
    websites: []
  }),
  methods: {
    apply(templateId) {
      if (this.resumeId > 0) {
        http
          .put("/resume/".concat(this.resumeId, "/template/", templateId))
          .then(response => {
            if (response.data == true) {
              this.$router.push({
                path: "/apply-info",
                query: { resumeId: this.resumeId }
              });
            } else {
              alert("操作失敗");
            }
          });
      } else {
        this.$router.push({ path: "/resume", query: { templateId } });
      }
    }
  }
};
</script>
