package com.example.internet_store.services;

import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.models.Picture;
import com.example.internet_store.models.Product;
import com.example.internet_store.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReceivePictureService {
    final PictureRepository pictureRepository;
    final ProductService productService;
    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;
    private final PictureService pictureService;
    @Value("${pictureFolder}")
    String firstPartOfPath;
    @Autowired
    public ReceivePictureService(PictureRepository pictureRepository, ProductService productService, DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration, PictureService pictureService) {
        this.pictureRepository = pictureRepository;
        this.productService = productService;
        this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
        this.pictureService = pictureService;
    }


    public void savePicture(Picture picture) {
        pictureRepository.save(picture);
    }



    @Transactional
    public void receiveImage(MultipartFile image, int id) {
        if (!image.isEmpty()) {
            System.out.println("receive ");
            try {
//                String uploadDir = "C:\\Users\\max\\IdeaProjects\\internet_store\\static\\download";
                String uploadDir = firstPartOfPath;
                String fileName = image.getOriginalFilename();
              //  String fileName = Integer.toString(id) + "-main.jpg";
                System.out.println(fileName);
               File uploadFile = new File(uploadDir, fileName);
                System.out.println("!!!!!!!!Файл существует " + uploadFile.exists());
                if (uploadFile.exists()){
                    String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                    String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
                  //  System.out.println("nameWithoutExtension " + nameWithoutExtension);
                  //  System.out.println("fileExtension " + fileExtension);

                    int i = 0;
                    while (uploadFile.exists()){
                        System.out.println("while is started");
                        i++;
                        StringBuilder sbFileName = new StringBuilder(nameWithoutExtension);
                        sbFileName.append("-v");
                        sbFileName.append(String.valueOf(i));
                        sbFileName.append(fileExtension);
                        fileName = sbFileName.toString();
                        uploadFile = new File(uploadDir, fileName);
                      //  System.out.println("File name: " + sbFileName.toString());

                    }
                  //  System.out.println("Такой файл уже существует");

                }


                    FileCopyUtils.copy(image.getBytes(), uploadFile);
                Picture picture = new Picture();
                Product product = productService.getProductById(id).get();
                picture.setFileName(fileName);
                savePicture(picture);
                picture.setMainPictureList(new ArrayList<Product>(Collections.singletonList(product)));
               // picture.setProductList(new ArrayList<Product>(Collections.singletonList(product)));
             //   product.setPictureList(new ArrayList<Picture>(Collections.singletonList(picture)));
                product.setMainPicture(picture);
                productService.editProduct(product, product.getGroup(), product.getManufacturer(),  id); //not effective


            } catch (IOException e) {
                // Обработка ошибки сохранения файла
                e.printStackTrace();
                // return "redirect:/product/error/upload";
            }
        }
        else {
          //  System.out.println("Нет фото");
        }
    }

    public int findPictureByProductName(String productName) {
        if(productService.getProductByName(productName).isPresent()){
        Product product = productService.getProductByName(productName).get();
        Picture picture = product.getMainPicture();
        return picture.getPictureId();
    }
        return 0;
}

    public Picture findPictureByProductNamePicture(String productName) {
        if(productService.getProductByName(productName).isPresent()){
            Product product = productService.getProductByName(productName).get();
            Picture picture = product.getMainPicture();
            return picture;
        }
        return null;
    }


    public void setSimilarPicture(MultipartFile photo, ProductDTO productDTO, Product product){

        if(photo.isEmpty()&&productDTO.getSimilarProductName()!=null){

            Picture picture = findPictureByProductNamePicture(productDTO.getSimilarProductName());
            if(picture!=null){
                product.setMainPicture(picture);
            }
        }

    }



    public void deletePictureAndFile(Picture picture) {
        String secondPartOfPath = picture.getFileName();
        StringBuilder fileName = new StringBuilder(firstPartOfPath);
        fileName.append(secondPartOfPath);
      //  System.out.println("fullname " + fileName.toString());
        File file = new File(fileName.toString());
        if (file.exists()) {
           // System.out.println("EXIST!!!!!!!!!!!!!!!!!!!!!!");
        }
            List<Product> listProduct = picture.getMainPictureList();
            for(Product product:listProduct){
                product.setMainPicture(null);
                productService.editProduct (product, product.getGroup(), product.getManufacturer(), product.getProductId());
            }
      //  System.out.println("File name" + file.getName());
      //  System.out.println("file is exist " + file.exists());
            file.delete();
            pictureService.deletePicture(picture);
        //System.out.println("File is exists: " + file.exists());
    }


}
