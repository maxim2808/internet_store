package com.example.internet_store.controllers;

import com.example.internet_store.models.Order;
import com.example.internet_store.models.ShoppingCart;
import com.example.internet_store.services.OrderService;
import com.example.internet_store.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    final OrderService orderService;
    final ProductService productService;


    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/createOrder")
    public String createOrder(HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (cart != null) {

            double totalPrice = productService.totalPrice(cart.getProducts());
            Order order = new Order();
            order.setOrderPrice(totalPrice);
            orderService.saveOrders(order, cart.getProducts().stream().map(product -> productService.convertToProduct(product)).toList());
           cart = null;
            session.setAttribute("shoppingCart", cart);
        }
        return "/order/createOrder";

    }
}