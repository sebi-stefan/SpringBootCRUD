package com.example.SpringBoot;

import com.example.SpringBoot.product.ProductRepository;
import com.example.SpringBoot.product.model.Product;
import com.example.SpringBoot.product.model.ProductDTO;
import com.example.SpringBoot.product.services.CreateProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CreateProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductService createProductService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenProductIsValid_whenCreateProduct_serviceSaveAndReturnProductDTO(){
        //Given
        Product product = new Product();
        product.setId(1);
        product.setName("Product Name");
        product.setDescription("Product Description which is at least 20 chars");
        product.setPrice(9.99);

        when(productRepository.save(product)).thenReturn((product));

        //When
        ResponseEntity<ProductDTO> response = createProductService.execute(product);


        //Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(new ProductDTO(product), response.getBody());
        verify(productRepository, times(1)).save(product);


    }

}
