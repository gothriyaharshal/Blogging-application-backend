package com.blog.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface imageFile {

    public String uploadingImage(String path , MultipartFile file) throws IOException;
    public InputStream getImage(String path,String fileName) throws FileNotFoundException;

}
