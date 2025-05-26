import React, { useState } from 'react';

const TrackingSearch = () => {
  const [trackingNumber, setTrackingNumber] = useState('');
  const [orderData, setOrderData] = useState(null);
  const [error, setError] = useState('');

  const handleSearch = async () => {
    setError('');
    setOrderData(null);

    try {
      const res = await fetch(`http://localhost:8081/api/shipping/tracking/${trackingNumber}`);
      if (!res.ok) throw new Error('Shipment not found');
      const data = await res.json();
      console.log(data);
      setOrderData(data);
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="order-search">
      <h2>Search Shipment</h2>
      <div className="input-group">
      <input
        type="text"
        value={trackingNumber}
        onChange={(e) => setTrackingNumber(e.target.value)}
        placeholder="Enter Order Number (e.g., TRN-1748069857274)"
      />
      <button onClick={handleSearch}>Search Shipment</button>
      </div>

      {error && <p className="error">{error}</p>}

      {orderData && (
        <div className="order-details">
          <h2>Shipment Details</h2>
          <p><strong>Order Number:</strong> {orderData.orderNumber}</p>
          <p><strong>Status:</strong> {orderData.orderStatus}</p>
          <p><strong>Tracking Number:</strong> {orderData.trackingNumber}</p>
          <p><strong>Order Date and Time:</strong> {new Date(orderData.orderDate).toLocaleString()}</p>
          <p><strong>Customer Address:</strong> {orderData.address}</p>
          <p><strong>Courier:</strong> {orderData.courier}</p>

          <h4>Items</h4>
          <table>
            <thead>
              <tr>
                <th>Product Number</th>
                <th>Name</th>
                <th>Qty</th>
                <th>Unit Price</th>
                <th>Total</th>
              </tr>
            </thead>
            <tbody>
              {orderData.items.map((item, idx) => (
                <tr key={idx}>
                  <td>{item.productNumber}</td>
                  <td>{item.productName}</td>
                  <td>{item.quantity}</td>
                  <td>${item.unitPrice.toFixed(2)}</td>
                  <td>${item.totalPrice.toFixed(2)}</td>
                </tr>
              ))}

              {orderData?.items && (
                <tr className="total-row">
                  <td colSpan="4" style={{ textAlign: 'right', fontWeight: 'bold' }}>Total Amount:</td>
                  <td style={{ fontWeight: 'bold' }}>
                    ${orderData.items.reduce((sum, item) => sum + (item.totalPrice || 0), 0).toFixed(2)}
                  </td>
                </tr>
              )}

            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default TrackingSearch;
