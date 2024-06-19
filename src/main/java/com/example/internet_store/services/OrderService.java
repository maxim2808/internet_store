package com.example.internet_store.services;

import com.example.internet_store.models.Order;
import com.example.internet_store.models.Product;
import com.example.internet_store.models.ProductOrder;
import com.example.internet_store.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    final OrderRepository orderRepository;
    final ProductService productService;
    final ProductOrderService productOrderService;

    public OrderService(OrderRepository orderRepository, ProductService productService, ProductOrderService productOrderService) {

        this.orderRepository = orderRepository;
        this.productService = productService;
        this.productOrderService = productOrderService;
    }



        @Transactional
    public void saveOrders(Order order2, List<Product> productList) {
        order2.setRegistrationDate(new Date());
        order2.setOrderStatus("Не обработан");
        if (order2.getProductsInOrder() == null) {
            order2.setProductsInOrder(new ArrayList<ProductOrder>());
            //  System.out.println("Для продукта " + product.getProductName() + " создан список заказов");
        }
        orderRepository.save(order2);
        List<ProductOrder> productOrderList = order2.getProductsInOrder();
        for (Product product : productList) {
           ProductOrder productOrder = new ProductOrder();
           productOrder.setPrice(product.getPrice());
           productOrder.setQuantity(product.getQuantity());
           productOrder.setProduct(product);

            order2.getProductsInOrder().add(productOrder);
        }
        for (ProductOrder product : productOrderList) {

            product.setOrder(order2);
           // productService.editProduct(product, product.getGroup(), product.getManufacturer(), product.getProductId());
            productOrderService.save(product);
        }

    }












//    @Transactional
//    public void saveOrders(Order order2, List<Product> productList) {
//        order2.setRegistrationDate(new Date());
//        order2.setOrderStatus("Не обработан");
//        if (order2.getProductsInOrder() == null) {
//            order2.setProductsInOrder(new ArrayList<ProductOrder>());
//            //  System.out.println("Для продукта " + product.getProductName() + " создан список заказов");
//        }
//        orderRepository.save(order2);
//        for (Product product : productList) {
//            new ProductOrder(product.getProductId());
//            order2.getProductsInOrder().add(product);
//        }
//        for (Product product : productList) {
//            if (product.getOrderList() == null) {
//                product.setOrderList(new ArrayList<>());
//            }
//            product.getOrderList().add(order2);
//            productService.editProduct(product, product.getGroup(), product.getManufacturer(), product.getProductId());
//        }
//
//    }
//
//
//








}

//        for (Product product : order2.getProductsInOrder()) {
//            if (product.getOrderList()==null){
//                product.setOrderList(new ArrayList<>());
//            }
//            for(Order order: product.getOrderList()){
//                System.out.println("Товар " + product.getProductName() + "привязан к заказу " + order.getOrderId());
//
//            }
//            product.getOrderList().add(order2);
            //productService.editProduct(product, product.getGroup(), product.getManufacturer(), product.getProductId());

//    public void setOrderAndProducts(Order order, List<Product> products) {
//        order.setProductsInOrder(products);
//        for (Product product : products) {
//        }
//    }




