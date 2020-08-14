package com.example.converting.demo.service;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

public interface HelperService {

    String readFiles();

    void writeFiles(String line);

}
