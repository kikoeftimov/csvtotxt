package com.example.converting.demo.controller;

import com.example.converting.demo.service.DownloadService;
import com.example.converting.demo.service.HelperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.*;

@Controller
@RequestMapping("/")
public class InputController {

    private final HelperService helperService;
    private final DownloadService downloadService;

    public InputController(HelperService helperService, DownloadService downloadService) {
        this.helperService = helperService;
        this.downloadService = downloadService;
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

    @PostMapping(value = "/download")
    public void getLogFile(HttpSession session, HttpServletResponse response, String datalink) throws Exception {
        response.setHeader("datalink", datalink);
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        downloadService.getLogFile(session, response);
    }
}
