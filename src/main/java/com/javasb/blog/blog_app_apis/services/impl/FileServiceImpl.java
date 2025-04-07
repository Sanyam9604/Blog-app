package com.javasb.blog.blog_app_apis.services.impl;

import com.javasb.blog.blog_app_apis.entities.Post;
import com.javasb.blog.blog_app_apis.exceptions.ResourceNotFoundException;
import com.javasb.blog.blog_app_apis.exceptions.UnsupportedFileTypeException;
import com.javasb.blog.blog_app_apis.repositories.PostRepository;
import com.javasb.blog.blog_app_apis.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // File name
        String fileName = file.getOriginalFilename();

        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

        if(!fileExtension.equals(".png") && !fileExtension.equals(".jpg")) {
            throw new UnsupportedFileTypeException(fileExtension, "Image", fileName);
        }

        // random name generator
        String randomId = UUID.randomUUID().toString();
        String fileName1 = randomId.concat(fileExtension);

        // Full path generation
        String filePath = path + File.separator + fileName1;

        // Create folder if not created
        File f = new File(path);
        if(!f.exists()) {
            f.mkdir();
        }

        // File copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;

        InputStream is = new FileInputStream(fullPath);

        return is;
    }
}
