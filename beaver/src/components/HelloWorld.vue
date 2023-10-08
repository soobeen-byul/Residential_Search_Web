<template>
  <div class="background">
    <div style="padding-top: 200px;text-align: center;"><img alt="logo" src="../assets/beaver.png" width="150"></div>
    <h1 style="font-size: 70px; padding-top:40px; font-family:NanumSquareNeo; font-weight:700;text-align: center;">서울 비버의 내 집 마련</h1>
    <div class ="row" style="padding-top: 20px;font-family: NanumSquareNeo; font-weight:500;padding-bottom: 10px;font-size: 20px;">
        <div class="col-md-2"></div>
        <div class="col-md">
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
          <div class="container-fluid" style="padding:15px;background-color: rgba(234,234,234,0.6);text-align: center;">
            <!-- <div class ="row mb-3"> -->
              <!-- <label for="houseRange" class="col-sm-2 col-form-label">건물유형</label> -->
              <!-- <div class="col-sm-10"> -->
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
              <!-- </div> -->
            <!-- </div> -->
            <!-- <div class ="row mb-3">
              <label for="constRange" class="col-sm-2 col-form-label">건축년도</label>
              <div class="col-sm-10">
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="checkbox" id="constRange" v-model="tfYear">
                  <label class="form-check-label" for="flexCheckDefault">&nbsp;무관</label>
                </div>
                <div class="form-check form-check-inline" style="padding-top:10px" v-if="tfYear==false">
                  <input type="text" class="form-control" placeholder="최소 금액" v-model="constYear">
                </div>
              </div>
            </div> -->
            <div class="hstack gap-3" style="padding-top:10px">
              <label for="distRange" class="form-label col-md-3 mx-auto">통근/통학 시간 : {{ this.$store.state.userDist }} 분 이하</label>
              <input type="range" class="form-range" min="0" max = "" step="10" id="distRange" v-model="this.$store.state.userDist">
            </div>
            <div class="input-group mb-3">
              <!-- <label for="saleRange" class="form-label col-md-3 mx-auto">매매가 : {{ currency(userSale) }} 만원 이하</label>
              <input type="range" class="form-range" min="1000" max="1000000" step="1000" id="saleRange" v-model="userSale"> -->
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
        <div class="col-md-2"></div>
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
  name: 'HelloWorld',

  data() {
    return {
    }
  },

  created() {
    this.constYear = this.nowYear -1; 
  },

  computed: {
            currency(){
              return(v)=>{
                return String(v).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
              }
              
            },
  },

  mounted() {
    this.load();
  },

  methods: {
    showApi() {
      new window.daum.Postcode({
        oncomplete: (data) => {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            let fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            let extraRoadAddr = ''; // 도로명 조합형 주소 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
              extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }
            // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
            if(fullRoadAddr !== ''){
                fullRoadAddr += extraRoadAddr;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            this.$store.state.postCode = data.zonecode; //5자리 새우편번호 사용
            this.$store.state.address = fullRoadAddr;
        }
      }).open()
    },

    load() {
      this.axios.get('/api/hello').then(res => { 
        console.log(res.data);
      });
    },
    // Axios를 사용하여 데이터를 Spring Boot로 전송 // 전송이 성공하면 Spring Boot에서 받은 데이터를 처리
    submitBtn() { this.$store.dispatch('fetchDataFromServer');
                  this.$router.push('/search');},

  //   handleResponse(responseData) {
  //     this.responseData = responseData;
  //     console.log(this.responseData);
  // }

   
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