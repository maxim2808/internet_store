package com.example.internet_store.controllers;

import com.example.internet_store.dto.OrderDTO;
import com.example.internet_store.models.Order;
import com.example.internet_store.models.ProductOrder;
import com.example.internet_store.models.ShoppingCart;
import com.example.internet_store.services.OrderService;
import com.example.internet_store.services.ProductOrderService;
import com.example.internet_store.services.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    final OrderService orderService;
    final ProductService productService;
    final ProductOrderService productOrderService;


    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;


    public OrderController(OrderService orderService, ProductService productService, ProductOrderService productOrderService,  DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration) {
        this.orderService = orderService;
        this.productService = productService;
        this.productOrderService = productOrderService;
        this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
    }

    @GetMapping("")
    public String getOrder(Model model, @RequestParam(value = "orderStatus", defaultValue = "Все статусы", required = false) String status) {
        System.out.println("controller status " + status);
        List<String> statusList = orderService.getStatusList();
        statusList.add("Все статусы");
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderStatus(status);
        model.addAttribute("statusModel",orderDTO);
        model.addAttribute("listStatusModel", statusList);
        model.addAttribute("orderModel", orderService.findAllOrders(status).stream().map(order -> orderService.convertToDTO(order)).toList());
        return "/order/AllOrdersPage";

    }


    @GetMapping("/createOrder")
    public String getCreateOrder(HttpSession session, Model model) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (cart != null) {
            OrderDTO orderDTO = new OrderDTO();
            model.addAttribute("orderModel", orderDTO);
        }
//        else {
//            model.addAttribute("orderModel", null);
//        }


        return "/order/createOrder";

    }

    @PostMapping("/createOrder")
    public String postCreateOrder(HttpSession session, @ModelAttribute("orderModel") @Valid OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/order/createOrder";
        }
        ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (orderDTO!= null) {
            double totalPrice = productService.totalPrice(cart.getProducts());
            orderDTO.setOrderPrice(totalPrice);
            Order order = orderService.convertToOrder(orderDTO);
            orderService.saveOrders(order, cart.getProducts().stream().map(product -> productService.convertToProduct(product)).toList());
            cart = null;
            session.setAttribute("shoppingCart", cart);
        }
        return "redirect:/product";
    }

    @GetMapping("/view/{id}")
    public String getView(@PathVariable("id") int id, Model model) {
        Order order = orderService.findOrderById(id).get();
        Date registrationDate = order.getRegistrationDate();
        OrderDTO orderDTO = orderService.convertToDTO(order);
        model.addAttribute("orderModel", orderDTO);
        model.addAttribute("registrationDate", registrationDate);
        List<ProductOrder> list = productOrderService.findByOrder(order);
        model.addAttribute("listProductOrderModel", list);
        List<String> listStatus = new ArrayList<>();
        listStatus.add("Не обработан");
        listStatus.add("Выполняется");
        listStatus.add("Завершен");
        listStatus.add("Отменен");
        model.addAttribute("listStatusModel", orderService.getStatusList());
        System.out.println("String data " + order.getStringDate());
        return "/order/viewOrderPage";
    }


    @PatchMapping("/view/{id}")
    public String patchView(@PathVariable("id") int id, @ModelAttribute("orderModer") OrderDTO orderDTO,
                            @ModelAttribute("registrationDate") Date registrationDate){
        System.out.println("patch order started");
        Order order = orderService.convertToOrder(orderDTO);
        order.setRegistrationDate(registrationDate);
    orderService.editStatus(order, id);
    return "redirect:/order";

    }
//






}