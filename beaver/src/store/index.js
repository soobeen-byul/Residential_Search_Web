import { createStore } from 'vuex';
import axios from 'axios';

export default createStore({
  state() {
    return {
      nowYear: new Date().getFullYear(),
      postCode: '',
      address: '',
      houseType: '',
      tfYear: true,
      constYear: 0,
      userDist: 0,
      minSale: 1000,
      maxSale: 1000000,
      minArea: 0,
      maxArea: 30,
      responseData: {},
    };
  },
  mutations: {
    setResponseData(state, data) {
      state.responseData = data;
    },
  },
  actions: {
    async fetchDataFromServer({ commit, state}) {
      const data = {
        postCode: state.postCode,
        address: state.address,
        houseType: state.houseType,
        userDist: state.userDist,
        minSale: state.minSale,
        maxSale: state.maxSale,
        minArea: state.minArea,
        maxArea: state.maxArea,
      };

      console.log(data);

      try {
        const response = await axios.post('/api/search', data);
        // console.log(response)
        commit('setResponseData', response.data);
      } catch (error) {
        console.error('Error:', error);
      }
    },
  },
  getters: {
  },
  modules: {
  },
});
