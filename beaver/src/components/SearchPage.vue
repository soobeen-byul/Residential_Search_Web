<template>
  <div class="background">
    <div>
        <ul v-if="Object.keys(this.$store.state.responseData).length > 0">
          <li v-for="(value, key) in this.$store.state.responseData" :key="key">{{ value }}</li>
        </ul>
        <p v-else>조회된 정보가 없습니다</p>
      </div>
  </div>
  
</template>
<script>
export default {
  name: 'SearchPage',

  data() {
    return {
    }
  },

  mounted() {
  },

  methods: {
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

.background{
  height: 100vh;
  /* overflow: hidden; */
  /* background-image: radial-gradient( circle farthest-corner at 10% 20%,  rgba(248,213,214,1) 0%, rgba(243,242,229,1) 90% ); */
  background-image: radial-gradient( circle farthest-corner at 10% 20%,  rgba(226,240,254,1) 0%, rgba(255,247,228,1) 90% );
  /* background-image: linear-gradient( 174.2deg,  rgba(255,244,228,1) 7.1%, rgba(240,246,238,1) 67.4% ); */
}

</style>