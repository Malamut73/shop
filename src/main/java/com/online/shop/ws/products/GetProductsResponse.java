package com.online.shop.ws.products;


import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "products"
})
@XmlRootElement(name = "getProductsResponse")
public class GetProductsResponse {

    @XmlElement(required = true)
    protected List<ProductsWS> products;

    public List<ProductsWS> getProducts() {
        if (products == null) {
            products = new ArrayList<ProductsWS>();
        }
        return this.products;
    }

}
