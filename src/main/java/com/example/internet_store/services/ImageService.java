package com.example.internet_store.services;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

    public void receiveImage(MultipartFile image, int id) {
        if (!image.isEmpty()) {
            try {
                // Сохранение загруженного файла на компьютере
                String uploadDir = "C:\\Users\\max\\IdeaProjects\\internet_store\\download"; // Замените на путь к папке, куда вы хотите сохранить файл
                //String fileName = image.getOriginalFilename();
                String fileName = Integer.toString(id) + "-main.jpg";
                System.out.println(fileName);
                File uploadFile = new File(uploadDir, fileName);
                if (uploadFile.exists()){
                    System.out.println("Такой файл уже существует");
                }
                else
                FileCopyUtils.copy(image.getBytes(), uploadFile);

            } catch (IOException e) {
                // Обработка ошибки сохранения файла
                e.printStackTrace();
               // return "redirect:/product/error/upload";
            }



        }
        else {
            System.out.println("Нет фото");
        }
    }



}
