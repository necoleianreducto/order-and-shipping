package com.ordering_service.ordering.client;

import com.ordering_service.ordering.dto.request.ShippingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "shipping-service",
        url = "${service.shipping.url}")
public interface ShippingClient {

    @PostMapping("/create-tracking")
    String createTrackingNumber(@RequestBody ShippingRequest shippingRequest);


}
