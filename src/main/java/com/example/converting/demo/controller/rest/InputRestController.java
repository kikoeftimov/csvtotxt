package com.example.converting.demo.controller.rest;

import com.example.converting.demo.service.DownloadService;
import com.example.converting.demo.service.HelperService;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.io.*;

@RestController
@RequestMapping("/rest")
public class InputRestController {

    private final DownloadService downloadService;
    private final HelperService helperService;

    public InputRestController(DownloadService downloadService, HelperService helperService) {
        this.downloadService = downloadService;
        this.helperService = helperService;
    }


    @PostMapping("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    @Produces(MediaType.TEXT_PLAIN_VALUE)
    public File addData(@RequestParam("file") MultipartFile file) throws IOException {

        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            File alldata = helperService.readFiles(file);
            return alldata;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("/download")
    @Produces(MediaType.TEXT_PLAIN_VALUE)
    @Consumes(MediaType.TEXT_PLAIN_VALUE)
    public void getLogFile(HttpSession session, HttpServletResponse response, String datalink) throws Exception {
        response.setHeader("datalink", datalink);
        downloadService.getLogFile(session, response);
    }
}
