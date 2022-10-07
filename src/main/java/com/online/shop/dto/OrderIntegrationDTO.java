package com.online.shop.dto;

import com.online.shop.domain.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderIntegrationDTO {
    private Long orderId;
    private String username;
    private String address;
    private List<OrderDetailsDTO> details;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetailsDTO {
        private String product;
        private Double price;
        private Double amount;
        private Double sum;

        public OrderDetailsDTO(OrderDetails details) {
            this.product = details.getProduct().getTitle();
            this.price = details.getPrice().doubleValue();
            this.amount = details.getAmount().doubleValue();
            this.sum = details.getPrice().multiply(details.getAmount()).doubleValue();
        }
    }
}
