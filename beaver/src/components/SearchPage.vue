<template>
  <div class="d-flex flex-nowrap background">
    <div class="d-flex flex-column align-items-stretch flex-shrink-0 bg-body-tertiary" style="width: 380px;">
      <a href="/" class="d-flex align-items-center flex-shrink-0 p-3 link-body-emphasis text-decoration-none border-bottom">
        <img alt="logo" src="../assets/beaver.png" width="50" href="/">
        <span style="font-size: 20px; font-family:NanumSquareNeo; font-weight:700;">&nbsp;&nbsp;서울 비버의 내 집 마련</span>
      </a>
      <div class="list-group list-group-flush border-bottom scrollarea">
        <a href="#" class="list-group-item list-group-item-action active py-3 lh-sm" aria-current="true">
          <div class="d-flex w-100 align-items-center justify-content-between">
            <strong class="mb-1">List group item heading</strong>
            <small>Wed</small>
          </div>
          <div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
        </a>
        <a href="#" class="list-group-item list-group-item-action py-3 lh-sm">
          <div class="d-flex w-100 align-items-center justify-content-between">
            <strong class="mb-1">List group item heading</strong>
            <small class="text-body-secondary">Tues</small>
          </div>
          <div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
        </a>
        <a href="#" class="list-group-item list-group-item-action py-3 lh-sm">
          <div class="d-flex w-100 align-items-center justify-content-between">
            <strong class="mb-1">List group item heading</strong>
            <small class="text-body-secondary">Mon</small>
          </div>
          <div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
        </a>
      </div>
    </div>
    <div class="col-md">
      <div class="container-fluid" style="padding:15px;text-align: center;">
        <div class="hstack gap-3" style="padding-bottom:20px;">
        <div class="daummap d-grid col-md-3 mx-auto">
          <button type="button" class="btn btn-secondary btn-block" @click="showApi">직장/학교 주소 검색하기</button>
        </div>
        <span><font-awesome-icon icon="fa-solid fa-house" /></span>
        <div class="d-grid col-md-2">
          <input type="text" class="form-control" placeholder="우편번호" v-model="this.$store.state.postCode" style="background-color: rgba(255,255,255,0.2);">
        </div>
        <div class="col-md">
          <input type="text" class="form-control" placeholder="주소" v-model="this.$store.state.address" style="background-color: rgba(255,255,255,0.2);">
        </div>
        <div ref="embed"></div>
      </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="단독다가구" v-model="this.$store.state.houseType">
          <label class="form-check-label" for="inlineRadio1">단독다가구</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="연립다세대" v-model="this.$store.state.houseType">
          <label class="form-check-label" for="inlineRadio2">연립다세대</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3" value="오피스텔" v-model="this.$store.state.houseType">
          <label class="form-check-label" for="inlineRadio3">오피스텔</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio4" value="아파트" v-model="this.$store.state.houseType">
          <label class="form-check-label" for="inlineRadio4">아파트</label>
        </div> 
        <div class="hstack gap-3" style="padding-top:10px">
          <label for="distRange" class="form-label col-md-3 mx-auto">통근/통학 시간 : {{ this.$store.state.userDist }} 분 이하</label>
          <input type="range" class="form-range" min="0" max = "" step="10" id="distRange" v-model="this.$store.state.userDist">
        </div>
        <div class="input-group mb-3">
          <label for="saleRange" class="form-label col-md-3 mx-auto">매매가 (만원)</label>
          <input type="text" class="form-control" placeholder="최소 금액" v-model="this.$store.state.minSale">
          <span class="input-group-text"> ~ </span>
          <input type="text" class="form-control" placeholder="최대 금액" v-model="this.$store.state.maxSale">
        </div>
        <div class="input-group mb-3">
          <label for="areaRange" class="form-label col-md-3 mx-auto">건물 면적 (평)</label>
          <input type="text" class="form-control" placeholder="최소 평수" v-model="this.$store.state.minArea">
          <span class="input-group-text"> ~ </span>
          <input type="text" class="form-control" placeholder="최대 평수" v-model="this.$store.state.maxArea">
        </div>
        <button type="button" class="btn btn-secondary btn-block"  @click="submitBtn">조회하기</button>
      </div>
    </div>
    <!-- <div class="b-example-divider b-example-vr"></div> -->
    
    <!-- <div>
        <ul v-if="Object.keys(this.$store.state.responseData).length > 0">
          <li v-for="(value, key) in this.$store.state.responseData" :key="key">{{ value }}</li>
        </ul>
        <p v-else>조회된 정보가 없습니다</p>
      </div> -->
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