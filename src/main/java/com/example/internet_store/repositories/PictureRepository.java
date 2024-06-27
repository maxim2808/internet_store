package com.example.internet_store.repositories;

import com.example.internet_store.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {
public List<Picture> findPictureByMainPictureListEmpty();
}
