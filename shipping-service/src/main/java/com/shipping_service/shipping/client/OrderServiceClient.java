package com.shipping_service.shipping.client;

import com.shipping_service.shipping.config.FeignConfig;
import com.shipping_service.shipping.dto.response.OrderAndItemsResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "order-service",
        url = "${order.service.url}",
        configuration = FeignConfig.class
)
public interface OrderServiceClient {

    @GetMapping("/api/orders/forShipping")
    List<OrderAndItemsResponseDTO> getForShippingOrders();
}
