package com.example.SpringBoot;

import com.example.SpringBoot.exceptions.ProductNotFoundException;
import com.example.SpringBoot.product.ProductRepository;
import com.example.SpringBoot.product.model.Product;
import com.example.SpringBoot.product.model.ProductDTO;
import com.example.SpringBoot.product.services.GetProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductService getProductService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenProductExists_whenGetProduct_serviceReturnProductDTO(){
        //Given
        Product product = new Product();
        product.setId(1);
        product.setName("Product Name");
        product.setDescription("Product Description which is at least 20 chars");
        product.setPrice(9.99);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        //When
        ResponseEntity<ProductDTO> response = getProductService.execute(1);


        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new ProductDTO(product), response.getBody());
        verify(productRepository, times(1)).findById(1);


    }

    @Test
    public void givenProductDoesNotExist_whenGetProductService_throwProductNotFoundException(){
        //Given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        //When & Then
        assertThrows(ProductNotFoundException.class, () -> getProductService.execute(1));

        verify(productRepository, times(1)).findById(1);

    }
}


