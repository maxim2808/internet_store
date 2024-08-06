package com.example.internet_store.services;

import com.example.internet_store.dto.ManufacturerDTO;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public int getSearchProductsSize(String keyword) {
        return productRepositories.findProductByProductNameStartingWith(keyword).size();
}


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


    public List<Product> getAllProductsByGroup(int page, int productPerPage, Group group){
        if (productPerPage>=1&&page>=1){
            int pageMinusOne = page - 1;
            return productRepositories.findByGroup(group, PageRequest.of(pageMinusOne, productPerPage)).getContent();}
      //  else return productRepositories.findAll();
        else return productRepositories.findByGroup(group);

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


    public List<ProductDTO> sortedProductDTO(List<ProductDTO> productList, String sorted) {
        List<ProductDTO> newProductList = new ArrayList<>(productList);
        if (sorted.equals("Цена по возрастанию")){
            newProductList.sort(Comparator.comparingDouble(ProductDTO::getPrice));
        }
        if (sorted.equals("Цена по убыванию")) {
            newProductList.sort(Comparator.comparingDouble(ProductDTO::getPrice).reversed());
        }
        if (sorted.equals("Название(А-Я)")){
            newProductList.sort(Comparator.comparing(ProductDTO::getProductName));
        }

        if (sorted.equals("Название(Я-А)")){
            newProductList.sort(Comparator.comparing(ProductDTO::getProductName).reversed());
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

    public List<Integer> listPage(int productsPerPage, int size) {
        if (productsPerPage> 0) {
            int sizeList = (int) Math.ceil((double)size / productsPerPage);
            List<Integer> numberList = new ArrayList<>();
            for (int i = 0; i < sizeList; i++) {
                numberList.add(i+1);
            }
           // System.out.println(numberList);
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
//        for (ProductDTO pr : shoppingCart.getProducts()) {
//            System.out.println(pr.getProductName() + " count: " + pr.getQuantity());
//        }
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

    public List<Product> getAlLProductByGroup(Group group){
        return productRepositories.findByGroup(group);
    }
    public List<Product> getProductByGroupAndManufacturers(
            //int page, int productPerPage,
                                                           Group group, List<ManufacturerDTO> manufacturerList){

        List<Manufacturer> trueList = new ArrayList<>();
        for (ManufacturerDTO m : manufacturerList) {
         //  System.out.println(m.getManufacurerId() + " " + m.getManufacturerName()+ " " + m.getSelceted() );
            if(m.getSelceted()==true)

                trueList.add(manufacturerService.getManufacturerByName(m.getManufacturerName()).get());
        }
        if (trueList.size() == 0) {
            for (ManufacturerDTO m : manufacturerList) {
                trueList.add(manufacturerService.getManufacturerByName(m.getManufacturerName()).get());
            }

        }


        return productRepositories.findProductByGroupAndManufacturerIn(group, trueList);
    }

    public Set <String> fillSortList(String sort){
        Set <String> sortList = new LinkedHashSet<>();
        if (sort!=null&&!sort.isEmpty()){sortList.add(sort);}
        sortList.add("Название(А-Я)");
        sortList.add("Название(Я-А)");
        sortList.add("Цена по возрастанию");
        sortList.add("Цена по убыванию");

        return sortList;
    }



    public Sort getSort(String sorted){
        Sort sort;
        if (sorted.equals("Цена по возрастанию")){
            sort = Sort.by(Sort.Direction.ASC, "price");
        }
        else if (sorted.equals("Цена по убыванию")) {
            sort = Sort.by(Sort.Direction.DESC, "price");
        }
        else if (sorted.equals("Название(Я-А)")){
            sort = Sort.by(Sort.Direction.DESC, "productName");
        }
        else{
            sort = Sort.by(Sort.Direction.ASC, "productName");}
        return sort;


    }

    public List <Product> getSortedListProducts(int page, int productPerPage, String sorted,  String groupName, String manufacturerName, boolean searchName){
    Sort sort = getSort(sorted);
        if (productPerPage>=1&&page>=1){

            int pageMinusOne = page - 1;
            Pageable pageable = PageRequest.of(pageMinusOne, productPerPage, sort);

        if ((groupName==null||groupName.equals("Все группы"))&&(manufacturerName==null||manufacturerName.equals("Все производители")))
       return productRepositories.findAll(pageable).getContent();
        else if ((groupName!=null||!groupName.equals("Все группы"))&&(manufacturerName==null||manufacturerName.equals("Все производители")))
        {           Group group = groupService.findByGroupName(groupName).get();
               return productRepositories.findByGroup(group, pageable).getContent();
        }
        else if ((groupName==null||groupName.equals("Все группы"))&&(manufacturerName!=null||!manufacturerName.equals("Все производители"))){
            Manufacturer manufacturer = manufacturerService.getManufacturerByName(manufacturerName).get();
            return productRepositories.findByManufacturer(manufacturer, pageable).getContent();
            }
        else if ((groupName!=null||!groupName.equals("Все группы"))&&(manufacturerName!=null||!manufacturerName.equals("Все производители")))
        {
            Group group = groupService.findByGroupName(groupName).get();
            Manufacturer manufacturer = manufacturerService.getManufacturerByName(manufacturerName).get();
            return productRepositories.findProductByGroupAndManufacturer(group, manufacturer, pageable).getContent();
        }

        }

        return productRepositories.findAll();

    }

    public int getCountProducts( String groupName, String manufacturerName){


            if ((groupName==null||groupName.equals("Все группы"))&&(manufacturerName==null||manufacturerName.equals("Все производители")))
                return productRepositories.findAll().size();
            else if ((groupName!=null||!groupName.equals("Все группы"))&&(manufacturerName==null||manufacturerName.equals("Все производители")))
            {           Group group = groupService.findByGroupName(groupName).get();
                return productRepositories.findByGroup(group).size();
            }
            else if ((groupName==null||groupName.equals("Все группы"))&&(manufacturerName!=null||!manufacturerName.equals("Все производители"))){
                Manufacturer manufacturer = manufacturerService.getManufacturerByName(manufacturerName).get();
                return productRepositories.findByManufacturer(manufacturer).size();
            }
            else if ((groupName!=null||!groupName.equals("Все группы"))&&(manufacturerName!=null||!manufacturerName.equals("Все производители")))
            {
                Group group = groupService.findByGroupName(groupName).get();
                Manufacturer manufacturer = manufacturerService.getManufacturerByName(manufacturerName).get();
                return productRepositories.findProductByGroupAndManufacturer(group, manufacturer).size();
            }



        return productRepositories.findAll().size();

    }




    public List <Product> getSortedListProductsByManufacturer(int page, int productPerPage, Group group, String sorted, List<ManufacturerDTO> manufacturerList){
        Sort sort = getSort(sorted);

        List<Manufacturer> trueList = new ArrayList<>();
        for (ManufacturerDTO m : manufacturerList) {
            // System.out.println(m.getManufacurerId() + " " + m.getManufacturerName()+ " " + m.getSelceted() );
            if(m.getSelceted()==true)

                trueList.add(manufacturerService.getManufacturerByName(m.getManufacturerName()).get());
        }
        if (trueList.size() == 0) {
            for (ManufacturerDTO m : manufacturerList) {
                trueList.add(manufacturerService.getManufacturerByName(m.getManufacturerName()).get());

            }
        }

        if (productPerPage>=1&&page>=1){

            int pageMinusOne = page - 1;
    Pageable pageable = PageRequest.of(pageMinusOne, productPerPage, sort);
            return productRepositories.findProductByGroupAndManufacturerIn(group, trueList, pageable).getContent();


        }
        else {return productRepositories.findProductByGroupAndManufacturerIn(group, trueList);}


    }

    public List<ManufacturerDTO> parseStringToListManufacturers(String stringManufacturers){
                Pattern pattern = Pattern.compile("manufacturers=(([^&]+))&selected=(([^,]+))");
    Matcher matcher = pattern.matcher(stringManufacturers);
    List<ManufacturerDTO> manufacturerDTOList = new ArrayList<>();
    while (matcher.find()) {
        ManufacturerDTO manucafrurer = new ManufacturerDTO();
        manucafrurer.setManufacturerName(matcher.group(1));

        manucafrurer.setSelceted(Boolean.valueOf(matcher.group(3)));
        manufacturerDTOList.add(manucafrurer);
    }
    return manufacturerDTOList;

    }





}
