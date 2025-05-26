import React, { useState } from 'react';

const ShipmentRecords = () => {
  const [orderStatus, setOrderStatus] = useState('PROCESSING');
  const [shipments, setShipments] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [expandedOrder, setExpandedOrder] = useState(null);
  const [editMode, setEditMode] = useState(null);
  const [editData, setEditData] = useState({ orderStatus: '', courier: '' });

  const fetchShipments = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await fetch(`http://localhost:8081/api/shipping/status/${orderStatus}`);
      if (!response.ok) {
        throw new Error('Failed to fetch shipments');
      }
      const data = await response.json();
      setShipments(data);
    } catch (err) {
      setError(err.message);
      setShipments([]);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = (e) => {
    e.preventDefault();
    fetchShipments();
  };

  const toggleItems = (orderNumber) => {
    setExpandedOrder(expandedOrder === orderNumber ? null : orderNumber);
  };

  const formatDate = (dateString) => {
    if (!dateString) return 'Not set';
    return new Date(dateString).toISOString().slice(0, 10);
  };

  const handleEdit = (shipment) => {
    setEditMode(shipment.trackingNumber);
    setEditData({
      orderStatus: shipment.orderStatus,
      courier: shipment.courier
    });
  };

  const handleSave = async (trackingNumber) => {
    try {
      const response = await fetch(`http://localhost:8081/api/shipping/${trackingNumber}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(editData),
      });

      if (!response.ok) {
        throw new Error('Failed to update shipment');
      }

      setEditMode(null);
      fetchShipments();
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <div className="shipment-records">
      <h2>Shipment Records</h2>

      <form onSubmit={handleSearch} className="search-form">
        <div className="form-group">
          <label htmlFor="orderStatus">Order Status:</label>
          <select
            id="orderStatus"
            value={orderStatus}
            onChange={(e) => setOrderStatus(e.target.value)}
          >
            <option value="PROCESSING">PROCESSING</option>
            <option value="SHIPPED">SHIPPED</option>
            <option value="DELIVERED">DELIVERED</option>
            <option value="CANCELLED">CANCELLED</option>
          </select>
        </div>
        <button type="submit" disabled={loading}>
          {loading ? 'Searching...' : 'Search'}
        </button>
      </form>

      {error && <div className="error">{error}</div>}

      {shipments.length > 0 ? (
        <div className="shipments-table">
          <table>
            <thead>
              <tr>
                <th>Order Number</th>
                <th>Tracking Number</th>
                <th>Status</th>
                <th>Order Date</th>
                <th>Courier</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {shipments.map((shipment) => (
                <React.Fragment key={shipment.trackingNumber}>
                  <tr>
                    <td>{shipment.orderNumber}</td>
                    <td>{shipment.trackingNumber}</td>
                    <td>
                      {editMode === shipment.trackingNumber ? (
                        <select
                          value={editData.orderStatus}
                          onChange={(e) => setEditData({ ...editData, orderStatus: e.target.value })}
                        >
                          <option value="PROCESSING">PROCESSING</option>
                          <option value="SHIPPED">SHIPPED</option>
                          <option value="DELIVERED">DELIVERED</option>
                          <option value="CANCELLED">CANCELLED</option>
                        </select>
                      ) : (
                        shipment.orderStatus
                      )}
                    </td>
                    <td>{formatDate(shipment.orderDate)}</td>
                    <td>
                      {editMode === shipment.trackingNumber ? (
                        <select
                          value={editData.courier}
                          onChange={(e) => setEditData({ ...editData, courier: e.target.value })}
                        >
                          <option value="FedEx">FedEx</option>
                          <option value="J&T">J&T</option>
                          <option value="Lalamove">Lalamove</option>
                          <option value="Grab">Grab</option>
                        </select>
                      ) : (
                        shipment.courier
                      )}
                    </td>
                    <td>
                      {editMode === shipment.trackingNumber ? (
                        <button onClick={() => handleSave(shipment.trackingNumber)}>Save</button>
                      ) : (
                        <button onClick={() => handleEdit(shipment)}>Edit</button>
                      )}
                      <button onClick={() => toggleItems(shipment.orderNumber)}>
                        {expandedOrder === shipment.orderNumber ? 'Hide' : 'Show'} Items
                      </button>
                    </td>
                  </tr>
                  {expandedOrder === shipment.orderNumber && (
                    <tr>
                      <td colSpan="6">
                        <table className="items-table">
                          <thead>
                            <tr>
                              <th>Product #</th>
                              <th>Name</th>
                              <th>Qty</th>
                              <th>Unit Price</th>
                              <th>Total</th>
                            </tr>
                          </thead>
                          <tbody>
                            {shipment.items.map((item, index) => (
                              <tr key={index}>
                                <td>{item.productNumber}</td>
                                <td>{item.productName}</td>
                                <td>{item.quantity}</td>
                                <td>${item.unitPrice.toFixed(2)}</td>
                                <td>${item.totalPrice.toFixed(2)}</td>
                              </tr>
                            ))}
                          </tbody>
                        </table>
                      </td>
                    </tr>
                  )}
                </React.Fragment>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        !loading && <div className="no-results">No shipments found with status: {orderStatus}</div>
      )}
    </div>
  );
};

export default ShipmentRecords;
