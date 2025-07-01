import React from 'react';

const AuthorManagement = () => {
  return (
    <div className="flex flex-col min-h-screen bg-white font-sans">
      <header className="flex items-center justify-between border-b border-[#f0f2f5] px-10 py-3">
        <div className="flex items-center gap-4 text-[#111418]">
          <div className="size-4">
            <svg viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path fillRule="evenodd" clipRule="evenodd" d="M24 4H6V17.3333V30.6667H24V44H42V30.6667V17.3333H24V4Z" fill="currentColor" />
            </svg>
          </div>
          <h2 className="text-lg font-bold tracking-[-0.015em]">StoryCraft</h2>
        </div>
        <div className="flex gap-8 items-center">
          <div className="flex items-center gap-9">
            <a className="text-sm font-medium text-[#111418]" href="#">Home</a>
            <a className="text-sm font-medium text-[#111418]" href="#">Explore</a>
            <a className="text-sm font-medium text-[#111418]" href="#">My Library</a>
          </div>
          <button className="h-10 bg-[#f0f2f5] text-[#111418] px-2.5 rounded-lg flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" width="20px" height="20px" fill="currentColor" viewBox="0 0 256 256">
              <path d="M221.8,175.94C216.25,166.38,208,139.33,208,104a80,80,0,1,0-160,0c0,35.34-8.26,62.38-13.81,71.94A16,16,0,0,0,48,200H88.81a40,40,0,0,0,78.38,0H208a16,16,0,0,0,13.8-24.06Z" />
            </svg>
          </button>
          <div
            className="bg-cover bg-center rounded-full size-10"
            style={{ backgroundImage: "url('https://lh3.googleusercontent.com/aida-public/AB6AXuB9KpPNhTubd3MUFpfbhL_IEqQhMIMr3pAdEwxqJW5hY5litP4C8snr1qVSajkambcFHsNnnj5o5cP7jtS9BCajsFNZGpdKkjz5omlrkIf7ve9IBTBqvGpxeOZrcxIYg4qi6aWTZNgYlqTvS1GlJQlge4ZWdxRFw99p35vhRq7tzGSdxz294OBBOQvXGMrBT2H0jD3yrDYDCiSXH8lhdTg9pBwmoJzquycsX3HONUwWcGwX0ar6mwCQoLySoKL1pRpUIKFlHxMQPOYf')" }}
          ></div>
        </div>
      </header>

      <main className="px-40 py-5 flex justify-center">
        <div className="max-w-[960px] w-full">
          <h1 className="text-[32px] font-bold mb-4 px-4">Author Management</h1>

          <section className="mb-6">
            <h2 className="text-lg font-bold px-4 pb-2">Author Registration</h2>
            <div className="flex flex-wrap max-w-[480px] gap-4 px-4">
              <label className="flex flex-col flex-1 min-w-40">
                <p className="pb-2 text-base font-medium">Name</p>
                <input placeholder="Enter your name" className="form-input bg-[#f0f2f5] h-14 rounded-lg p-4 placeholder-[#60758a]" />
              </label>
              <label className="flex flex-col flex-1 min-w-40">
                <p className="pb-2 text-base font-medium">Introduction</p>
                <textarea placeholder="Brief intro" className="form-input bg-[#f0f2f5] min-h-36 rounded-lg p-4 placeholder-[#60758a]" />
              </label>
              <label className="flex flex-col flex-1 min-w-40">
                <p className="pb-2 text-base font-medium">User ID</p>
                <input placeholder="Enter user ID" className="form-input bg-[#f0f2f5] h-14 rounded-lg p-4 placeholder-[#60758a]" />
              </label>
            </div>
          </section>

          <section className="mb-6">
            <h2 className="text-lg font-bold px-4 pb-2">Content Creation</h2>
            <div className="flex flex-wrap max-w-[480px] gap-4 px-4">
              <label className="flex flex-col flex-1 min-w-40">
                <p className="pb-2 text-base font-medium">E-book Title</p>
                <input placeholder="E-book Title" className="form-input bg-[#f0f2f5] h-14 rounded-lg p-4 placeholder-[#60758a]" />
              </label>
              <label className="flex flex-col flex-1 min-w-40">
                <p className="pb-2 text-base font-medium">Content</p>
                <textarea placeholder="Write content" className="form-input bg-[#f0f2f5] min-h-36 rounded-lg p-4 placeholder-[#60758a]" />
              </label>
              <label className="flex flex-col flex-1 min-w-40">
                <p className="pb-2 text-base font-medium">Summary</p>
                <textarea placeholder="Summary" className="form-input bg-[#f0f2f5] min-h-36 rounded-lg p-4 placeholder-[#60758a]" />
              </label>
            </div>
          </section>

          <section className="mb-6">
            <h2 className="text-lg font-bold px-4 pb-2">Publishing Requests</h2>
            <div className="flex gap-3 px-4 py-2">
              <button className="h-10 px-4 rounded-lg bg-[#0c7ff2] text-white font-bold">Submit Request</button>
              <button className="h-10 px-4 rounded-lg bg-[#f0f2f5] text-[#111418] font-bold">Cancel Request</button>
            </div>
            <div className="flex px-4 pt-2">
              <button className="h-10 px-4 rounded-lg bg-[#f0f2f5] text-[#111418] font-bold">Request E-book Privacy</button>
            </div>
          </section>
        </div>
      </main>
    </div>
  );
};

export default AuthorManagement;