// 0. 如果使用模块化机制编程，导入Vue和VueRouter，要调用 Vue.use(VueRouter)

// 1. 定义 (路由) 组件。
// 可以从其他文件 import 进来
Vue.use(httpVueLoader);

// 2. 定义路由
// 每个路由应该映射一个组件。 其中"component" 可以是
// 通过 Vue.extend() 创建的组件构造器，
// 或者，只是一个组件配置对象。
// 我们晚点再讨论嵌套路由。
const routes = [
  {path: '/demo', component: httpVueLoader('/demo.vue')},
  {path: '/home', component: httpVueLoader('/vue/portal/home.vue')},

  {path: '/about', component: httpVueLoader('/vue/portal/about.vue')},
  {path: '/culture', component: httpVueLoader('/vue/portal/culture.vue')},
  {path: '/honor', component: httpVueLoader('/vue/portal/honor.vue')},

  {path: '/notice', component: httpVueLoader('/vue/portal/notice.vue')},
  {path: '/notice-detail', component: httpVueLoader('/vue/portal/notice-detail.vue')},
  {path: '/news', component: httpVueLoader('/vue/portal/news.vue')},
  {path: '/news-detail', component: httpVueLoader('/vue/portal/news-detail.vue')},

  {path: '/products', component: httpVueLoader('/vue/portal/products.vue')},
  {path: '/products-detail', component: httpVueLoader('/vue/portal/products-detail.vue')},
  {path: '/friends', component: httpVueLoader('/vue/portal/friends.vue')},
  {path: '/contact', component: httpVueLoader('/vue/portal/contact.vue')},

];

// 3. 创建 router 实例，然后传 `routes` 配置
// 你还可以传别的配置参数, 不过先这么简单着吧。
const router = new VueRouter({
  routes // (缩写) 相当于 routes: routes
});


// 4. 创建和挂载根实例。
// 记得要通过 router 配置参数注入路由，
// 从而让整个应用都有路由功能

const app = new Vue({
  el: '#app',
  router,
  components: {},
  data() {
    return {
      userInfo: {
        username:'',
      },
      welcome: true,
      outerVisible: true,
      innerVisible: false,
      show: {
        mineInfo: false,
        changePassword: false,
        ordersList: false,
      }
    }
  },
  mounted() {
    this.getUserInfo()
    // router.push({path: '/home', params: {userId: 123}})
  },
  methods: {
    clickMenu(url) {
      this.welcome = false;
      router.push({path: url, params: {userId: 123}});
      console.log(url);
    },
    getUserInfo() {
      axios.get(`/getUserInfo`).then(response => {
        const result = response.data;
        console.log('通过api获取到的数据:', result);
        if (result.status !== 200) {
          this.$message.error('数据加载失败');
          return
        }
        this.userInfo = result.data ? result.data : null;
      }).catch(function (error) {
        console.log('请求出现错误:', error);
      });
    },
    openOrderList() {
      this.show.ordersList = true;
      // this.$prompt('请输入邮箱', '提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
      //   inputErrorMessage: '邮箱格式不正确'
      // }).then(({value}) => {
      //   this.$message({
      //     type: 'success',
      //     message: '你的邮箱是: ' + value
      //   });
      // }).catch(() => {
      //   this.$message({
      //     type: 'info',
      //     message: '取消输入'
      //   });
      // });
    },
    saveUserInfo() {
      this.show.ordersList = true;

    },
  }
});
