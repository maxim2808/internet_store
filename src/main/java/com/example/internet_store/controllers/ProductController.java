package com.example.internet_store.controllers;

import com.example.internet_store.dto.ManufacturerDTO;
import com.example.internet_store.dto.ManufactuterDTOList;
import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.dto.QuantityDTO;
import com.example.internet_store.models.*;
import com.example.internet_store.services.*;
import com.example.internet_store.utils.ProductValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
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
import java.util.Set;
import java.util.stream.Collectors;

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
    private final SpringDataWebAutoConfiguration springDataWebAutoConfiguration;
    @Value("${productPerPage}")
    private String productPerPageString;
    @Value("${pictureFolderInProject}")
    private String pictureFolderInProject;
    final PersoneService personeService;


    @Autowired
    public ProductController(ProductService productService, GroupService groupService, ManufacturerService manufacturerService,
                             ProductValidator productValidator,
                             ReceivePictureService receivePictureService, DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration, PictureService pictureService, PersoneService personeService, SpringDataWebAutoConfiguration springDataWebAutoConfiguration) {
        this.productService = productService;
        this.groupService = groupService;
        this.manufacturerService = manufacturerService;
        this.productValidator = productValidator;
        this.receivePictureService = receivePictureService;
        this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
        this.pictureService = pictureService;
        this.personeService = personeService;
        this.springDataWebAutoConfiguration = springDataWebAutoConfiguration;
    }

    @GetMapping("")
    public String index(
            Model model, @RequestParam(value = "page", defaultValue = "1", required = false) int page
            ,@RequestParam(value = "groupName", defaultValue = "Все группы", required = false ) String group,
            @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @RequestParam(value = "manufacturerName", defaultValue = "Все производители", required = false) String manufacturerName,
            @RequestParam(value = "sort", defaultValue = "", required = false) String sort

    ) {

        int productPerPage = Integer.parseInt(productPerPageString);
       // List<String> groupNameList = new ArrayList<>(groupService.findAll().stream().map(group1 -> group1.getGroupName()).toList());
      //  List<String> manufacturerNameList = new ArrayList<>(manufacturerService.getAllManufacturers().stream().map(manufacturer->manufacturer.getManufacturerName()).toList());
       // groupNameList.add(0,"Все группы");
       // manufacturerNameList.add(0,"Все производители");
        Set<String> groupNameList = groupService.getGroupsName(group);
        Set<String> manufacturerNameList = manufacturerService.getAllManufacturerNames(manufacturerName);


        List<ProductDTO> productDTOList;
        List<Integer> pages;

        if(searchName==null||searchName.equals("")){
            productDTOList =   productService.getSortedListProducts(page, productPerPage, sort,
                    group, manufacturerName,false).stream().map(product -> productService.convertToProductDTO(product)).toList();
            pages =productService.listPage(productPerPage, productService.getCountProducts(group, manufacturerName));


}
        else {
            productDTOList = productService.searchProducts(page, productPerPage, searchName).stream().map
                    (product -> productService.convertToProductDTO(product)).toList();
            pages =productService.listPage(productPerPage, productService.getSearchProductsSize(searchName));

        }

        productService.addFolderName(productDTOList);
        model.addAttribute("groupNameListModel", groupNameList);
        model.addAttribute("manufacturerNameListModel", manufacturerNameList);
        model.addAttribute("groupForSelectModel", new Group());
        model.addAttribute("manufacturerForSelectModel", new Manufacturer());
        model.addAttribute("searchNameModel", searchName);
        model.addAttribute("listObjectGroup", groupService.findAll().stream().map(group1 -> groupService.convertToDTO(group1)).toList());
        model.addAttribute("sortListModel", productService.fillSortList(sort));
        model.addAttribute("numberOfPageModel", pages);
        model.addAttribute("productPerPageModel", productPerPage);
        model.addAttribute("productsModel", productDTOList);
        model.addAttribute("isAdminModel", personeService.isAdmin());
        model.addAttribute("currentSortModel", sort);
        model.addAttribute("currentGroup", group);
        model.addAttribute("currentManufacturer", manufacturerName);
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
        productDTO.setProductURL(productService.createUrl(productDTO.getProductName()));}
        else{
            productDTO.setProductURL(productService.characterReplacementForUrl(productDTO.getProductURL()));
        };


        productValidator.validate(productDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupListModel", groupService.findAll());
            model.addAttribute("manufacturerListModel", manufacturerService.getAllManufacturers());
            productService.messageForQuantity(bindingResult);
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
            StringBuilder address = new StringBuilder(pictureFolderInProject);
            address.append(product.getMainPicture().getFileName());
            model.addAttribute("addressPicModel", address.toString());
        }
        else {
            model.addAttribute("addressPicModel");
        }
        ProductDTO oneProductDTO =  productService.convertToProductDTO(product);
        QuantityDTO quantityDTO = new QuantityDTO();
        quantityDTO.setQuantity(1);
        //oneProductDTO.setQuantity(1);
        model.addAttribute("isAdminModel", personeService.isAdmin());
        model.addAttribute("oneProductModel", oneProductDTO);
        model.addAttribute("quantityModel", quantityDTO);
        return "/product/oneProductPage";
    }




    @PatchMapping("/view/{productURL}")
    public String postViewProduct(HttpSession session, @PathVariable("productURL") String productUrl, Model model,
                                  @ModelAttribute("quantityModel") @Valid QuantityDTO quantityDTO,
                                  BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            ProductDTO productDTO = productService.convertToProductDTO(productService.getProductByProductUrl(productUrl).get());
            model.addAttribute("oneProductModel", productDTO);
            if(productDTO.getMainPicture()!=null){
                StringBuilder address = new StringBuilder(pictureFolderInProject);
                address.append(productDTO.getMainPicture().getFileName());
                model.addAttribute("addressPicModel", address.toString());

            }
            else {
                model.addAttribute("addressPicModel");
            }
            productService.messageForQuantity(bindingResult);


            return "/product/oneProductPage";
        }
        if (quantityDTO.getQuantity()>0){
            productService.addProductToCart(session, productUrl, quantityDTO.getQuantity());

            // return "redirect:/product/view/" + productUrl;
            return "/cart/successfullyPage";
        }
        else {
            return "/cart/errorPage";}

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
                                  @RequestParam("photo") MultipartFile photo,
                                  Model model) throws IOException {



        Product oldProduct = productService.getProductByProductUrl(productUrl).get();
        int id = oldProduct.getProductId();
        productDTO.setProductId(id);
        productService.setUrlForProduct(productDTO);
        productValidator.validate(productDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            productService.messageForQuantity(bindingResult);
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
        return "errorPage";
    }

    @DeleteMapping("/delete/{productURL}")
    public String deleteProduct(@PathVariable("productURL") String productURL) {
      Product product = productService.getProductByProductUrl(productURL).get();
      productService.deleteProductById(product.getProductId());
      return "redirect:/product";
    }

    @GetMapping("/group/{url}")
    public String getGroupPage(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                            @ModelAttribute("manufacturerListModel") ManufactuterDTOList manufactuterDTOList,
                               @PathVariable("url") String groupUrl,  @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
                               Model model,
            @RequestParam(value = "slist", defaultValue = "", required = false) String stringList   ) {

        List<ManufacturerDTO> parsedList = productService.parseStringToListManufacturers(stringList);
        int productPerPage = Integer.parseInt(productPerPageString);
        Group group = groupService.findByURL(groupUrl).get();
        List<ManufacturerDTO> manufacturerList = manufacturerService.getAllManufacturersByGroup(group.getGroupId()).stream().map
                (manufacturerService::convertToManufacturerDTO).toList();
        List<ProductDTO> productDTOList;
        if (manufactuterDTOList.getManufacturerDTOList().size()==0) {
            manufactuterDTOList = new ManufactuterDTOList();
            for (ManufacturerDTO manufacturerDTO : manufacturerList) {
                manufactuterDTOList.addManufacturerDTO(manufacturerDTO);
            }
        }
        if(groupService.isTrueGroup(parsedList)==true) {
            manufactuterDTOList = new ManufactuterDTOList();
            for (ManufacturerDTO manufacturerDTO : parsedList) {
                manufactuterDTOList.addManufacturerDTO(manufacturerDTO);
            }
        }
        productDTOList = productService.getSortedListProductsByManufacturer(page, productPerPage, group,sort, manufactuterDTOList.getManufacturerDTOList()).stream().map(product -> productService.convertToProductDTO(product)).toList();
        productService.addFolderName(productDTOList);
     //   List<String> sortListModel = productService.fillSortList();
        Set<String> sortListModel = productService.fillSortList(sort);
        String manufacturersParam = manufactuterDTOList.getManufacturerDTOList().stream()
                .map(manufacturerDTO -> "manufacturers=" + manufacturerDTO.getManufacturerName() + "&selected=" + manufacturerDTO.getSelceted())
                .collect(Collectors.joining(","));
        model.addAttribute("productsModel", productService.sortedProductDTO(productDTOList, sort));
       // model.addAttribute("numberOfPageModel", productService.listPage(productPerPage, productService.getAlLProductByGroup(group).size() ));
        model.addAttribute("numberOfPageModel", productService.listPage(productPerPage, productService.getProductByGroupAndManufacturers(group, manufactuterDTOList.getManufacturerDTOList()).size()));
        model.addAttribute("productPerPageModel", productPerPage);
        model.addAttribute("isAdminModel", personeService.isAdmin());
        model.addAttribute("groupURLModel", groupUrl);
        model.addAttribute("manufacturerListModel", manufactuterDTOList);
        model.addAttribute("sortListModel", sortListModel);
        model.addAttribute("sortModel", sort);
        model.addAttribute("stringManufacturerModel", manufacturersParam);
        return "/product/productsByGroupPage";
    }



}





