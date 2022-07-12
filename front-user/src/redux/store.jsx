import { persistReducer } from "redux-persist";
import { combineReducers, configureStore } from "@reduxjs/toolkit";
import memberReducer from "./reducer/memberSlice";
import sessionStorage from "redux-persist/es/storage/session";

const reducer = combineReducers({
  member: memberReducer
});

const persistConfig = {
  key: 'root',
  storage: sessionStorage,
  whitelist: ['member'],
};

const persistedReducer = persistReducer(persistConfig, reducer);

export const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoreActions: true,
      }
    }),
});