import React from 'react';
import { useNavigate } from 'react-router-dom';

const AppBar = () => {
  const navigate = useNavigate();

  return (
    <header className="flex items-center justify-between px-10 py-4 border-b border-gray-200">
      <div className="flex items-center gap-4 text-[#111418]">
        <div className="w-6 h-6">
          {/* 로고 SVG */}
          <svg viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path
              d="M36.7273 44C33.9891 44 31.6043 39.8386 30.3636 33.69C29.123 39.8386 26.7382 44 24 44C21.2618 44 18.877 39.8386 17.6364 33.69C16.3957 39.8386 14.0109 44 11.2727 44C7.25611 44 4 35.0457 4 24C4 12.9543 7.25611 4 11.2727 4C14.0109 4 16.3957 8.16144 17.6364 14.31C18.877 8.16144 21.2618 4 24 4C26.7382 4 29.123 8.16144 30.3636 14.31C31.6043 8.16144 33.9891 4 36.7273 4C40.7439 4 44 12.9543 44 24C44 35.0457 40.7439 44 36.7273 44Z"
              fill="currentColor"
            />
          </svg>
        </div>
        <h2 className="text-lg font-bold">BookVerse</h2>
      </div>

      <div className="flex gap-4">
        <button
          onClick={() => navigate('/login')}
          className="text-sm font-medium text-[#111418] hover:underline"
        >
          Login
        </button>
        <button
          onClick={() => navigate('/signup')}
          className="text-sm font-medium text-[#111418] hover:underline"
        >
          Signup
        </button>
      </div>
    </header>
  );
};

export default AppBar;
