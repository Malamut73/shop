package com.online.shop.ws.products;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "productsWS", propOrder = {
        "id",
        "title",
        "price"
})
public class ProductsWS {

    protected long id;
    @XmlElement(required = true)
    protected String title;
    protected double price;


    public long getId() {
        return id;
    }


    public void setId(long value) {
        this.id = value;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String value) {
        this.title = value;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double value) {
        this.price = value;
    }

}