import React from "react";
import "./Footer.css";

const Footer = ({ contactInfo }) => {
  return (
    <footer className="footer">
      <div className="footer-content">
        <p>
          Kontakt: <a href={`mailto:${contactInfo.email}`}>{contactInfo.email}</a>
        </p>
        <p>Telefon: {contactInfo.phone}</p>
        <p>Adres: {contactInfo.address}</p>
      </div>
      <p className="footer-copyright">
        &copy; {new Date().getFullYear()} BWR. Wszelkie prawa zastrzeżone.
      </p>
    </footer>
  );
};

export default Footer;