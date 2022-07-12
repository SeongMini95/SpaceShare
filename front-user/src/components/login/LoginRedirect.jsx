import React, { useEffect } from 'react';
import { useDispatch } from "react-redux";
import { useNavigate, useSearchParams } from "react-router-dom";
import { login } from "../../redux/reducer/memberSlice";
import jwtDecode from "jwt-decode";
import { DEFAULT_PROFILE_URI } from "../../globalVar";

const LoginRedirect = () => {
  const [searchParams] = useSearchParams();
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    const token = searchParams.get('token');
    const certifyKey = searchParams.get('code');
    const redirectUri = searchParams.get('redirect_uri');

    let returnUri = redirectUri;

    try {
      if (token) {
        const claims = jwtDecode(token);

        dispatch(login({
          token: token,
          nickname: claims.nickname,
          profile: claims.profile ? claims.profile : DEFAULT_PROFILE_URI,
        }));
      } else {
        if (certifyKey) {
          returnUri = '/member/afterSignUp';
        }
      }
    } catch (e) {
      console.error('token not valid');
    }

    navigate(returnUri, certifyKey ? {
      state: {
        certifyKey: certifyKey,
        redirectUri: redirectUri
      }
    } : {});
  }, []);
};

export default LoginRedirect;