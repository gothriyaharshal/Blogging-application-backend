package com.blog.ServiceImpl;

import com.blog.Service.imageFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class imageFileimpl implements imageFile {
    @Override
    public String uploadingImage(String path, MultipartFile file) throws IOException {

          //getting file name
        String filename = file.getOriginalFilename();

        String string = UUID.randomUUID().toString();
        String concat = string.concat(filename.substring(filename.lastIndexOf(".")));


        //fullpath
        String filepath=path+"."+concat;
        return "";
    }

    @Override
    public InputStream getImage(String path, String fileName) throws FileNotFoundException {
        return null;
    }
}
