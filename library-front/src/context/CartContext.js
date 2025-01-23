import React, { createContext, useState, useContext } from "react";

// Tworzenie kontekstu
const CartContext = createContext();

// Provider kontekstu, który opakuje aplikację
export const CartProvider = ({ children }) => {
  const [cartItems, setCartItems] = useState([]);

  // Funkcja do dodawania produktów do koszyka
  const addToCart = (book) => {
    setCartItems((prevItems) => [...prevItems, book]);
  };

  // Funkcja do usuwania produktów z koszyka
  const removeFromCart = (id) => {
    setCartItems((prevItems) => prevItems.filter((item) => item.id !== id));
  };

  return (
    <CartContext.Provider value={{ cartItems, addToCart, removeFromCart }}>
      {children}
    </CartContext.Provider>
  );
};

// Hook do używania kontekstu
export const useCart = () => {
  return useContext(CartContext);
};