package com.example.internet_store.services;

import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.models.Group;
import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.models.Product;
import com.example.internet_store.repositories.ProductRepositories;
import com.ibm.icu.text.Transliterator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    final ProductRepositories productRepositories;
    final GroupService groupService;
    final ManufacturerService manufacturerService;
    final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepositories productRepositories, GroupService groupService, ManufacturerService manufacturerService, ModelMapper modelMapper) {
        this.productRepositories = productRepositories;
        this.groupService = groupService;
        this.manufacturerService = manufacturerService;
        this.modelMapper = modelMapper;
    }

    public List<Product> getAllProducts() {
       return productRepositories.findAll();
    }

//    public List<ProductDTO> getAllProductsDTO() {
//        List<Product> products = productRepositories.findAll();
//        List<ProductDTO> productsDTO = new ArrayList<>();
//        for (Product product : products) {
//            productsDTO.add(convertToProductDTO(product));
//        }
//        return productsDTO;
//    }


    public Optional<Product> getProductById(int id) {
        return productRepositories.findById(id);
    }

    @Transactional
    public void saveProduct(Product product, Group group, Manufacturer manufacturer) {
        product.setRegistrationDate(new Date());
        product.setDiscount(0);
        product.setPopular(false);
        product.setProductGroup(groupService.findById(group.getGroupId()).get());
        product.setManufacturer(manufacturerService.findById(manufacturer.getManufacurerId()).get());
        productRepositories.save(product);
    }
    public Optional<Product> getProductByName(String name) {
      return   productRepositories.findByProductName(name);
    }

    @Transactional
    public void editProduct(Product receivedProduct, Group group, Manufacturer manufacturer, int id) {
        receivedProduct.setProductGroup(groupService.findById(group.getGroupId()).get());
        receivedProduct.setManufacturer(manufacturerService.findById(manufacturer.getManufacurerId()).get());
    receivedProduct.setProductId(id);
    productRepositories.save(receivedProduct);
    }

    public Optional<Product> getProductByProductUrl(String productUrl) {
       return productRepositories.findByProductURL(productUrl);
    }

    public Product convertToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO convertToProductDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public String createProductUrl(String name){
        Transliterator transliterator = Transliterator.getInstance(("Russian-Latin/BGN"));
        String englishName = transliterator.transliterate(name);
       String urlWord= characterReplacementForUrl(englishName);
    return urlWord;

    }

    public String characterReplacementForUrl(String englishName) {
        String urlWord = englishName.toLowerCase()
                .replaceAll("[^a-zA-Z0-9-]", "-") // Замена символов, кроме букв, цифр и дефиса на дефис
                .replaceAll("-{2,}", "-") // Удаление повторяющихся дефисов
                .replaceAll("^-|-$", ""); // Удаление дефисов в начале и конце строки
        return urlWord;
    }


}
