package com.example.converting.demo.service.impl;

import com.example.converting.demo.service.DownloadService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class DownloadServiceImpl implements DownloadService {

    @Override
    public void getLogFile(HttpSession session, HttpServletResponse response) throws Exception {
        try {
            String filePathToBeServed = response.getHeader("datalink");
            File fileToDownload = new File(filePathToBeServed);
            InputStream inputStream = new FileInputStream(fileToDownload);
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename="+ fileToDownload.getName());
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
