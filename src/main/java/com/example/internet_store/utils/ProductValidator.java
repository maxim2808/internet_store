package com.example.internet_store.utils;

import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.models.Product;
import com.example.internet_store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class ProductValidator implements Validator {


   final ProductService productService;

    public ProductValidator(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ProductDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
    ProductDTO productDTO = (ProductDTO) target;

        if (productService.getProductByProductUrl(productDTO.getProductURL()).isPresent()) {
            Product product =productService.getProductByProductUrl(productDTO.getProductURL()).get();
            if (product.getProductId()!=productDTO.getProductId()) {
                errors.rejectValue("productURL", "", "Товар с таким URL уще существует");
            }
        }

        if (productService.getProductByName(productDTO.getProductName()).isPresent()) {
            Product product = productService.getProductByName(productDTO.getProductName()).get();
            if (product.getProductId()!=productDTO.getProductId()) {
            errors.rejectValue("productName", "", "Товар с таким именем уже существует");
            }
        }

        if (!productService.getProductByName(productDTO.getSimilarProductName()).isPresent()&&!productDTO.getSimilarProductName().isBlank()) {
            errors.rejectValue("similarProductName", "", "Такого товара не существует");
        }

        if (productDTO.getProductURL().isBlank()) {
            errors.rejectValue("productURL", "", "Поле не должно быть пустым");
        }





    }
}
