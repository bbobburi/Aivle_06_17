import React from 'react';
import AppBar from '../components/AppBar';
import Footer from '../components/Footer';

const SignupPage = () => {
  return (
    <div className="flex flex-col min-h-screen bg-white font-sans">
      <AppBar />
      <main className="flex-grow px-40 py-10 flex justify-center">
        <div className="w-full max-w-[512px]">
          <h2 className="text-[32px] font-bold text-[#111418] mb-6">Signup</h2>
          <div className="mb-4">
            <label className="block mb-1 font-medium text-[#111418]">User ID</label>
            <input type="text" placeholder="Choose a user ID" className="w-full h-14 p-[15px] border rounded-lg" />
          </div>
          <div className="mb-4">
            <label className="block mb-1 font-medium text-[#111418]">Email</label>
            <input type="email" placeholder="Enter your email" className="w-full h-14 p-[15px] border rounded-lg" />
          </div>
          <div className="mb-4">
            <label className="block mb-1 font-medium text-[#111418]">Password</label>
            <input type="password" placeholder="Create a password" className="w-full h-14 p-[15px] border rounded-lg" />
          </div>
          <div className="text-right">
            <button className="bg-[#0c7ff2] text-white px-4 py-2 rounded-lg font-bold">Signup</button>
          </div>
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default SignupPage;
