import React, { useState } from 'react';
import { useLocation, useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import axios from "axios";
import { login } from "../../redux/reducer/memberSlice";
import jwtDecode from "jwt-decode";
import { DEFAULT_PROFILE_URI } from "../../globalVar";

const AfterSignUp = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { certifyKey, redirectUri } = useLocation().state;
  const [nickname, setNickname] = useState('');

  const save = async () => {
    try {
      const response = await axios.post('/api/member/afterSignUp', {
        code: certifyKey,
        nickname: nickname,
      });

      const { result, token } = response.data;
      if (result) {
        const claims = jwtDecode(token);

        dispatch(login({
          token: token,
          nickname: claims.nickname,
          profile: claims.profile ? claims.profile : DEFAULT_PROFILE_URI,
        }));

        navigate(redirectUri, { replace: true });
      }
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <>
      <input value={certifyKey} onChange={(e) => e.preventDefault()} />
      <input value={nickname} onChange={(e) => setNickname(e.target.value)} />
      <button onClick={save}>저장하기</button>
    </>
  );
};

export default AfterSignUp;