package com.blogapp.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogapp.services.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException{

        // File Name
        String name=file.getOriginalFilename();

        String randomID=UUID.randomUUID().toString();
        String fileName=randomID.concat(name.substring(name.lastIndexOf(".")));

        // Full Path 
        String filePath=path+File.separator+fileName;

        // Create folder if not created 
        File f=new File(filePath);
        if(!f.exists()){
            f.mkdir();
        }

        Files.copy(file.getInputStream(),Paths.get(filePath));
        return name;


    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        String fullPath=path+File.separator+fileName;
        InputStream is=new FileInputStream(fullPath);
        return is;
    }
    
}
