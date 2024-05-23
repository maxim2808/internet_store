package com.example.internet_store;

import com.example.internet_store.models.Persone;
import com.example.internet_store.models.Product;
import com.example.internet_store.services.PersoneService;
import com.example.internet_store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
@Component
public class InternetStoreApplication {
	 PersoneService personeService;

     @Bean
     ModelMapper modelMapper() {
         return new ModelMapper();
     }

	@Autowired
    public InternetStoreApplication(PersoneService personeService) {
        this.personeService = personeService;
    }

    public static void main(String[] args) throws IOException {
//		ApplicationContext context = SpringApplication.run(InternetStoreApplication.class, args);
//		ProductService productService = context.getBean(ProductService.class);
//		Product product = new Product();
//		product.setItemName("Test product3");
//		product.setPrice(25.0);
// String path = "C:\\Users\\max\\IdeaProjects\\internet_store\\images\\lgl70.jpg";
//
// 		File file = new File(path);
//		 byte[] bytes = Files.readAllBytes(file.toPath());
//		 product.setImage(bytes);
//		productService.saveProduct(product);


		SpringApplication.run(InternetStoreApplication.class, args);


	}

}
