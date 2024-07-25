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


     @Bean
     ModelMapper modelMapper() {
         return new ModelMapper();
     }



    public static void main(String[] args) throws IOException {



		SpringApplication.run(InternetStoreApplication.class, args);


	}

}
