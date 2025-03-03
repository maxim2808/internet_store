package com.example.internet_store.services;

import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.models.Picture;
import com.example.internet_store.models.Product;
import com.example.internet_store.repositories.PictureRepository;
import com.example.internet_store.repositories.ProductRepositories;
import org.springframework.beans.factory.annotation.Autowired;
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
@Transactional(readOnly = true)
public class PictureService {
    final PictureRepository pictureRepository;
   // final ProductService productService;
    private final ProductRepositories productRepositories;

    @Autowired
    public PictureService(PictureRepository pictureRepository, ProductRepositories productRepositories) {
        this.pictureRepository = pictureRepository;
      //  this.productService = productService;
        this.productRepositories = productRepositories;
    }

//    public void savePicture(Picture picture) {
//        pictureRepository.save(picture);
//    }



//@Transactional
//    public void receiveImage(MultipartFile image, int id) {
//        if (!image.isEmpty()) {
//            try {
//                // Сохранение загруженного файла на компьютере
//                String uploadDir = "C:\\Users\\max\\IdeaProjects\\internet_store\\static\\download"; // Замените на путь к папке, куда вы хотите сохранить файл
//                String fileName = Integer.toString(id) + "-main.jpg";
//                System.out.println(fileName);
//                File uploadFile = new File(uploadDir, fileName);
//                if (uploadFile.exists()){
//                    System.out.println("Такой файл уже существует");
//                }
//                else
//                FileCopyUtils.copy(image.getBytes(), uploadFile);
//                Picture picture = new Picture();
//                Product product = productService.getProductById(id).get();
//                picture.setFileName(fileName);
//                savePicture(picture);
//
//                picture.setProductList(new ArrayList<Product>(Collections.singletonList(product)));
//                product.setPictureList(new ArrayList<Picture>(Collections.singletonList(picture)));
//                product.setMainPicture(picture);
//                productService.editProduct(product, product.getGroup(), product.getManufacturer(), id); //not effective
//              //  product.setPictureList(new);
//
//
//            } catch (IOException e) {
//                // Обработка ошибки сохранения файла
//                e.printStackTrace();
//            }
//
//
//
//        }
//        else {
//            System.out.println("Нет фото");
//        }
//    }

    public String getFileName(int id) {
        return pictureRepository.findById(id).get().getFileName();
    }


    public Picture getMainPicture(Product product) {
        return product.getMainPicture();
    }

    public Picture getPictureById(int id) {
        return pictureRepository.findById(id).get();
    }

public void picSerSave(Picture picture) {
        pictureRepository.save(picture);
}

public List<Picture> findRedundantPictures() {
        return pictureRepository.findPictureByMainPictureListEmpty();
}

@Transactional
public void deletePicture(Picture picture) {
        pictureRepository.delete(picture);
}




}
