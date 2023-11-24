package com.example.lthdt.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {
    public List<String> getListImageOfUser(long userId);

    public void deleteImage(String uploadDir, String filename);
}
