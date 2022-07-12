import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  token: '',
  nickname: '',
  profile: '',
};

export const memberSlice = createSlice({
  name: 'member',
  initialState,
  reducers: {
    login: (state, action) => {
      state.token = action.payload.token;
      state.nickname = action.payload.nickname;
      state.profile = action.payload.profile;
    },
    logout: () => initialState,

  }
});

export const { login, logout } = memberSlice.actions;

export default memberSlice.reducer;