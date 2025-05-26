import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import './App.css';
import OrderSearch from './components/OrderNumberSearch';
import ProductStore from './components/Order';
import AdminLogin from './components/AdminLogin';
import ShipmentDetails from './components/ShipmentDetails';

function App() {
  const [isAdmin, setIsAdmin] = useState<boolean>(false);

  return (
    <Router>
      <div className="app-container">
        <nav className="nav-menu">
          <ul>
            <li><Link to="/">Order Search</Link></li>
            <li><Link to="/shipping">Shipping</Link></li>
            {!isAdmin && <li><Link to="/admin">Admin Login</Link></li>}
            {isAdmin && <li><Link to="/shipment-details">Shipment Details</Link></li>}
          </ul>
        </nav>

        <Routes>
          <Route path="/" element={<OrderSearch />} />
          <Route path="/shipping" element={<ProductStore />} />
          <Route 
            path="/admin" 
            element={<AdminLogin setIsAdmin={setIsAdmin} />} 
          />
          <Route 
            path="/shipment-details" 
            element={isAdmin ? <ShipmentDetails /> : <AdminLogin setIsAdmin={setIsAdmin} />} 
          />
        </Routes>
      </div>
    </Router>
  );
}

export default App;