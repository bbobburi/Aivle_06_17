import React from 'react';
import AppBar from '../components/AppBar';
import Footer from '../components/Footer';

const AccountPage = () => {
  return (
    <div className="flex flex-col min-h-screen bg-white font-sans">
      <AppBar />
      <main className="flex-grow px-40 py-10 flex justify-center">
        <div className="w-full max-w-[512px]">
          <h2 className="text-[32px] font-bold text-[#111418] mb-6">Account Management</h2>

          <section>
            <h3 className="text-lg font-bold text-[#111418] mb-2">Registration</h3>
            <div className="mb-4">
              <label className="block mb-1 font-medium text-[#111418]">User ID</label>
              <input type="text" placeholder="Enter your user ID" className="w-full h-14 p-[15px] rounded-lg border border-[#dbe0e6] text-[#111418] placeholder-[#60758a]" />
            </div>
            <div className="mb-4">
              <label className="block mb-1 font-medium text-[#111418]">Email</label>
              <input type="email" placeholder="Enter your email" className="w-full h-14 p-[15px] rounded-lg border border-[#dbe0e6] text-[#111418] placeholder-[#60758a]" />
            </div>
            <div className="mb-4">
              <label className="block mb-1 font-medium text-[#111418]">Password</label>
              <input type="password" placeholder="Enter your password" className="w-full h-14 p-[15px] rounded-lg border border-[#dbe0e6] text-[#111418] placeholder-[#60758a]" />
            </div>
            <div className="text-right mb-8">
              <button className="bg-[#0c7ff2] text-white font-bold px-4 py-2 rounded-lg">Register</button>
            </div>
          </section>

          <section>
            <h3 className="text-lg font-bold text-[#111418] mb-2">Subscription</h3>
            <div className="flex gap-3 mb-6">
              <button className="bg-[#f0f2f5] text-[#111418] font-bold px-4 py-2 rounded-lg">Subscribe</button>
              <button className="bg-[#f0f2f5] text-[#111418] font-bold px-4 py-2 rounded-lg">Cancel Subscription</button>
            </div>
          </section>

          <section>
            <h3 className="text-lg font-bold text-[#111418] mb-2">Membership</h3>
            <div className="mb-6">
              <button className="bg-[#f0f2f5] text-[#111418] font-bold px-4 py-2 rounded-lg">Apply for Premium Membership</button>
            </div>
          </section>

          <section>
            <h3 className="text-lg font-bold text-[#111418] mb-2">Subscription Status</h3>
            <p className="text-[#111418]">Your current subscription status: Active</p>
          </section>

          <section className="mt-6">
            <h3 className="text-lg font-bold text-[#111418] mb-2">View Requests</h3>
            <p className="text-[#111418]">You have 3 pending view requests.</p>
          </section>
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default AccountPage;
