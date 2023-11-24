package com.example.lthdt.service.impl;

import com.example.lthdt.entity.Image;
import com.example.lthdt.exception.BadRequestException;
import com.example.lthdt.exception.InternalServerException;
import com.example.lthdt.repository.ImageRepository;
import com.example.lthdt.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<String> getListImageOfUser(long userId) {
        List<String> images = imageRepository.getListImageOfUser(userId);

        return images;
    }

    @Override
    @Transactional(rollbackFor = InternalServerException.class)
    public void deleteImage(String uploadDir, String filename) {
        String link = "/media/static/" + filename;
        Image img = imageRepository.findByLink(link);
        if (img == null) {
            throw new BadRequestException("File không tồn tại");
        }

        Integer inUse = imageRepository.checkImgInUse(link);
        if (inUse != null) {
            throw new BadRequestException("Ảnh đã được sử dụng không thể xóa");
        }

        imageRepository.delete(img);

        File file = new File(uploadDir + "/" + filename);
        if (file.exists()) {
            if (!file.delete()) {
                throw new InternalServerException("Lỗi khi xóa ảnh");
            }
        }
    }
}
