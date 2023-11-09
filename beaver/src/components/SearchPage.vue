<template>
  <header class="navbar sticky-top bg-dark flex-md-nowrap p-0 shadow" data-bs-theme="dark">
    <a class="navbar-brand col-md-3 col-lg-2 me-0 px-3 fs-6 text-white" href="/" style="font-family:NanumSquareNeo; font-weight:700;">&nbsp;&nbsp;서울 비버의 내 집 마련</a>
    <ul class="navbar-nav flex-row d-md-none">
      <li class="nav-item text-nowrap">
        <button class="nav-link px-3 text-white" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSearch" aria-controls="navbarSearch" aria-expanded="false" aria-label="Toggle search">
          <svg class="bi"><use xlink:href="#search"></use></svg>
        </button>
      </li>
      <li class="nav-item text-nowrap">
        <button class="nav-link px-3 text-white" type="button" data-bs-toggle="offcanvas" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
          <svg class="bi"><use xlink:href="#list"></use></svg>
        </button>
      </li>
    </ul>
    <div id="navbarSearch" class="navbar-search w-100 collapse">
      <input class="form-control w-100 rounded-0 border-0" type="text" placeholder="Search" aria-label="Search">
    </div>
  </header>
  <div class="d-flex flex-nowrap background">
    <div class="d-flex flex-column align-items-stretch flex-shrink-0 bg-body-tertiary" style="width: 380px;">
      <a href="/" class="d-flex align-items-center flex-shrink-0 p-3 link-body-emphasis text-decoration-none border-bottom">
        <img alt="logo" src="../assets/beaver.png" width="50" href="/">
        <span style="font-size: 20px; font-family:NanumSquareNeo; font-weight:700;">&nbsp;&nbsp;{{ this.$store.state.address }}</span>
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
      <div class="container-fluid" style="padding:20px;text-align: center;">
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
          <label for="distRange" class="form-label col-md-3 mx-auto">통근/통학 거리 : {{ this.$store.state.userDist }} km 이하</label>
          <input type="range" class="form-range" min="0" max = "40" step="1" id="distRange" v-model="this.$store.state.userDist">
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
        <button type="button" class="btn btn-secondary btn-block"  @click="fetchDataAndProcessData">조회하기</button>
      </div>
      
      <div class="b-example-divider b-example-vr"></div>
      <ul class="nav nav-fill nav-justified nav-underline justify-content-center">
        <li class="nav-item">
          <a class="nav-link" :class="{ 'active': selectedPage === 'avg' }" @click="this.selectedPage = 'avg'">행정동별 거래금액 평균</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">행정동별 거래 데이터</a>
          <ul class="dropdown-menu">
            <li v-for="item in regionData" :key="item.id">
              <a class="dropdown-item" href="#">{{item.행정동명}}</a>
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link" :class="{ 'active': selectedPage === 'all' }" @click="this.selectedPage = 'all'">전체 거래 데이터 확인하기</a>
        </li>
      </ul>
      <!-- 페이지 내용을 선택한 페이지에 따라 변경 -->
      <div v-if="this.selectedPage === 'avg'" style="margin: 20px 0 20px 20px;">
        <div>
          <div class="row">
            <div class="col-md-4" v-for="(regionData, regionName) in averagedData" :key="regionName">
              <div class="card mb-3">
                <div class="card-body">
                  <h5 class="card-title">{{ regionName }}</h5>
                  <p class="card-text">평균 거래금액: {{ regionData }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else-if="this.selectedPage === 'all'">
        <h2>전체 거래 데이터</h2>
        <div v-for="(regionData, regionName) in groupedData" :key="regionName">
            <h3>{{ regionName }}</h3>
            <ul>
                <li v-for="item in regionData" :key="item.id">
                    {{ item.행정동명 }} - {{ item.건물명 }} - {{ item.거래금액 }}
                </li>
            </ul>
        </div>
      </div>
      <div v-else>
        <h2>{{ this.selectedPage }}</h2>
      </div>
      
      <!-- <div>
        <ul v-if="Object.keys(this.$store.state.responseData).length > 0">
          <li v-for="(value, key) in this.$store.state.responseData" :key="key">{{ value }}</li>
        </ul>
        <p v-else>조회된 정보가 없습니다</p>
      </div> -->
  
    </div>
    

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
      selectedPage: 'avg',
      groupedData: {},
      averagedData: {}
    }
  },

  mounted() {
    // this.groupData();
    // this.calculateAverage();
  },
  
  computed: {
    

  },

  methods: {
    async submitBtn() { 
                  await this.$store.dispatch('fetchDataFromServer');

                  // this.$router.push('/search');
                  this.groupData();
                  this.calculateAverage();
                },
    
    async groupData() {
      const groupedData = {};

      for (const key in this.$store.state.responseData) {
        const item = this.$store.state.responseData[key];
        const groupKey = `${item.자치구명} ${item.행정동명}`;
        if (!groupedData[groupKey]) {groupedData[groupKey] = [];}
        groupedData[groupKey].push(item);
      };

      this.groupedData=groupedData;
    },

    async calculateAverage() {
      const averagedData = {}; // 객체로 변경

    for (const key in this.groupedData) {
      const group = this.groupedData[key];
      const sum = group.reduce((total, item) => total + item.거래금액, 0);
      const average = sum / group.length;
      const roundedAverage = parseFloat(average.toFixed(2)); // 반올림 후 숫자로 변환
      averagedData[key] = roundedAverage;
    }

    this.averagedData = averagedData;
    },

    async fetchDataAndProcessData() {
      // First, fetch data from the server
      await this.$store.dispatch('fetchDataFromServer');

      // After data is loaded, call groupData and calculateAverage
      this.groupData();
      this.calculateAverage();
    }


  },

  created() {
    // Call fetchDataAndProcessData when the page is created
    this.fetchDataAndProcessData();
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
  /* height: 100vh; */
  /* overflow: hidden; */
  /* background-image: radial-gradient( circle farthest-corner at 10% 20%,  rgba(248,213,214,1) 0%, rgba(243,242,229,1) 90% ); */
  background-image: radial-gradient( circle farthest-corner at 10% 20%,  rgba(226,240,254,1) 0%, rgba(255,247,228,1) 90% );
  /* background-image: linear-gradient( 174.2deg,  rgba(255,244,228,1) 7.1%, rgba(240,246,238,1) 67.4% ); */
}

</style>