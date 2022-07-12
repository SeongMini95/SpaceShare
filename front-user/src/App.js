import './css/App.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Main from "./components/Main";
import Page404 from "./components/error/Page404";
import Login from "./components/login/Login";
import LoginRedirect from "./components/login/LoginRedirect";
import AfterSignUp from "./components/member/AfterSignUp";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route index path="/" element={<Main />} />

          <Route path="login" element={<Login />} />
          <Route path="login/redirect" element={<LoginRedirect />} />

          <Route path="member/afterSignUp" element={<AfterSignUp />} />

          <Route path="*" element={<Page404 />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
