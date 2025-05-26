package com.shipping_service.shipping.client;

import com.shipping_service.shipping.config.FeignConfig;
import com.shipping_service.shipping.dto.request.UpdateOrderStatusRequest;
import com.shipping_service.shipping.dto.response.OrderAndItemsResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "order-service",
        url = "${service.order.url}")
public interface OrderServiceClient {

    @GetMapping("/updateOrderStatus")
    void updateOrderStatus (@RequestBody UpdateOrderStatusRequest updateOrderStatusRequest);
}
