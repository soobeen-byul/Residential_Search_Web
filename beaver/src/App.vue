<template>
  <div id="app">
    <!-- <Header /> -->
    <router-view></router-view>
  </div>
</template>

<script>
// import HelloWorld from './components/HelloWorld.vue';

export default {
  name: 'App',
  //  components: {
  //    HelloWorld
  //  },
  //  mounted() {
  //   request('http://localhost:8080/api/hello', function(error, response, body){
  //     window.console.log('error',error);
  //     window.console.log('statusCode',response && response.statusCode);
  //     window.console.log('body',body);
  //   })
  //  }
}
</script>

<style>
#app {
  /* font-family: Avenir, Helvetica, Arial, sans-serif; */
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
  margin-top: 0px;
}

.v-application {
  font-family: 'NanumSquareNeo', sans-serif !important;
}
@font-face {
  font-family:'NanumSquareNeo';
  src: url('./assets/fonts/NanumSquareNeo-eHv.ttf') format('truetype');
  font-weight:700
}
@font-face {
  font-family:'NanumSquareNeo';
  src: url('./assets/fonts/NanumSquareNeo-cBd.ttf') format('truetype');
  font-weight:500 
}
@font-face {
  font-family:'NanumSquareNeo';
  src: url('./assets/fonts/NanumSquareNeo-bRg.ttf') format('truetype');
  font-weight: 300
}

</style>
