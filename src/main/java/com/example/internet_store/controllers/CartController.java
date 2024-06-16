package com.example.internet_store.controllers;

import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.models.ShoppingCart;
import com.example.internet_store.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String getCourt(HttpSession session, Model model){

        ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (cart!=null){
            List<ProductDTO> productList = cart.getProducts();
            productService.addFolderName(productList);
            model.addAttribute("listModel", productList);
            model.addAttribute("totalPrice", productService.totalPrice(productList));
        }
        return "/cart/cartPage";
    }

    @PostMapping("")
    public String addCourt(@ModelAttribute("listModel") ProductDTO productDTO) {
        System.out.println("Name " + productDTO.getProductName());
        System.out.println("Quantity "+ productDTO.getQuantity());
        return "redirect:/product";
    }

    @PostMapping("/view/{productURL}")

    public String postViewProduct(HttpSession session, @PathVariable("productURL") String productUrl, Model model, ProductDTO productDTO) {
        productService.addProductToCart(session, productUrl, productDTO.getQuantity());
        // return "redirect:/product/view/" + productUrl;
        return "/cart/successfullyPage";

    }

    @GetMapping("/clear")
    public String clearCart(HttpSession session) {
        ShoppingCart shoppingCart = null;
        session.setAttribute("shoppingCart", shoppingCart);
        return "redirect:/cart";

    }



}
