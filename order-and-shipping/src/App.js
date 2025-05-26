import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import SearchOrderNumber from "./components/SearchOrderNumber";
import ProductStore from "./components/Order";
import ShipmentRecords from './components/ShipmentRecords';
import SearchShipment from "./components/SearchShipment"

function MainPage() {
  return (
    <div className="main-page">
      <SearchOrderNumber />
      <ProductStore />
    </div>
  );
}

function App() {
  return (
    <Router>
      <div className="app-container">
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/search_shipments" element={<SearchShipment />} />
          <Route path="/shipments" element={<ShipmentRecords />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;