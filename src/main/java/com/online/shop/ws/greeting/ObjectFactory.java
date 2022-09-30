package com.online.shop.ws.greeting;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public GetGreetingResponse createdGetGreetingResponse(){
        return new GetGreetingResponse();
    }

    public Greeting createGreeting(){
        return new Greeting();
    }

    public GetGreetingRequest createGetGreetingRequest(){
        return new GetGreetingRequest();
    }

}
