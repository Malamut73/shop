package com.online.shop.ws.greeting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "greeting", propOrder = {
        "text",
        "date"
})
public class Greeting {

    @XmlElement(required = true)
    protected String text;
    @XmlElement(required = true)
    protected XMLGregorianCalendar date;

    public String getText(){
        return text;
    }

    public void setText(String value){
        this.text = value;
    }

    public XMLGregorianCalendar getDate(){
        return date;
    }

    public void setDate(XMLGregorianCalendar date) {
        this.date = date;
    }
}
