import React from 'react';
import { useNavigate } from 'react-router-dom';
import AppBar from '../components/AppBar';
import Footer from '../components/Footer';

const MainPage = () => {
  const navigate = useNavigate();

  const handleNavigation = (label) => {
    switch (label) {
      case 'Author Management':
        navigate('/author');
        break;
      case 'Administrator System':
        navigate('/admin');
        break;
      case 'Library Platform':
        navigate('/library');
        break;
      case 'AI System':
        navigate('/ai');
        break;
      case 'Subscriber System':
        navigate('/subscriber');
        break;
      case 'Point System':
        navigate('/points');
        break;
      default:
        break;
    }
  };

  return (
    <div className="flex flex-col min-h-screen bg-white font-sans">
      <AppBar />
      <main className="flex-grow px-40 py-10 flex justify-center">
        <div className="max-w-[960px] w-full flex flex-col items-center gap-6">
          <div
            className="min-h-[480px] flex flex-col gap-6 bg-cover bg-center bg-no-repeat items-center justify-center p-4 rounded-lg text-white text-center"
            style={{
              backgroundImage:
                "linear-gradient(rgba(0,0,0,0.1), rgba(0,0,0,0.4)), url('https://lh3.googleusercontent.com/...')",
            }}
          >
            <h1 className="text-4xl font-black">Welcome to BookVerse</h1>
            <h2 className="text-base font-normal">
              Manage your e-books efficiently with our microservice architecture platform. Choose your role to get started.
            </h2>
            <button className="bg-[#0c7ff2] text-white font-bold px-5 py-3 rounded-lg">Explore Features</button>
          </div>

          <h2 className="text-[22px] font-bold text-[#111418] mt-10">Role-Specific Dashboards</h2>
          <div className="flex flex-col items-stretch gap-3 max-w-[480px] w-full">
            {['Author Management', 'Administrator System', 'Library Platform', 'AI System'].map((role) => (
              <button
                key={role}
                onClick={() => handleNavigation(role)}
                className="bg-[#0c7ff2] text-white font-bold h-10 px-4 rounded-lg"
              >
                {role}
              </button>
            ))}
          </div>

          <div className="flex flex-wrap gap-3 justify-center max-w-[480px] w-full">
            {['Subscriber System', 'Point System'].map((role) => (
              <button
                key={role}
                onClick={() => handleNavigation(role)}
                className="bg-[#0c7ff2] text-white font-bold h-10 px-4 rounded-lg flex-grow"
              >
                {role}
              </button>
            ))}
          </div>
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default MainPage;
