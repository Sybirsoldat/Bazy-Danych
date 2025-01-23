import React from "react";
import "./Navbar.css";

const Navbar = () => {
  return (
    <nav className="navbar">
      <span className="navbar-logo">BWR</span>
      <ul className="navbar-menu">
        <li>Zarezerwuj salę</li>
        <li>Wypożycz</li>
        <li>Zaloguj się</li>
        <li>Zarejestruj się</li>
        <li>Profil Użytkownika</li>
        <li>O nas</li>
      </ul>
      <button className="navbar-cart">Koszyk (3)</button>
    </nav>
  );
};

export default Navbar;