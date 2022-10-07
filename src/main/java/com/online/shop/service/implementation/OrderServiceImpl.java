package com.online.shop.service.implementation;

import com.online.shop.config.OrderIntegrationConfig;
import com.online.shop.dao.OrderDAO;
import com.online.shop.domain.Order;
import com.online.shop.dto.OrderIntegrationDTO;
import com.online.shop.service.OrderService;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final OrderIntegrationConfig integrationConfig;

    public OrderServiceImpl(OrderDAO orderDAO, OrderIntegrationConfig integrationConfig) {
        this.orderDAO = orderDAO;
        this.integrationConfig = integrationConfig;
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        Order savedOrder = orderDAO.save(order);
        sendIntegrationNotify(savedOrder);
    }

    private void sendIntegrationNotify(Order order){
        OrderIntegrationDTO dto = new OrderIntegrationDTO();
        dto.setUsername(order.getUser().getName());
        dto.setAddress(order.getAddress());
        dto.setOrderId(order.getId());
        List<OrderIntegrationDTO.OrderDetailsDTO> details = order.getDetails().stream()
                .map(OrderIntegrationDTO.OrderDetailsDTO::new).collect(Collectors.toList());
        dto.setDetails(details);

        Message<OrderIntegrationDTO> message = MessageBuilder.withPayload(dto)
                .setHeader("Content-type", "application/json")
                .build();

        integrationConfig.getOrdersChannel().send(message);
    }

    @Override
    public Order getOrder(Long id) {
        return orderDAO.findById(id).orElse(null);
    }}
