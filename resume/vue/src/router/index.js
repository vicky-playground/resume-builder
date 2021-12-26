import Vue from "vue";
import VueRouter from "vue-router";
import Index from "../views/Index";
import Login from "../views/account/Login";
import Signup from "../views/account/Signup";
import Privacy from "../views/Privacy";
import Terms from "../views/Terms";
import Setting from "../views/account/Setting";
import Forgetpw from "../views/account/Forgetpw";
import Resetpw from "../views/account/Resetpw";
import BasicInfo from "../views/user-info/BasicInfo";
import EducationList from "../views/user-info/education/EducationList";
import EducationInfo from "../views/user-info/education/EducationInfo";
import ExperienceList from "../views/user-info/experience/ExperienceList";
import ExperienceInfo from "../views/user-info/experience/ExperienceInfo";
import SkillInfo from "../views/user-info/SkillInfo";
import Autobiography from "../views/user-info/Autobiography";
import BasicInfoApply from "../views/apply-info/BasicInfoApply";
import EducationInfoApply from "../views/apply-info/EducationInfoApply";
import ExperienceInfoApply from "../views/apply-info/ExperienceInfoApply";
import SkillInfoApply from "../views/apply-info/SkillInfoApply";
import AutobiographyApply from "../views/apply-info/AutobiographyApply";
import ApplyInfoIntro from "../views/apply-info/ApplyInfoIntro";
import TemplateList from "../views/TemplateList";
import ResumeList from "../views/user-info/ResumeList";
import Resume from "../views/user-info/Resume";
import Website from "../views/WebsiteTemplate";
import Faq from "../views/Faq";
import store from "../store";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Index",
    component: Index,
    meta: {
      title: "首頁"
    }
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    meta: {
      title: "帳號登入"
    }
  },
  {
    path: "/forgetpw",
    name: "Forgetpw",
    component: Forgetpw,
    meta: {
      title: "忘記密碼"
    }
  },
  {
    path: "/resetpw",
    name: "Resetpw",
    component: Resetpw,
    meta: {
      title: "重設密碼"
    }
  },
  {
    path: "/signup",
    name: "Signup",
    component: Signup,
    meta: {
      title: "帳號註冊"
    }
  },
  {
    path: "/terms",
    name: "Terms",
    component: Terms,
    meta: {
      title: "使用條款"
    }
  },
  {
    path: "/privacy",
    name: "Privacy",
    component: Privacy,
    meta: {
      title: "隱私權政策"
    }
  },
  {
    path: "/template-list",
    name: "TemplateList",
    component: TemplateList,
    meta: {
      title: "履歷模版範本"
    }
  },
  {
    path: "/website",
    name: "Website",
    component: Website,
    meta: {
      title: "網站模版"
    }
  },
  {
    path: "/user-info",
    name: "BasicInfo",
    component: BasicInfo,
    meta: {
      title: "基本資料"
    }
  },
  {
    path: "/apply-info-intro",
    name: "ApplyInfoIntro",
    component: ApplyInfoIntro,
    meta: {
      title: "套用基本資料說明"
    }
  },
  {
    path: "/apply-info",
    name: "BasicInfoApply",
    component: BasicInfoApply,
    meta: {
      title: "套用基本資料"
    }
  },
  {
    path: "/faq",
    name: "Faq",
    component: Faq,
    meta: {
      title: "常見問題"
    }
  },
  {
    path: "/educationinfo-apply",
    name: "EducationInfoApply",
    component: EducationInfoApply,
    meta: {
      title: "套用學歷資料"
    }
  },

  {
    path: "/experienceinfo-apply",
    name: "ExperienceInfoApply",
    component: ExperienceInfoApply,
    meta: {
      title: "套用工作經歷"
    }
  },
  {
    path: "/skillinfo-apply",
    name: "SkillInfoApply",
    component: SkillInfoApply,
    meta: {
      title: "套用專業技能"
    }
  },

  {
    path: "/autobiography-apply",
    name: "AutobiographyApply",
    component: AutobiographyApply,
    meta: {
      title: "套用自傳"
    }
  },

  {
    path: "/education",
    name: "EducationInfo",
    component: EducationInfo,
    meta: {
      title: "學歷"
    }
  },
  {
    path: "/experience",
    name: "ExperienceInfo",
    component: ExperienceInfo,
    meta: {
      title: "工作經歷"
    }
  },
  {
    path: "/skill",
    name: "SkillInfo",
    component: SkillInfo,
    meta: {
      title: "專業技能"
    }
  },
  {
    path: "/autobiography",
    name: "Autobiography",
    component: Autobiography,
    meta: {
      title: "自傳"
    }
  },
  {
    path: "/experience-list",
    name: "ExperienceList",
    component: ExperienceList,
    meta: {
      title: "工作經歷列表"
    }
  },
  {
    path: "/education-list",
    name: "EducationList",
    component: EducationList,
    meta: {
      title: "學歷列表"
    }
  },
  {
    path: "/setting",
    name: "Setting",
    component: Setting,
    meta: {
      title: "帳號設定"
    }
  },
  {
    path: "/resume-list",
    name: "ResumeList",
    component: ResumeList,
    meta: {
      title: "履歷列表"
    }
  },
  {
    path: "/resume",
    name: "Resume",
    component: Resume,
    meta: {
      title: "履歷表"
    }
  },
  {
    path: "*",
    redirect: "/"
  }
];

const router = new VueRouter({
  mode: "history",
  // base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title;
  }

  // console.log(from);
  let allow = false;
  let allowPass = [
    "/",
    "/login",
    "/forgetpw",
    "/resetpw",
    "/signup",
    "/terms",
    // "/apply-info",
    // "/educationinfo-apply",
    // "/experienceinfo-apply",
    "/privacy",
    // "/user-info",
    // "/education-list",
    // "/education",
    // "/experience-list",
    // "/experience",
    // "/skill",
    // "/autobiography",
    // "/skillinfo-apply",
    // "/autobiography-apply",
    "/apply-info-intro",
    // "/website",
    "/template-list",
    "/faq"
    // "/setting"
  ];
  allowPass.forEach(path => {
    if (path === to.path) {
      allow = true;
    }
  });

  if (allow) {
    next();
  } else if (localStorage.getItem("user")) {
    let user = JSON.parse(localStorage.getItem("user"));
    if (user.expiration >= Date.now()) {
      next();
    } else {
      store.commit("logout");
      // next({ path: "/login" });
      next({ path: "/login", query: { redirect: to.path } });
    }
  } else {
    next({ path: "/login", query: { redirect: to.path } });
  }
});

export default router;
