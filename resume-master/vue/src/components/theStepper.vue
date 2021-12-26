<template>
  <v-stepper alt-labels v-model="step">
    <v-stepper-header>
      <v-stepper-step step="1" editable @click="routerTo('/user-info')">
        基本資料
      </v-stepper-step>

      <v-divider />
      <v-stepper-step step="2" editable @click="routerTo('/education-list')">
        學歷
      </v-stepper-step>

      <v-divider />
      <v-stepper-step step="3" editable @click="routerTo('/experience-list')">
        工作經歷
      </v-stepper-step>

      <v-divider />
      <v-stepper-step step="4" editable @click="routerTo('/skill')">
        專業技能
      </v-stepper-step>

      <v-divider />
      <v-stepper-step step="5" editable @click="routerTo('/autobiography')">
        自傳
      </v-stepper-step>
    </v-stepper-header>
  </v-stepper>
</template>

<script>
export default {
  props: ["step"],
  data: () => ({}),
  methods: {
    routerTo(path) {
      if (this.$router.currentRoute.path !== path) {
        this.$router.push({ path });
      }
    }
  }
};
</script>
