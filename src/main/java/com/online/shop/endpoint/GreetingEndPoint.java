package com.online.shop.endpoint;

import com.online.shop.service.implementation.GreetingService;
import com.online.shop.ws.greeting.GetGreetingRequest;
import com.online.shop.ws.greeting.GetGreetingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
public class GreetingEndPoint {

    public static final String NAMESPACE_GREETING = "http://online.com/shop/ws/greeting";


    private GreetingService greetingService;

    @Autowired
    public GreetingEndPoint(GreetingService greetingService){
        this.greetingService = greetingService;
    }

    @PayloadRoot(namespace = NAMESPACE_GREETING, localPart = "getGreetingRequest")
    @ResponsePayload
    public GetGreetingResponse getGreeting(@RequestPayload GetGreetingRequest request) throws DatatypeConfigurationException{
        GetGreetingResponse response = new GetGreetingResponse();
        response.setGreeting(greetingService.generateGreeting(request.getName()));
        return response;
    }

}
