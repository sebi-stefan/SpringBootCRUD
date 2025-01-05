package com.example.SpringBoot.product.headers;

import com.example.SpringBoot.product.model.Product;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeaderController {

    @GetMapping("/header")
    public String getRegionalResponse(@RequestHeader(required = false, defaultValue = "US") String region) {
        if (region.equals("US")) return "Region: US";
        if (region.equals("CA")) return "Region: CA";

        return "Country not supported";

    }

    @GetMapping(value = "/header/product", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Product> getProduct(){
        Product product = new Product();
        product.setId(1);
        product.setName("Super product");
        product.setDescription("the greates product you'll ever see ever");

        return ResponseEntity.ok(product);
    }
}
