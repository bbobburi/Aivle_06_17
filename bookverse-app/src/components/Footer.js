import React from 'react';

const Footer = () => {
  return (
    <footer className="w-full text-center py-4 border-t border-[#f0f2f5] mt-4 text-[#60758a] text-sm">
      &copy; {new Date().getFullYear()} BookVerse. All rights reserved.
    </footer>
  );
};

export default Footer;
