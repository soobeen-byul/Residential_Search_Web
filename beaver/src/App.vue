<template>
  <div id="app">
    <!-- <Header /> -->
    <router-view></router-view>
  </div>
</template>

<script>
// import Header from './components/HeaderView.vue';


export default {
  name: 'App',
  // components: {
    // Header/
  // }
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