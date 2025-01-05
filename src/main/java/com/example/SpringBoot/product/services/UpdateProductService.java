package com.example.SpringBoot.product.services;

import com.example.SpringBoot.Command;
import com.example.SpringBoot.exceptions.ProductNotFoundException;
import com.example.SpringBoot.product.ProductRepository;
import com.example.SpringBoot.product.model.Product;
import com.example.SpringBoot.product.model.ProductDTO;
import com.example.SpringBoot.product.model.UpdateProductCommand;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateProductService implements Command<UpdateProductCommand, ProductDTO> {

    private ProductRepository productRepository;

    public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @CachePut(value = "productCache", key = "#command.getId()")
    public ResponseEntity<ProductDTO> execute(UpdateProductCommand command) {

        Optional<Product> productOptional = productRepository.findById(command.getId());
        if(productOptional.isPresent()){
            Product product = command.getProduct();
            product.setId(command.getId());
//            ProductValidator.execute(product);
            productRepository.save(product);
            return ResponseEntity.ok(new ProductDTO(product));
        }

        throw new ProductNotFoundException();
    }
}
