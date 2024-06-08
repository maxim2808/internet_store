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
        System.out.println("name " + productDTO.getProductName());
        System.out.println("url " + productDTO.getProductURL());



//        if (productService.getProductByProductUrl(productDTO.getProductURL()).isPresent()) {
//           Product product =productService.getProductByProductUrl(productDTO.getProductURL()).get();
//           if (product.getProductId()!=productDTO.getProductId()) {
//            errors.rejectValue("productURL", "", "Товар с таким URL уще существует");
//           }
//        }


        if (productService.getProductByProductUrl(productDTO.getProductURL()).isPresent()) {
            System.out.println("validation is editing of product");
            Product product =productService.getProductByProductUrl(productDTO.getProductURL()).get();
            System.out.println("validation product id :" + product.getProductId());
            System.out.println("validation productDTO id :" + productDTO.getProductId());
            if (product.getProductId()!=productDTO.getProductId()) {
                System.out.println("error!!!!!!!!!!!!!!!");
                errors.rejectValue("productURL", "", "Товар с таким URL уще существует");
            }
        }



        ;
        if (productService.getProductByName(productDTO.getProductName()).isPresent()) {
            Product product = productService.getProductByName(productDTO.getProductName()).get();
            System.out.println("product name " + product.getProductName() + product.getProductId());
            System.out.println("productDTO name " + productDTO.getProductName() + productDTO.getProductId());
            if (product.getProductId()!=productDTO.getProductId()) {
            errors.rejectValue("productName", "", "Товар с таким именем уже существует");
            }
        }
        if (productDTO.getProductURL().isBlank()) {
            errors.rejectValue("productURL", "", "Поле не должно быть пустым");
        }


    }
}
