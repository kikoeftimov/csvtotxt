package com.example.converting.demo.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

public interface HelperService {

    File readFiles(MultipartFile file) throws IOException;
}
