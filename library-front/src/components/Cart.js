import React from "react";
import { useCart } from "../context/CartContext";
import "./Cart.css";

const Cart = () => {
  const { cartItems, removeFromCart } = useCart();

  const totalPrice = cartItems.reduce((total, item) => total + item.price, 0);

  return (
    <div className="cart-container">
      <h2>Twój Koszyk</h2>
      {cartItems.length === 0 ? (
        <p>Koszyk jest pusty.</p>
      ) : (
        <ul>
          {cartItems.map((item) => (
            <li key={item.id}>
              <span>{item.title} - {item.price.toFixed(2)} zł</span>
              <button onClick={() => removeFromCart(item.id)}>Usuń</button>
            </li>
          ))}
        </ul>
      )}
      <h3>Łączna cena: {totalPrice.toFixed(2)} zł</h3>
      <button className="checkout-button">Przejdź do kasy</button>
    </div>
  );
};

export default Cart;