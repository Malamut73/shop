package com.online.shop.ws.greeting;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "name"
})
@XmlRootElement(name = "getGreetingRequest")
public class GetGreetingRequest {


    @XmlElement(required = true)
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
