import React, { useEffect } from 'react';
import { useDispatch } from "react-redux";
import { useNavigate, useSearchParams } from "react-router-dom";
import { login } from "../../redux/reducer/memberSlice";
import jwtDecode from "jwt-decode";

const LoginRedirect = () => {
  const [searchParams] = useSearchParams();
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    const token = searchParams.get('token');
    const certify_key = searchParams.get('code');
    let redirect_uri = searchParams.get('redirect_uri');

    try {
      if (token) {
        const claims = jwtDecode(token);

        dispatch(login({
          token: token,
          nickname: claims.nickname,
          profile: claims.profile,
        }));
      } else {
        if (certify_key) {
          redirect_uri = '/member/afterSignUp';
        }
      }
    } catch (e) {
      console.error('token not valid');
    }

    navigate(redirect_uri, certify_key ? {
      state: {
        certify_key: certify_key,
        redirect_uri: redirect_uri
      }
    } : null);
  }, []);

  return (
    <>
    </>
  );
};

export default LoginRedirect;