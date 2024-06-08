package com.example.internet_store.controllers;

import com.example.internet_store.dto.GroupDTO;
import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.models.Group;
import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.models.Picture;
import com.example.internet_store.models.Product;
import com.example.internet_store.services.*;
import com.example.internet_store.utils.ProductValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    final ProductService productService;
    final GroupService groupService;
    final ManufacturerService manufacturerService;
   final ProductValidator productValidator;
  //  private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;
   // final PictureService pictureService;
    final ReceivePictureService receivePictureService;
    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;
    private final PictureService pictureService;


    @Autowired
    public ProductController(ProductService productService, GroupService groupService, ManufacturerService manufacturerService,
                             ProductValidator productValidator,
                             //  DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration,
                             //  PictureService pictureService,
                             ReceivePictureService receivePictureService, DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration, PictureService pictureService) {
        this.productService = productService;
        this.groupService = groupService;
        this.manufacturerService = manufacturerService;
      this.productValidator = productValidator;
      //  this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
     //   this.pictureService = pictureService;
        this.receivePictureService = receivePictureService;
        this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
        this.pictureService = pictureService;
    }

    @GetMapping("")
    public String index(Model model) {

       List<ProductDTO> productDTOList = productService.getAllProducts().stream().map(product -> productService.convertToProductDTO(product)).toList();
     model.addAttribute("productsModel", productDTOList);

        return "product/productPage";
    }

    @GetMapping("/create")
    public String createProduct(@ModelAttribute ("createProductModel") ProductDTO productDTO, Model model) {
        model.addAttribute("groupListModel", groupService.findAll());
        model.addAttribute("oneGroupModel", new Group());
        model.addAttribute("manufacturerListModel", manufacturerService.getAllManufacturers());
        model.addAttribute("oneManufacturer", new Manufacturer());
        return "/product/createProduct";
    }

    @PostMapping("/create")
    public String postCreateProduct(@ModelAttribute ("createProductModel")
                                      @Valid ProductDTO productDTO, BindingResult bindingResult,
                                    @ModelAttribute ("oneGroupModel") Group group, @ModelAttribute("oneManufacturer") Manufacturer manufacturer, Model model
            , @RequestParam("photo") MultipartFile photo) throws IOException
    {



        if (productDTO.getProductURL().isBlank()){
        productDTO.setProductURL(productService.createProductUrl(productDTO.getProductName()));}
        else{
            productDTO.setProductURL(productService.characterReplacementForUrl(productDTO.getProductURL()));
        };
        productValidator.validate(productDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("Binding Error");
            model.addAttribute("groupListModel", groupService.findAll());
            model.addAttribute("manufacturerListModel", manufacturerService.getAllManufacturers());
            return "/product/createProduct";
        }

        Product product = productService.convertToProduct(productDTO);
        System.out.println("before id " + product.getProductId());
        productService.saveProduct(product, group, manufacturer);
        System.out.println("Id!!! " + product.getProductId());
        receivePictureService.receiveImage(photo, product.getProductId());
        return "redirect:/product";
    }

    @GetMapping("/view/{productURL}")
    public String oneProductPage (@PathVariable("productURL") String productUrl, Model model) {
        Product product = productService.getProductByProductUrl(productUrl).get();
        if(product.getMainPicture()!=null){
        StringBuilder address = new StringBuilder("/download/");
        address.append(product.getMainPicture().getFileName());
            System.out.println(address);
            model.addAttribute("addressPicModel", address.toString());
        }
        else {
            model.addAttribute("addressPicModel");
        }
        model.addAttribute("oneProductModel", productService.convertToProductDTO(product));
        return "/product/oneProductPage";
    }

    @GetMapping("/edit/{productURL}")
    public String getEditPage(Model model, @PathVariable("productURL") String productUrl) {
        Product product = productService.getProductByProductUrl(productUrl).get();

        model.addAttribute("oneProductModel", productService.convertToProductDTO(product));
        model.addAttribute("oneGroupModel", product.getGroup());
        model.addAttribute("groupListModel", groupService.findAll());
        model.addAttribute("oneManufacturerModel", product.getManufacturer());
        model.addAttribute("manufacturerListModel", manufacturerService.getAllManufacturers());


        //model.addAttribute("mainPictureModel", pictureService.getPictureById(product.getMainPicture().getPictureId()));

        return "/product/editProductPage";
    }


    @PatchMapping("/edit/{productURL}")
    public String editProductPage(@ModelAttribute ("oneProductModel") @Valid ProductDTO productDTO,
                                  BindingResult bindingResult, @PathVariable("productURL") String productUrl,
                                  @ModelAttribute("oneGroupModel") Group group,
                                  @ModelAttribute("oneManufacturerModel") Manufacturer manufacturer,
                                  Model model) {

        Product oldProduct = productService.getProductByProductUrl(productUrl).get();
        int id = oldProduct.getProductId();
        productDTO.setProductId(id);
        productService.setUrlForProduct(productDTO);
        productValidator.validate(productDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupListModel", groupService.findAll());
            model.addAttribute("manufacturerListModel", manufacturerService.getAllManufacturers());
            return "/product/editProductPage";
        }
        Product product = productService.convertToProduct(productDTO);
        productService.enrichProductAfterEdit(product, oldProduct);
        productService.editProduct(product, group, manufacturer, id );
        System.out.println("save are finish");
        return "redirect:/product";
    }


    @GetMapping("/error/upload")
    public String getErrorUpload() {
        return "/product/uploadPhotoPage";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, Model model) {
        String errorMessage = "Превышен максимальный размер загружаемого файла.";
        model.addAttribute("errorMessage", errorMessage);
        // Вернуть представление, которое отображает сообщение об ошибке
        return "errorPage";
    }



}





