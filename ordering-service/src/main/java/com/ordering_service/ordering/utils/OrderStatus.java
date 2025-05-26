package com.ordering_service.ordering.utils;

public enum OrderStatus {
    PENDING, // when creating of tracking number failed
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
