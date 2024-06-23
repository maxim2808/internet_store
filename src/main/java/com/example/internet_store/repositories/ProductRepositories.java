package com.example.internet_store.repositories;

import com.example.internet_store.models.Group;
import com.example.internet_store.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepositories extends JpaRepository<Product, Integer> {
    public Optional<Product> findByProductURL(String productUri);
    public Optional<Product> findByProductName(String productName);
    public List<Product> findByGroup(Group group);
    public Page<Product> findByGroup(Group group, Pageable pageable);
   // public List<Product> findProductByProductNameStartingWith(String productName);
    public Page<Product> findProductByProductNameStartingWith(String productName, Pageable pageable);
    public Page<Product> findProductByProductNameStartingWithAndGroup(String productName, Group group, Pageable pageable);
    public List<Product> findProductByProductNameStartingWithAndGroup(String productName, Group group);

}
