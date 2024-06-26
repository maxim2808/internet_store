package com.example.internet_store.controllers;

import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.models.*;
import com.example.internet_store.services.*;
import com.example.internet_store.utils.ProductValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    final ProductService productService;
    final GroupService groupService;
    final ManufacturerService manufacturerService;
    final ProductValidator productValidator;
    final ReceivePictureService receivePictureService;
    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;
    private final PictureService pictureService;
    @Value("${productPerPage}")
    private String productPerPageString;


    @Autowired
    public ProductController(ProductService productService, GroupService groupService, ManufacturerService manufacturerService,
                             ProductValidator productValidator,
                             ReceivePictureService receivePictureService, DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration, PictureService pictureService) {
        this.productService = productService;
        this.groupService = groupService;
        this.manufacturerService = manufacturerService;
        this.productValidator = productValidator;
        this.receivePictureService = receivePictureService;
        this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
        this.pictureService = pictureService;
    }

    @GetMapping("")
    public String index(
            Model model, @RequestParam(value = "page", defaultValue = "1", required = false) int page
            ,@RequestParam(value = "groupName", defaultValue = "Все группы", required = false ) String group,
            @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @RequestParam(value = "manufacturerName", defaultValue = "Все производители", required = false) String manufacturerName,
            @RequestParam(value = "sort", defaultValue = "", required = false) String sort

    ) {

       // group = "Мониторы";
        int productPerPage = Integer.parseInt(productPerPageString);
        List<String> groupNameList = new ArrayList<>(groupService.findAll().stream().map(group1 -> group1.getGroupName()).toList());
        List<String> manufacturerNameList = new ArrayList<>(manufacturerService.getAllManufacturers().stream().map(manufacturer->manufacturer.getManufacturerName()).toList());
        groupNameList.add(0,"Все группы");
        manufacturerNameList.add(0,"Все производители");
        model.addAttribute("groupNameListModel", groupNameList);
        model.addAttribute("manufacturerNameListModel", manufacturerNameList);
        Group groupForSelect = new Group();
        Manufacturer manufacturerForSelect = new Manufacturer();
        model.addAttribute("groupForSelectModel", groupForSelect);
        model.addAttribute("manufacturerForSelectModel", manufacturerForSelect);
        model.addAttribute("searchNameModel", searchName);
        List<ProductDTO> productDTOList;
        List<String> sortLostList = new ArrayList<>();
        sortLostList.add("Цена по возрастанию");
        sortLostList.add("Цена по убыванию");
        sortLostList.add("Название(А-Я)");
        sortLostList.add("Название(Я-А)");
        model.addAttribute("sortLostListModel", sortLostList);
//        model.addAttribute("sortModel", sort);


        if(searchName==null||searchName.equals("")){
            List <Product> sortedProductList =productService.sortedProduct(productService.getAllProducts(page, productPerPage, group, manufacturerName,
                            false ), sort);
            productDTOList = sortedProductList.stream().map(product -> productService.convertToProductDTO(product)).toList();
//          productDTOList = productService.sortedProduct(productService.getAllProducts(page, productPerPage, group, manufacturerName, false ).stream().map(product ->
//                    productService.convertToProductDTO(product)).toList();
}
        else {
//             productDTOList = productService.getAllProducts(page, productPerPage, group, searchName).stream().map(product ->
//                    productService.convertToProductDTO(product)).toList();
            productDTOList = productService.searchProducts(page, productPerPage, searchName).stream().map
                    (product -> productService.convertToProductDTO(product)).toList();
        }

        productService.addFolderName(productDTOList);
       model.addAttribute("numberOfPageModel", productService.listPage(productPerPage));
       model.addAttribute("productPerPageModel", productPerPage);
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
            System.out.println("binding result has error!!!!!!!!!!!!!!!!!!!");
            model.addAttribute("groupListModel", groupService.findAll());
            model.addAttribute("manufacturerListModel", manufacturerService.getAllManufacturers());
            return "/product/createProduct";
        }


        Product product = productService.convertToProduct(productDTO);
        receivePictureService.setSimilarPicture(photo, productDTO, product);
        productService.saveProduct(product, group, manufacturer);
        receivePictureService.receiveImage(photo, product.getProductId());

        return "redirect:/product";
    }

    @GetMapping("/view/{productURL}")
    public String oneProductPage (HttpSession session, @PathVariable("productURL") String productUrl, Model model) {
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
//
//    @PostMapping("/view/{productURL}")
//
//    public String postViewProduct(HttpSession session, @PathVariable("productURL") String productUrl, Model model, ProductDTO productDTO) {
//        productService.addProductToCart(session, productUrl, productDTO.getQuantity());
//       // return "redirect:/product/view/" + productUrl;
//        return "/cart/successfullyPage";
//
//    }



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
                                  @RequestParam("photo") MultipartFile photo,
                                  Model model) throws IOException {
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
        if (photo.isEmpty()){
            receivePictureService.setSimilarPicture(photo, productDTO, product);
            productService.editProduct(product, group, manufacturer, id );
        }
       else {receivePictureService.receiveImage(photo, product.getProductId());}
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

    @DeleteMapping("/delete/{productURL}")
    public String deleteProduct(@PathVariable("productURL") String productURL) {
      Product product = productService.getProductByProductUrl(productURL).get();
      productService.deleteProductById(product.getProductId());
      return "redirect:/product";
    }

//    @GetMapping("/cart")
//    public String getCourt(HttpSession session, Model model){
//
//        ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
//        if (cart!=null){
//        List<ProductDTO> productList = cart.getProducts();
//        productService.addFolderName(productList);
//        model.addAttribute("listModel", productList);
//        model.addAttribute("totalPrice", productService.totalPrice(productList));
//        }
//        return "/product/cartPage";
//    }
//
//    @PostMapping("/cart")
//    public String addCourt(@ModelAttribute("listModel") ProductDTO productDTO) {
//                    System.out.println("Name " + productDTO.getProductName());
//            System.out.println("Quantity "+ productDTO.getQuantity());
//        return "redirect:/product";
//    }
}





