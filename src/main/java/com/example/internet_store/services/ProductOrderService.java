package com.example.internet_store.services;

import com.example.internet_store.models.ProductOrder;
import com.example.internet_store.repositories.ProductOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductOrderService {
    final ProductOrderRepository productOrderRepository;

    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    @Transactional
    public void save(ProductOrder productOrder) {
        productOrderRepository.save(productOrder);
    }
}
