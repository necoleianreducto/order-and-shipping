import React, { useState } from 'react';

const OrderSearch = () => {
  const [orderNumber, setOrderNumber] = useState('');
  const [orderData, setOrderData] = useState(null);
  const [error, setError] = useState('');

  const handleSearch = async () => {
    setError('');
    setOrderData(null);

    try {
      const res = await fetch(`http://localhost:8080/api/orders/${orderNumber}`);
      if (!res.ok) throw new Error('Order not found');
      const data = await res.json();
      console.log(data); // Debug response
      setOrderData(data);
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="order-search">
      <h2>Search Order</h2>
      <div className="input-group">
      <input
        type="text"
        value={orderNumber}
        onChange={(e) => setOrderNumber(e.target.value)}
        placeholder="Enter Order Number (e.g., OR-1748069857274)"
      />
      <button onClick={handleSearch}>Search Order</button>
      </div>

      {error && <p className="error">{error}</p>}

      {orderData && (
        <div className="order-details">
          <h3>Order Info</h3>
          <p><strong>Customer:</strong> {orderData.customerName}</p>
          <p><strong>Order Number:</strong> {orderData.orderNumber}</p>
          <p><strong>Status:</strong> {orderData.orderStatus}</p>
          <p><strong>Tracking Number:</strong> {orderData.trackingNumber}</p>
          <p><strong>Order Date:</strong> {new Date(orderData.orderDate).toLocaleString()}</p>

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

export default OrderSearch;
