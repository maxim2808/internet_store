package com.example.internet_store.repositories;

import com.example.internet_store.models.Order;
import com.example.internet_store.models.Persone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    public List<Order> findByOrderStatus(String status);
    public List<Order> findByPersone(Persone persone);
}
