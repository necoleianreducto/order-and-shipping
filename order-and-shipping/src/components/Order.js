import React, { useState } from 'react';

const ProductStore = () => {
  // Sample product data
  const [products] = useState([
    { productNumber: 'PN-0001', productName: 'Product 1', unitPrice: 10 },
    { productNumber: 'PN-0002', productName: 'Product 2', unitPrice: 10 },
    { productNumber: 'PN-0003', productName: 'Product 3', unitPrice: 15 },
    { productNumber: 'PN-0004', productName: 'Product 4', unitPrice: 20 },
    { productNumber: 'PN-0005', productName: 'Product 5', unitPrice: 5 },
    { productNumber: 'PN-0006', productName: 'Product 6', unitPrice: 7 },
    { productNumber: 'PN-0007', productName: 'Product 7', unitPrice: 22 },
    { productNumber: 'PN-0008', productName: 'Product 8', unitPrice: 4 },
    { productNumber: 'PN-0009', productName: 'Product 9', unitPrice: 10 },
    { productNumber: 'PN-0010', productName: 'Product 10', unitPrice: 14 },
    { productNumber: 'PN-0011', productName: 'Product 11', unitPrice: 27 },
    { productNumber: 'PN-0012', productName: 'Product 12', unitPrice: 30 },
  ]);

  const [cart, setCart] = useState([]);
  const [customerInfo, setCustomerInfo] = useState({
    customerName: '',
    address: '',
    createdBy: '',
    updatedBy: ''
  });
  const [orderSuccess, setOrderSuccess] = useState(false);
  const [orderConfirmation, setOrderConfirmation] = useState(null);

  // Add product to cart
  const addToCart = (product) => {
    const existingItem = cart.find(item => item.productNumber === product.productNumber);
    
    if (existingItem) {
      setCart(cart.map(item => 
        item.productNumber === product.productNumber 
          ? { ...item, quantity: item.quantity + 1, totalPrice: (item.quantity + 1) * item.unitPrice }
          : item
      ));
    } else {
      setCart([...cart, { 
        ...product, 
        quantity: 1, 
        totalPrice: product.unitPrice 
      }]);
    }
  };

  // Remove product from cart
  const removeFromCart = (productNumber) => {
    setCart(cart.filter(item => item.productNumber !== productNumber));
  };

  // Update quantity
  const updateQuantity = (productNumber, newQuantity) => {
    if (newQuantity < 1) return;
    
    setCart(cart.map(item => 
      item.productNumber === productNumber 
        ? { ...item, quantity: newQuantity, totalPrice: newQuantity * item.unitPrice }
        : item
    ));
  };

  // Calculate total amount
  const totalAmount = cart.reduce((sum, item) => sum + item.totalPrice, 0);

  // Handle checkout
  const handleCheckout = async () => {
    const orderData = {
      ...customerInfo,
      createdBy: customerInfo.customerName,
      updatedBy: customerInfo.customerName,
      totalAmount: totalAmount,
      items: cart
    };

    try {
      const response = await fetch('http://localhost:8080/api/orders', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(orderData),
      });

      if (!response.ok) {
        throw new Error('Failed to place order');
      }

      const responseData = await response.json();
      setOrderConfirmation(responseData);
      setOrderSuccess(true);
      setCart([]);
    } catch (error) {
      console.error('Error:', error);
      alert('Error placing order');
    }
  };

  return (
    <div className="store-container">
      <h1>Product Store</h1>
      
      <div className="store-layout">
        {/* Product Listing */}
        <div className="product-list">
          <h2>Products</h2>
          <div className="products">
            {products.map(product => (
              <div key={product.productNumber} className="product-card">
                <h3>{product.productName}</h3>
                <p>Product #: {product.productNumber}</p>
                <p>Price: ${product.unitPrice.toFixed(2)}</p>
                <button onClick={() => addToCart(product)}>Add to Cart</button>
              </div>
            ))}
          </div>
        </div>

        {/* Shopping Cart */}
        <div className="shopping-cart">
          <h2>Shopping Cart</h2>
          {cart.length === 0 ? (
            <p>Your cart is empty</p>
          ) : (
            <>
              <div className="cart-items">
                {cart.map(item => (
                  <div key={item.productNumber} className="cart-item">
                    <div className="item-info">
                      <h4>{item.productName}</h4>
                      <p>${item.unitPrice.toFixed(2)} × {item.quantity} = ${item.totalPrice.toFixed(2)}</p>
                    </div>
                    <div className="item-actions">
                      <input 
                        type="number" 
                        min="1" 
                        value={item.quantity}
                        onChange={(e) => updateQuantity(item.productNumber, parseInt(e.target.value))}
                      />
                      <button onClick={() => removeFromCart(item.productNumber)}>Remove</button>
                    </div>
                  </div>
                ))}
              </div>
              <div className="cart-summary">
                <h3>Total: ${totalAmount.toFixed(2)}</h3>
              </div>

              {/* Customer Information */}
              <div className="customer-info">
                <h3>Customer Information</h3>
                <div className="form-group">
                  <label>Name:</label>
                  <input 
                    type="text" 
                    value={customerInfo.customerName}
                    onChange={(e) => setCustomerInfo({...customerInfo, customerName: e.target.value})}
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Address:</label>
                  <input 
                    type="text" 
                    value={customerInfo.address}
                    onChange={(e) => setCustomerInfo({...customerInfo, address: e.target.value})}
                    required
                  />
                </div>
                <button 
                  onClick={handleCheckout} 
                  disabled={!customerInfo.customerName || !customerInfo.address}
                >
                  Place Order
                </button>
              </div>
            </>
          )}

          {orderSuccess && orderConfirmation && (
            <div className="order-success">
            <h3>Order Placed Successfully!</h3>
            <div className="order-details">
                <h4>Order Confirmation</h4>
                <p><strong>Order Number:</strong> {orderConfirmation.orderNumber}</p>
                <p><strong>Status:</strong> {orderConfirmation.orderStatus}</p>
                <p><strong>Tracking Number:</strong> {orderConfirmation.trackingNumber}</p>
                <p><strong>Order Date:</strong> {new Date(orderConfirmation.orderDate).toLocaleString()}</p>
            </div>
            <button onClick={() => {
                setOrderSuccess(false);
                setOrderConfirmation(null);
                }}>
            Continue Shopping
            </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProductStore;