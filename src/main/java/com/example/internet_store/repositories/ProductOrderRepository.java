package com.example.internet_store.repositories;

import com.example.internet_store.models.Order;
import com.example.internet_store.models.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    List<ProductOrder> findByOrder(Order order);
}
