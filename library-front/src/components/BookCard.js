import React from "react";
import "./BookCard.css";

const BookCard = ({ title, img }) => {
  if (!title || !img) {
    console.error("Brak danych: ", { title, img });
    return null;
  }

  return (
    <div className="book-card">
      <img src={img} alt={title} className="book-image" />
      <h3>{title}</h3>
    </div>
  );
};

export default BookCard;