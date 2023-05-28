<template>
  <div class="background">
    <img alt="Vue logo" src="../assets/beaver.png" width="150" style="padding-top: 200px;">
    <h1 style="font-size: 70px; padding-top:40px; font-family:NanumSquareNeo; font-weight:700;"> 비버의 내 집 마련</h1>
    <div class="daummap">
      <div class ="row" style="padding-top: 20px;">
        <div class="col-sm-3"></div>
        <div class="col-sm">
          <h3 style="font-family: NanumSquareNeo; font-weight: 300; padding-bottom: 10px;">직장 또는 학교의 주소를 입력해주세요!</h3>
          <div class="input-group mb-3" style="height:50px">
            <span class="input-group-text">주소</span>
            <input type="text" class="form-control" style="background-color: rgba(255,255,255,0.2);">
            <button class="input-group-text" @click="showApi">검색</button>
          </div>
        </div>
        <div class="col-sm-3"></div>
      </div>
      <!-- <h1>우편번호: <span>{{ zip }}</span></h1>
      <h1>기본주소: <span>{{ addr1 }}</span></h1> -->
      <div ref="embed"></div>
      <!-- <button @click="showApi">주소API 호출</button> -->
    </div>
  </div>
  
</template>

<!-- <script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script> -->

<script>
export default {
  name: 'daumMap',
  data() {
    return {
      zip: '',
      addr1: '',
      addr2: ''
    }
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
            this.zip = data.zonecode; //5자리 새우편번호 사용
            this.addr1 = fullRoadAddr;
        }
      }).open()
    }
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
  overflow: hidden;
  /* background-image: radial-gradient( circle farthest-corner at 10% 20%,  rgba(248,213,214,1) 0%, rgba(243,242,229,1) 90% ); */
  background-image: radial-gradient( circle farthest-corner at 10% 20%,  rgba(226,240,254,1) 0%, rgba(255,247,228,1) 90% );
  /* background-image: linear-gradient( 174.2deg,  rgba(255,244,228,1) 7.1%, rgba(240,246,238,1) 67.4% ); */
}

.flex-container{
  width: 100%;
  height: 100vh;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
      -ms-flex-align: center;
          align-items: center;
  -webkit-box-pack: center;
      -ms-flex-pack: center;
          justify-content: center;
}
</style>
