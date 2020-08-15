package com.example.converting.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface HelperService {

    String readFiles(MultipartFile file);

    void writeFiles(String line);
}
