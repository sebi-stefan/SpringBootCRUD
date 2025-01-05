package com.example.SpringBoot;

import com.example.SpringBoot.product.ProductRepository;
import com.example.SpringBoot.product.model.Product;
import com.example.SpringBoot.product.model.ProductDTO;
import com.example.SpringBoot.product.services.GetProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class GetProductsServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductsService getProductsService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenProductsExist_whenGetProducts_serviceReturnProductsDTO(){
        //Given
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Product Name");
        product1.setDescription("Product Description which is at least 20 chars");
        product1.setPrice(9.99);

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Another Product");
        product2.setDescription("This is another product description, longer than 20 characters.");
        product2.setPrice(19.99);

        Product product3 = new Product();
        product3.setId(3);
        product3.setName("Yet Another Product");
        product3.setDescription("Description for yet another product, which exceeds 20 characters.");
        product3.setPrice(29.99);

        List<Product> products = new ArrayList<>(List.of(product1, product2, product3));

        when(productRepository.findAll()).thenReturn(products);

        //When
        ResponseEntity<List<ProductDTO>> response = getProductsService.execute(null);


        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        for(int i = 0; i < response.getBody().size(); i++){
            assertEquals(response.getBody().get(i), new ProductDTO(products.get(i)));
        }

        verify(productRepository, times(1)).findAll();


    }


}
