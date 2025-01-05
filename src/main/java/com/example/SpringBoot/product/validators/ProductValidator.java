package com.example.SpringBoot.product.validators;

import com.example.SpringBoot.exceptions.ErrorMessages;
import com.example.SpringBoot.exceptions.ProductNotValidException;
import com.example.SpringBoot.product.model.Product;
import org.springframework.util.StringUtils;

public class ProductValidator {

    private ProductValidator(){}

    public static void execute(Product product){
        if(StringUtils.isEmpty(product.getName())){
            throw new ProductNotValidException(ErrorMessages.NAME_REQUIRED.getMessage());
        }
        if(product.getDescription().length() < 20){
            throw new ProductNotValidException(ErrorMessages.DESCRIPTION_LENGTH.getMessage());
        }
        if(product.getPrice() == null | product.getPrice() < 0.00){
            throw new ProductNotValidException(ErrorMessages.PRICE_CANNOT_BE_NEGATIVE.getMessage());
        }
    }
}
