import React, { useState, useEffect } from 'react';

const ShipmentDetails = () => {
  const [shipments, setShipments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchShipments = async () => {
      try {
        const response = await fetch('http://localhost:8081/api/shipments');
        if (!response.ok) {
          throw new Error('Failed to fetch shipments');
        }
        const data = await response.json();
        setShipments(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchShipments();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="shipment-details">
      <h2>Shipment Details</h2>
      <table>
        <thead>
          <tr>
            <th>Order Number</th>
            <th>Status</th>
            <th>Tracking Number</th>
            <th>Customer</th>
            <th>Address</th>
          </tr>
        </thead>
        <tbody>
          {shipments.map((shipment) => (
            <tr key={shipment.orderNumber}>
              <td>{shipment.orderNumber}</td>
              <td>{shipment.orderStatus}</td>
              <td>{shipment.trackingNumber}</td>
              <td>{shipment.customerName}</td>
              <td>{shipment.address}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ShipmentDetails;