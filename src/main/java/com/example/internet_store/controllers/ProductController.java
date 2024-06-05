package com.example.internet_store.controllers;

import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.models.Group;
import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.models.Product;
import com.example.internet_store.services.GroupService;
import com.example.internet_store.services.PictureService;
import com.example.internet_store.services.ManufacturerService;
import com.example.internet_store.services.ProductService;
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
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    final ProductService productService;
    final GroupService groupService;
    final ManufacturerService manufacturerService;
   final ProductValidator productValidator;
  //  private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;
    final PictureService pictureService;
    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;


    @Autowired
    public ProductController(ProductService productService, GroupService groupService, ManufacturerService manufacturerService,
                             ProductValidator productValidator,
                             //  DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration,
                             PictureService pictureService, DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration) {
        this.productService = productService;
        this.groupService = groupService;
        this.manufacturerService = manufacturerService;
      this.productValidator = productValidator;
      //  this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
        this.pictureService = pictureService;
        this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
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


//        if (!photo.isEmpty()) {
//            try {
//                // Сохранение загруженного файла на компьютере
//                String uploadDir = "C:\\Users\\max\\IdeaProjects\\internet_store\\download"; // Замените на путь к папке, куда вы хотите сохранить файл
//                String fileName = photo.getOriginalFilename();
//                File uploadFile = new File(uploadDir, fileName);
//                FileCopyUtils.copy(photo.getBytes(), uploadFile);
//
//            } catch (IOException e) {
//                // Обработка ошибки сохранения файла
//                e.printStackTrace();
//                return "redirect:/product/error/upload";
//            }
//        }
//        else {
//            System.out.println("Нет фото");
//        }


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
        pictureService.receiveImage(photo, product.getProductId());
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
//        try {
//            System.out.println("Try block is starting");
//            StringBuilder fileAddres = new StringBuilder("/static");
//            fileAddres = fileAddres.append(address);
//            System.out.println(fileAddres);
//            String address2 = "C:\\Users\\max\\IdeaProjects\\internet_store\\static\\download\\100-main.jpg";
//            String shortAddress = "/static/download/100-main.jpg";
//            File image = new File(shortAddress);
//            if (image.exists()){
//                System.out.println("файл с данным изображением сущесвует");
//            }
//        }
//        catch (Exception e) {
//            System.out.println("При получегт файла произошла ошибка " + e.getMessage());
//        }



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

        return "/product/editProductPage";
    }


    @PatchMapping("/edit/{productURL}")
    public String editProductPage(@ModelAttribute ("oneProductModel") @Valid ProductDTO productDTO,
                                  BindingResult bindingResult, @PathVariable("productURL") String productUrl, @ModelAttribute("oneGroupModel") Group group,
                                  @ModelAttribute("oneManufacturerModel") Manufacturer manufacturer, Model model) {
        int id = productService.getProductByProductUrl(productUrl).get().getProductId();
        productDTO.setProductId(id);
        if (productDTO.getProductURL().isBlank()){
            productDTO.setProductURL(productService.createProductUrl(productDTO.getProductName()));}
        else{
            productDTO.setProductURL(productService.characterReplacementForUrl(productDTO.getProductURL()));
        };
        productValidator.validate(productDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupListModel", groupService.findAll());
            model.addAttribute("manufacturerListModel", manufacturerService.getAllManufacturers());
            return "/product/editProductPage";
        }

        Product product = productService.convertToProduct(productDTO);
        productService.editProduct(product, group, manufacturer, id);
        return "redirect:/product";
    }

//    @GetMapping("/uploadPhoto")
//    public String getUploadPhoto() {
//        return "/product/uploadPhotoPage";
//    }
//
//
//    @PostMapping("/uploadPhoto")
//    public String uploadPhoto(@RequestParam("photo") MultipartFile photo) {
//        System.out.println("post start!!!!!!!!");
//        if (!photo.isEmpty()) {
//            try {
//                // Сохранение загруженного файла на компьютере
//                String uploadDir = "C:\\Users\\max\\IdeaProjects\\internet_store\\download"; // Замените на путь к папке, куда вы хотите сохранить файл
//                String fileName = photo.getOriginalFilename();
//                File uploadFile = new File(uploadDir, fileName);
//                FileCopyUtils.copy(photo.getBytes(), uploadFile);
//
//                return "/product/createProduct";
//            } catch (IOException e) {
//                // Обработка ошибки сохранения файла
//                e.printStackTrace();
//                return "redirect:/product/error/upload";
//            }
//        } else {
//            return "redirect:/product/error/upload";
//        }
//    }

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





