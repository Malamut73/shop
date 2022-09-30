package com.online.shop.config;

import com.online.shop.endpoint.GreetingEndPoint;
import com.online.shop.endpoint.ProductsEndpoint;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


@EnableWs
@Configuration
public class WebServiceConfig {


    @Bean
    public ServletRegistrationBean messageDispatcherServlet (ApplicationContext applicationContext){
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformSchemaLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "greeting")
    public DefaultWsdl11Definition defaultWsdl11Definition(){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("GreetingPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(GreetingEndPoint.NAMESPACE_GREETING);
        wsdl11Definition.setSchema(xsdGreetingSchema());
        return wsdl11Definition;
    }

    @Bean("greetingSchema")
    public XsdSchema xsdGreetingSchema(){
        return new SimpleXsdSchema(new ClassPathResource("ws/greeting.xsd"));
    }


    @Bean(name = "products")
    public DefaultWsdl11Definition productsWsdlDefinition(){
        DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
        defaultWsdl11Definition.setPortTypeName("ProductsPort");
        defaultWsdl11Definition.setLocationUri("/ws");
        defaultWsdl11Definition.setTargetNamespace(ProductsEndpoint.NAMESPACE_URL);
        defaultWsdl11Definition.setSchema(xsdProductsSchema());
        return defaultWsdl11Definition;
    }

    @Bean("productSchema")
    public XsdSchema xsdProductsSchema(){
        return new SimpleXsdSchema(new ClassPathResource("ws/products.xsd"));
    }


}
