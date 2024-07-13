package com.example.internet_store.services;

import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.models.*;
import com.example.internet_store.repositories.ProductRepositories;
import com.ibm.icu.text.Transliterator;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class ProductService {
    final ProductRepositories productRepositories;
    final GroupService groupService;
    final ManufacturerService manufacturerService;
    final ModelMapper modelMapper;
    final PictureService pictureService;
    @Value("${pictureFolderInProject}")
    private String pictureFolderInProject;
  //  final ReceivePictureService receivePictureService;


    @Autowired
    public ProductService(ProductRepositories productRepositories, GroupService groupService, ManufacturerService manufacturerService, ModelMapper modelMapper, PictureService pictureService
                          ) {
        this.productRepositories = productRepositories;
        this.groupService = groupService;
        this.manufacturerService = manufacturerService;
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
      //  this.receivePictureService = receivePictureService;

        ;
    }

    public List<Product> getAllProducts() {
       return productRepositories.findAll();
    }

    public List<Product> searchProducts(int page, int productPerPage, String keyword) {
        if (productPerPage>=1&&page>=1) {
            int pageMinusOne = page - 1;

            Pageable pageable = PageRequest.of(pageMinusOne, productPerPage);
            return productRepositories.findProductByProductNameStartingWith(keyword, pageable).getContent();
    }
            else return productRepositories.findProductByProductNameStartingWith(keyword);
    }


//
//    public List<Product> getAllProducts(int page, int productPerPage, String groupName, String searchName) {
//        if (groupName==null||groupName.equals("Все группы")){
//            if (searchName==null||searchName.equals("")){
//
//        if (productPerPage>=1&&page>=1){
//            int pageMinusOne = page - 1;
//        return productRepositories.findAll(PageRequest.of(pageMinusOne, productPerPage)).getContent();}
//        else return productRepositories.findAll();}
//
//        else {
//                if (productPerPage>=1&&page>=1) {
//                    int pageMinusOne = page - 1;
//
//                    Pageable pageable = PageRequest.of(pageMinusOne, productPerPage);
//                    Page<Product> pageList = productRepositories.findProductByProductNameStartingWith(searchName, pageable);
//                System.out.println("Search name " + searchName);
//
//                 return pageList.getContent();}
//        return productRepositories.findAll();}}
//        else {
//            Group group = groupService.findByGroupName(groupName).get();
//            if (searchName==null||searchName.equals("")){
//
//            if (productPerPage>=1&&page>=1) {
//                int pageMinusOne = page - 1;
//
//                Pageable pageable = PageRequest.of(pageMinusOne, productPerPage);
//                Page<Product> pageList = productRepositories.findByGroup(group, pageable);
//                return pageList.getContent();
//
//            }
//            else return productRepositories.findByGroup(group);
//
//        }
//        else {
//                if (productPerPage>=1&&page>=1) {
//                int pageMinusOne = page - 1;
//
//                Pageable pageable = PageRequest.of(pageMinusOne, productPerPage);
//                Page<Product> pageList = productRepositories.findProductByProductNameStartingWithAndGroup(searchName, group, pageable);
//                System.out.println("Search name " + searchName);
//                return pageList.getContent();
//            }
//else return         productRepositories.findProductByProductNameStartingWithAndGroup(searchName,group );
//        }
//        }
//    }



    public List<Product> getAllProducts(int page, int productPerPage, String groupName, String manufacturerName, boolean searchName) {
        if ((groupName==null||groupName.equals("Все группы"))&&(manufacturerName==null||manufacturerName.equals("Все производители"))){
            if (productPerPage>=1&&page>=1){
                int pageMinusOne = page - 1;
                return productRepositories.findAll(PageRequest.of(pageMinusOne, productPerPage)).getContent();}
            else return productRepositories.findAll();}

        else if ((groupName!=null||!groupName.equals("Все группы"))&&(manufacturerName==null||manufacturerName.equals("Все производители"))){
            Group group = groupService.findByGroupName(groupName).get();
            if (productPerPage>=1&&page>=1){
                int pageMinusOne = page - 1;
                Pageable pageable = PageRequest.of(pageMinusOne, productPerPage);
                return productRepositories.findByGroup(group, pageable).getContent();}
            else return productRepositories.findByGroup(group);
        }


        else if ((groupName==null||groupName.equals("Все группы"))&&(manufacturerName!=null||!manufacturerName.equals("Все производители"))){
            Manufacturer manufacturer = manufacturerService.getManufacturerByName(manufacturerName).get();
            if (productPerPage>=1&&page>=1){
                int pageMinusOne = page - 1;
                Pageable pageable = PageRequest.of(pageMinusOne, productPerPage);
                return productRepositories.findByManufacturer(manufacturer, pageable).getContent();}
            else return productRepositories.findByManufacturer(manufacturer);
        }

        else if ((groupName!=null||!groupName.equals("Все группы"))&&(manufacturerName!=null||!manufacturerName.equals("Все производители"))){
            Group group = groupService.findByGroupName(groupName).get();
            Manufacturer manufacturer = manufacturerService.getManufacturerByName(manufacturerName).get();
            if (productPerPage>=1&&page>=1){
                int pageMinusOne = page - 1;
                Pageable pageable = PageRequest.of(pageMinusOne, productPerPage);
                return productRepositories.findProductByGroupAndManufacturer(group, manufacturer, pageable).getContent();}
            else {
                return productRepositories.findProductByGroupAndManufacturer(group, manufacturer);
            }

        }




        else {
            Group group = groupService.findByGroupName(groupName).get();
            if (productPerPage>=1&&page>=1){
                int pageMinusOne = page - 1;
                Pageable pageable = PageRequest.of(pageMinusOne, productPerPage);
                return productRepositories.findByGroup(group, pageable).getContent();
         }
            else return productRepositories.findByGroup(group);
        }
    }

    public List<Product> sortedProduct(List<Product> productList, String sorted) {
        List<Product> newProductList = new ArrayList<>(productList);
        if (sorted.equals("Цена по возрастанию")){
            newProductList.sort(Comparator.comparingDouble(Product::getPrice));
        }
        if (sorted.equals("Цена по убыванию")) {
            newProductList.sort(Comparator.comparingDouble(Product::getPrice).reversed());
        }
        if (sorted.equals("Название(А-Я)")){
            newProductList.sort(Comparator.comparing(Product::getProductName));
        }

        if (sorted.equals("Название(Я-А)")){
            newProductList.sort(Comparator.comparing(Product::getProductName).reversed());
        }
        return newProductList;
    }



    public List<Product> findPopularProducts(Boolean popular) {
      return   productRepositories.findProductByPopular(popular);
    }





    public Optional<Product> getProductById(int id) {
        return productRepositories.findById(id);
    }

    @Transactional
    public void saveProduct(Product product, Group group, Manufacturer manufacturer) {
        product.setRegistrationDate(new Date());
        product.setDiscount(0);
        product.setPopular(false);
        product.setProductGroup(groupService.findById(group.getGroupId()).get());
        product.setManufacturer(manufacturerService.findById(manufacturer.getManufacurerId()).get());
        productRepositories.save(product);
    }
    public Optional<Product> getProductByName(String name) {
      return   productRepositories.findByProductName(name);
    }


    @Transactional
    public void editProduct(Product receivedProduct, Group group, Manufacturer manufacturer,
                           int id) {
        receivedProduct.setProductGroup(groupService.findById(group.getGroupId()).get());
        receivedProduct.setManufacturer(manufacturerService.findById(manufacturer.getManufacurerId()).get());
        receivedProduct.setProductId(id);
        productRepositories.save(receivedProduct);
    }

//    @Transactional
//    public void editProduct(Product receivedProduct, Group group, Manufacturer manufacturer, List<Order> listOfOrders,
//                            int id) {
//        receivedProduct.setProductGroup(groupService.findById(group.getGroupId()).get());
//        receivedProduct.setManufacturer(manufacturerService.findById(manufacturer.getManufacurerId()).get());
//        receivedProduct.setProductId(id);
//        receivedProduct.setOrderList(listOfOrders);
//        productRepositories.save(receivedProduct);
//    }



    
    public Optional<Product> getProductByProductUrl(String productUrl) {
       return productRepositories.findByProductURL(productUrl);
    }

    public Product convertToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO convertToProductDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public String createUrl(String name){
        Transliterator transliterator = Transliterator.getInstance(("Russian-Latin/BGN"));
        String englishName = transliterator.transliterate(name);
       String urlWord= characterReplacementForUrl(englishName);
    return urlWord;

    }

    public String characterReplacementForUrl(String englishName) {
        String urlWord = englishName.toLowerCase()
                .replaceAll("[^a-zA-Z0-9-]", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");
        return urlWord;
    }

    public void enrichProductAfterEdit(Product newProduct, Product oldProduct) {
        newProduct.setRegistrationDate(oldProduct.getRegistrationDate());
        if(oldProduct.getMainPicture()!=null){
            newProduct.setMainPicture(oldProduct.getMainPicture());
        }
        else {newProduct.setMainPicture(null);};
    }

    public void setUrlForProduct(ProductDTO productDTO) {

        if (productDTO.getProductURL().isBlank()){
            productDTO.setProductURL(createUrl(productDTO.getProductName()));}
        else{
            productDTO.setProductURL(characterReplacementForUrl(productDTO.getProductURL()));
        };

    }

    public List<Integer> listPage(int productsPerPage) {
        if (productsPerPage> 0) {
            int sizeList = (int) Math.ceil((double)productRepositories.findAll().size() / productsPerPage);
            List<Integer> numberList = new ArrayList<>();
            for (int i = 0; i < sizeList; i++) {
                numberList.add(i+1);
            }
            System.out.println(numberList);
            return numberList;
        } else return null;
    }

    @Transactional
    public void deleteProductById(int id) {
        productRepositories.deleteById(id);
    }

    public void productCount(HttpSession session){
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (shoppingCart == null) {
            shoppingCart= new ShoppingCart();
            shoppingCart.setCount(0);
            session.setAttribute("shoppingCart", shoppingCart);

        }
        int count = shoppingCart.getCount();
        count++;
        shoppingCart.setCount(count);
    }

    public void addProductToCart(HttpSession session, String productUrl, int count) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (shoppingCart == null) {
            shoppingCart= new ShoppingCart();
            session.setAttribute("shoppingCart", shoppingCart);

        }

            Product product = getProductByProductUrl(productUrl).get();
            ProductDTO productDTO = convertToProductDTO(product);
            productDTO.setQuantity(count);
            shoppingCart.getProducts().add(productDTO);
        for (ProductDTO pr : shoppingCart.getProducts()) {
            System.out.println(pr.getProductName() + " count: " + pr.getQuantity());
        }
    }


    public void addFolderName(List<ProductDTO> productDTOList){
        for (ProductDTO product :productDTOList) {
            if(product.getMainPicture()!=null){
                StringBuilder address = new StringBuilder(pictureFolderInProject);
                address.append(product.getMainPicture().getFileName());
                product.setAddressPicture(address.toString());
            }
            else {
                product.setAddressPicture("");
            }
        }
    }

    public double totalPrice(List<ProductDTO> productDTOList){
        int totalPrice = 0;
        for (ProductDTO product : productDTOList) {
            totalPrice += product.getPrice()*product.getQuantity();
        }
        return totalPrice;
    }


    public void deleteProductWhereCountZeroOrLess(HttpSession session){
        if (session.getAttribute("shoppingCart") != null) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        shoppingCart.getProducts().removeIf(productDTO -> productDTO.getQuantity() <= 0);

    }
    }

    public void messageForQuantity(BindingResult bindingResult) {
        for (FieldError error : bindingResult.getFieldErrors()) {
            if (error.getField().equals("quantity") && error.getCode().equals("typeMismatch")) {

                FieldError newError = new FieldError(
                        error.getObjectName(),
                        error.getField(),
                        "Введите целое число для поля 'Количество'"
                );
                bindingResult.addError(newError);
            }

        }
    }

}
