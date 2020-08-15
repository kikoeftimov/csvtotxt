package com.example.converting.demo.controller;

import com.example.converting.demo.service.HelperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
            String alldata = helperService.readFiles(file);
            model.addAttribute("alldata", alldata);
            model.addAttribute("title", file.getName());
            return "data";
        } catch (Exception ex){
            model.addAttribute("message", "error");
        }
           return "redirect:/";
    }
}
