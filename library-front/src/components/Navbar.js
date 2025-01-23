import React from "react";
import { Link } from "react-router-dom";
import { useCart } from "../context/CartContext"; // Upewnij się, że ścieżka jest poprawna
import "./Navbar.css";

const Navbar = () => {
  const { cartItems } = useCart(); // Pobranie cartItems z kontekstu

  return (
    <nav className="navbar">
      <Link to="/" className="navbar-logo">BWR</Link>
      <ul className="navbar-menu">
        <li><Link to="/">Wypożycz</Link></li>
        <li><Link to="/login">Zaloguj się</Link></li>
        <li><Link to="/register">Zarejestruj się</Link></li>
        <li><Link to="/profile">Profil</Link></li>
        <li><Link to="/about">O nas</Link></li>
      </ul>
      <Link to="/cart">
        <button className="navbar-cart">Koszyk ({cartItems.length})</button>
      </Link>
    </nav>
  );
};

export default Navbar;