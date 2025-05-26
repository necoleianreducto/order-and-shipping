package com.shipping_service.shipping.repository;

import com.shipping_service.shipping.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
