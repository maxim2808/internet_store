package com.example.internet_store.repositories;

import com.example.internet_store.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepositories extends JpaRepository<Product, Integer> {
    public Optional<Product> findByProductURL(String productUri);
    public Optional<Product> findByProductName(String productName);


}
