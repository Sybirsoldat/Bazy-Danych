import React, { useState } from "react";
import "./CookiePopup.css"; // Upewnij się, że styl dla popupu jest osobny

const CookiePopup = () => {
  const [isVisible, setIsVisible] = useState(true); // Stan widoczności popupu

  const handleAccept = () => {
    setIsVisible(false); // Ukryj popup po kliknięciu
    // Opcjonalnie, zapisz zgodę w localStorage lub cookie
    localStorage.setItem("cookiesAccepted", "true");
  };

  return (
    isVisible && (
      <div className="cookie-popup">
        <p>
          Strona korzysta z plików cookies (ciasteczka), potwierdź że zgadzasz
          się klikając w ten przycisk.
        </p>
        <button onClick={handleAccept} className="cookie-button">
          Zgadzam się
        </button>
      </div>
    )
  );
};

export default CookiePopup;