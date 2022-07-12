import React from 'react';
import { useSelector } from "react-redux";

const Main = () => {
  const { token, nickname, profile } = useSelector((state) => state.member);

  return (
    <>
      <p>메인</p>
      <p>{token}</p>
      <p>{nickname}</p>
      <p><img src={profile} /></p>
    </>
  );
};

export default Main;