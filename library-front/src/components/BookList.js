import React from "react"; 
import BookCard from "./BookCard";
import "./BookList.css";
import { useCart } from "../context/CartContext";

const BookList = ({ title, books }) => {
  const { addToCart } = useCart();

  return (
    <section className="booklist">
      <h2>{title}</h2>
      <div className="booklist-grid">
        {books.map((book) => (
          <div key={book.id} className="book-card">
            <BookCard title={book.title} img={book.img} />
            <p>Cena: {book.price} zł</p>
            <button className="btn-primary" onClick={() => addToCart(book)}>
              Zarezerwuj Teraz
            </button>
          </div>
        ))}
      </div>
    </section>
  );
};

export default BookList;