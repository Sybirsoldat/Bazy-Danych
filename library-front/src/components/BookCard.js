import React from "react";
import "./BookCard.css";

const BookCard = ({ title, img }) => (
  <div className="book-card">
    <img src={img} alt={title} className="book-image" />
    <button className="book-button">Zarezerwuj Teraz</button>
  </div>
);

export default BookCard;