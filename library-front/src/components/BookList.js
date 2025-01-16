import React from "react";
import BookCard from "./BookCard";
import "./BookList.css";

const BookList = ({ title, books }) => (
  <section className="booklist">
    <h2>{title}</h2>
    <div className="booklist-grid">
      {books.map((book) => (
        <BookCard key={book.id} title={book.title} img={book.img} />
      ))}
    </div>
  </section>
);

export default BookList;