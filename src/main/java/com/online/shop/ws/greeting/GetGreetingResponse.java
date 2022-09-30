package com.online.shop.ws.greeting;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "greeting"
})
@XmlRootElement(name = "getGreetingResponse")
public class GetGreetingResponse {

    @XmlElement(required = true)
    protected Greeting greeting;

    public Greeting getGreeting() {
        return greeting;
    }

    public void setGreeting(Greeting greeting) {
        this.greeting = greeting;
    }
}
