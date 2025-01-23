import React from "react";
import ReactDOM from "react-dom/client"; // Ważne: użyj /client dla React 18+
import App from "./App";
import { CartProvider } from "./context/CartContext";
import reportWebVitals from "./reportWebVitals";

// Tworzenie root elementu zgodnie z React 18+
const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <React.StrictMode>
    <CartProvider>
      <App />
    </CartProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
