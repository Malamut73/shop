package com.online.shop.ws.products;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public GetProductsRequest createdGetProductsRequest(){
        return new GetProductsRequest();
    }

    public ProductsWS createProductsWS(){
        return new ProductsWS();
    }

    public GetProductsResponse createGetProductsResponse(){
        return new GetProductsResponse();
    }


    

}
