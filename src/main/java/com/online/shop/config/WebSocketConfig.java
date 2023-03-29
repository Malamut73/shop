package com.online.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").withSockJS();
    }


    //брокер сообщений который  будет использоваться для
    // направлениия сообщений от одного клиента к другому
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //чей адрес начинается с "/topic" должны быть направленны в брокер сообщений
        // который их перенаправляет всем клиентам
        registry.enableSimpleBroker("/topic");
        //чей адрес начинается с /app должен быть направлен в
        // MessageMapping("/....") в контроллере, занимающиейся
        // обработкой сообщений
        registry.setApplicationDestinationPrefixes("/app");
    }
}
