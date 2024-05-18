package com.example.internet_store.repositories;

import com.example.internet_store.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositories extends JpaRepository<Product, Integer> {

}
