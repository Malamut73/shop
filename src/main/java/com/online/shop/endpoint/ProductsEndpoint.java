package com.online.shop.endpoint;



import com.online.shop.dto.ProductDTO;
import com.online.shop.service.ProductService;
import com.online.shop.ws.products.GetProductsRequest;
import com.online.shop.ws.products.GetProductsResponse;
import com.online.shop.ws.products.ProductsWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
public class ProductsEndpoint {

    public static final String NAMESPACE_URL = "http://online.com/shop/ws/products";


    private ProductService productService;

    @Autowired
    public ProductsEndpoint(ProductService productService){

        this.productService = productService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getGreeting(@RequestPayload GetProductsRequest request) throws DatatypeConfigurationException {
        GetProductsResponse response = new GetProductsResponse();
        productService.getAll()
                        .forEach(ProductDTO -> response.getProducts().add(generateProductWS(ProductDTO)));
        return response;
    }

    public ProductsWS generateProductWS(ProductDTO productDTO) {
        ProductsWS productsWS = new ProductsWS();
        productsWS.setId(productDTO.getId());
        productsWS.setTitle(productDTO.getTitle());
        productsWS.setPrice(productsWS.getPrice());
        return productsWS;
    }

}
