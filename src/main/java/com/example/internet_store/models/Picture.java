package com.example.internet_store.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    int pictureId;
    @Column(name = "file_name")
    String fileName;
//
//    @ManyToMany(mappedBy = "pictureList")
//    List<Product> productList;

    @OneToMany(mappedBy = "mainPicture")
    List<Product> mainPictureList = new ArrayList<>();

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

//    public List<Product> getProductList() {
//        return productList;
//    }
//
//    public void setProductList(List<Product> productList) {
//        this.productList = productList;
//    }

    public List<Product> getMainPictureList() {
        return mainPictureList;
    }

    public void setMainPictureList(List<Product> mainPictureList) {
        this.mainPictureList = mainPictureList;
    }
}
