package com.example.internet_store.controllers;

import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.dto.ProductDTOList;
import com.example.internet_store.dto.QuantityDTO;
import com.example.internet_store.models.ShoppingCart;
import com.example.internet_store.services.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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





//    @PostMapping("")
//    public String addCourt(@ModelAttribute("listModel") ProductDTO productDTO) {
//        System.out.println("Name " + productDTO.getProductName());
//        System.out.println("Quantity "+ productDTO.getQuantity());
//        return "redirect:/product";
//    }
//
//


    @GetMapping("/edit")
    public String editCourt(HttpSession session, Model model){
        ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (cart!=null){
            List<ProductDTO> productList = cart.getProducts();
            productService.addFolderName(productList);
            ProductDTOList productDTOList = new ProductDTOList();
            for (ProductDTO productDTO : productList) {
                productDTOList.addProductDTO(productDTO);
            }

            model.addAttribute("listModel", productList);
            model.addAttribute("totalPrice", productService.totalPrice(productList));
            model.addAttribute("ProductDTOListModel", productDTOList);
        }
        return "/cart/editCartPage";
    }

    @PatchMapping("edit")
    public String postEditCourt(HttpSession session
           ,@ModelAttribute("ProductDTOListModel") ProductDTOList productDTOList
    ) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
      if (cart!=null){
       // ProductDTOList productDTOList = new ProductDTOList();

        for(int i=0; i<cart.getProducts().size(); i++){
           cart.getProducts().get(i).setQuantity(productDTOList.getProductsDTO().get(i).getQuantity());
        }
        productService.deleteProductWhereCountZeroOrLess(session);}
        return "redirect:/cart";
    }





//    @PostMapping("/view/{productURL}")
//    public String postViewProduct(HttpSession session, @PathVariable("productURL") String productUrl, Model model,
//         @ModelAttribute("oneProductModel") ProductDTO productDTO
//                                  ,@ModelAttribute("quantityModel") @Valid QuantityDTO quantityDTO, BindingResult bindingResult
//                                  ) {
//        System.out.println("post started");
//        if (bindingResult.hasErrors()) {
//            return "/product/oneProductPage";
//        }
//        if (quantityDTO.getQuantity()>0){
//        productService.addProductToCart(session, productUrl, quantityDTO.getQuantity());
//
//        // return "redirect:/product/view/" + productUrl;
//        return "/cart/successfullyPage";
//    }
//        else {
//            return "/cart/errorPage";}
//
//    }

    @GetMapping("/clear")
    public String clearCart(HttpSession session) {
        ShoppingCart shoppingCart = null;
        session.setAttribute("shoppingCart", shoppingCart);
        return "redirect:/cart";
    }



}
