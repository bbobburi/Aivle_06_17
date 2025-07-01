import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AppBar from '../components/AppBar';
import Footer from '../components/Footer';

const LoginPage = () => {
  const navigate = useNavigate();

  // 입력 필드 상태값 추가
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = () => {
    if (userId && password) {
      // 로그인 성공 시 이동 (예: 계정 페이지)
      navigate('/account');
    } else {
      alert('아이디와 비밀번호를 입력해주세요.');
    }
  };

  return (
    <div className="flex flex-col min-h-screen bg-white font-sans">
      <AppBar />
      <main className="flex-grow px-40 py-10 flex justify-center">
        <div className="w-full max-w-[512px]">
          <h2 className="text-[32px] font-bold text-[#111418] mb-6">Login</h2>

          <div className="mb-4">
            <label className="block mb-1 font-medium text-[#111418]">User ID</label>
            <input
              type="text"
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
              placeholder="Enter your user ID"
              className="w-full h-14 p-[15px] rounded-lg border border-[#dbe0e6]"
            />
          </div>

          <div className="mb-4">
            <label className="block mb-1 font-medium text-[#111418]">Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Enter your password"
              className="w-full h-14 p-[15px] rounded-lg border border-[#dbe0e6]"
            />
          </div>

          <div className="text-right">
            <button
              onClick={handleLogin}
              className="bg-[#0c7ff2] text-white px-4 py-2 rounded-lg font-bold"
            >
              Login
            </button>
          </div>

          <p className="text-sm mt-4 text-center">
            Don't have an account?{' '}
            <button
              onClick={() => navigate('/signup')}
              className="text-blue-600 hover:underline"
            >
              Sign up
            </button>
          </p>
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default LoginPage;
