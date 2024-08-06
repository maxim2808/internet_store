package com.example.internet_store.controllers;

import com.example.internet_store.models.Picture;
import com.example.internet_store.services.PictureService;
import com.example.internet_store.services.ReceivePictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/picture")
public class PictureController {
    final PictureService pictureService;
    final ReceivePictureService receivePictureService;
    @Value("${pictureFolderInProject}")
    private String pictureFolderInProject;

    public PictureController(PictureService pictureService, ReceivePictureService receivePictureService) {
        this.pictureService = pictureService;
        this.receivePictureService = receivePictureService;
    }

//    @GetMapping("")
//    public String allPicture(Model model) {
//        model.addAttribute("pictureListModel", pictureService.findAllPictures());
//        return "picture";
//    }

    @GetMapping("/redundant")
    public String getRedundant(Model model) {
        List<Picture> redundantPictureList = pictureService.findRedundantPictures();
        model.addAttribute("redundantPicturesModel", redundantPictureList);
//        for(Picture picture : redundantPictureList) {
//            receivePictureService.deletePicture(picture);
//        }
        return "/picture/pictureWithoutProductPage";
    }

    @GetMapping("/view/{id}")
    public String viewPicture(Model model, @PathVariable ("id") int id) {
        Picture picture =  pictureService.getPictureById(id);
        model.addAttribute("pictureModel",picture);

        StringBuilder address = new StringBuilder(pictureFolderInProject);
        address.append(picture.getFileName());
      //  System.out.println(address);
        model.addAttribute("addressPicModel", address.toString());
        return "/picture/pictureViewPage";
    }


    @DeleteMapping("/view/{id}")
    public String deletePicture(@PathVariable("id") int id, @ModelAttribute ("pictureModel") Picture picture) {
     //   System.out.println("Start Delete picture");
      //  pictureService.deletePicture(picture);
        Picture picture1 = pictureService.getPictureById(id);
     //   System.out.println("Before delete " + picture1.getPictureId() + " id " + picture1.getFileName());
       receivePictureService.deletePictureAndFile(picture1);
        return "redirect:/picture/redundant";

    }
}
