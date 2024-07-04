package com.example.internet_store.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductDTOList {
    List<ProductDTO>  productsDTO = new ArrayList<ProductDTO>();
    public void addProductDTO(ProductDTO productDTO) {
        productsDTO.add(productDTO);
    }

    public List<ProductDTO> getProductsDTO() {
        return productsDTO;
    }

    public void setProductsDTO(List<ProductDTO> productsDTO) {
        this.productsDTO = productsDTO;
    }
}
