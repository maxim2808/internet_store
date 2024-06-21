package com.example.internet_store.services;

import com.example.internet_store.dto.OrderDTO;
import com.example.internet_store.models.Order;
import com.example.internet_store.models.Product;
import com.example.internet_store.models.ProductOrder;
import com.example.internet_store.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class OrderService {
    final OrderRepository orderRepository;
    final ProductService productService;
    final ProductOrderService productOrderService;
    final ModelMapper modelMapper;
    @Value("${listStatus}")
    public String StatusString;
   // public  List<String> listStatus = Arrays.asList(StatusString.split(","));

    public OrderService(OrderRepository orderRepository, ProductService productService, ProductOrderService productOrderService, ModelMapper modelMapper) {

        this.orderRepository = orderRepository;
        this.productService = productService;
        this.productOrderService = productOrderService;
        this.modelMapper = modelMapper;
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findOrderById(int id) {
        return orderRepository.findById(id);
    }


    @Transactional
    public void saveOrders(Order order2, List<Product> productList) {
        order2.setRegistrationDate(new Date());
        order2.setOrderStatus(getStatusList().get(0));
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


    @Transactional
    public void editStatus (Order order, int id) {
        order.setOrderId(id);
        orderRepository.save(order);
    }

    public OrderDTO convertToDTO(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }

    public Order convertToOrder(OrderDTO dto){
        return modelMapper.map(dto, Order.class);
    }

    public List<String> getStatusList(){
   //     return Arrays.asList(StatusString.split(","));
        List<String> statusList = new ArrayList<>();
        statusList.add("Не обработан");
        statusList.add("Выполняется");
        statusList.add("Завершен");
        return statusList;
    }

}







