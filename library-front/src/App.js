import React from "react";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import BookList from "./components/BookList";
import CookiePopup from "./components/CookiePopup";
import About from "./components/About";
import Login from "./components/Login";
import Register from "./components/Register";
import Cart from "./components/Cart";
import Profile from "./components/Profile";
import image1 from "./images/image1.jpg";
import image2 from "./images/image2.jpg";
import image3 from "./images/image3.jpg";
import image4 from "./images/image4.jpg";
import image5 from "./images/image5.jpg";
import image6 from "./images/image6.jpg";
import image7 from "./images/image7.jpg";
import image8 from "./images/image8.jpg";

const App = () => {
  const booksSelected = [
    { id: 1, title: "Nie za darmo", img: image1 },
    { id: 2, title: "Future", img: image2 },
    { id: 3, title: "Duna", img: image3 },
    { id: 4, title: "Cyberpunk: Bez Przypadku", img: image4 },
    { id: 5, title: "Haunting Adeline", img: image5 },
  ];

  const booksPopular = [
    { id: 6, title: "Nie za darmo", img: image1 },
    { id: 7, title: "Future", img: image2 },
    { id: 8, title: "Shallow River", img: image6 },
    { id: 9, title: "Haunting Adeline", img: image7 },
    { id: 10, title: "Satan's Affaire", img: image8 },
  ];

  const contactInfo = {
    email: "kontakt@bwr.com",
    phone: "+48 123 456 789",
    address: "ul. Przykladowa 123, 00-000 Warszawa",
  };


  return (
    <Router>
      <Navbar />
      <Routes>
        <Route
          path="/"
          element={
            <main>
              <BookList title="Wybrane dla Ciebie" books={booksSelected} />
              <BookList title="Popularne" books={booksPopular} />
            </main>
          }
        />
        <Route path="/profile" element={<Profile />} />
        <Route path="/about" element={<About />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/cart" element={<Cart />} />
      </Routes>
      <Footer contactInfo={contactInfo} />
      <CookiePopup />
    </Router>
  );
};

export default App;