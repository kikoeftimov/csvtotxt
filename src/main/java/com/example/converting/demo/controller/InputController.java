package com.example.converting.demo.controller;

import com.example.converting.demo.service.HelperService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
@RequestMapping("/")
public class InputController {

    private final HelperService helperService;

    public InputController(HelperService helperService) {
        this.helperService = helperService;
    }

    @GetMapping
    public String getHomePage(){
        return "data";
    }

    @PostMapping("/upload")
    public String addData(@RequestParam("file") MultipartFile file, Model model) throws IOException {

        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            File alldata = helperService.readFiles(file);
            model.addAttribute("datalink", alldata.getName());
            return "data";
        } catch (Exception e){
            e.printStackTrace();
        }
           return "redirect:/";
    }

    @GetMapping("/download")
    public void getLogFile(HttpSession session, HttpServletResponse response) throws Exception {
        try {
            String filePathToBeServed = "converted.txt";
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
