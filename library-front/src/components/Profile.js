import React from 'react';
import './Profile.css';  // Stylizacja strony profilu

const Profile = () => {
  return (
    <div className="profile-container">
      <h1>Profil Użytkownika</h1>
      <div className="profile-info">
        <p><strong>Imię:</strong> Jan</p>
        <p><strong>Nazwisko:</strong> Kowalski</p>
        <p><strong>Email:</strong> jan.kowalski@example.com</p>
        <p><strong>Telefon:</strong> 123-456-789</p>
      </div>
    </div>
  );
};

export default Profile;