package com.example.converting.demo.controller;

import com.example.converting.demo.service.HelperService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

@Controller
@RequestMapping("/")
public class InputController {

    private final HelperService helperService;

    public InputController(HelperService helperService) {
        this.helperService = helperService;
    }

    @GetMapping
    public String getData(Model model){
//        String s = helperService.readFiles();
//        model.addAttribute("s", s);
        return "data";
    }

    @PostMapping("/upload")
    public String addData(@RequestParam("file") File file, Model model){
        if(!file.exists()){
            model.addAttribute("message", "Please select a CSV file to upload");
            model.addAttribute("status", false);
        }
        else{
            model.addAttribute("status", true);
//            String s = helperService.readFiles();
//            model.addAttribute("s", s);
//            model.addAttribute("file", file);
//            helperService.store(file);
        }
        return "redirect:/upload";
    }

    @GetMapping("/upload")
    public String giveMeData(Model model){
        String s = helperService.readFiles();
        model.addAttribute("s", s);
        // model.addAttribute("s", s);
        return "newpage";
    }


//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = helperService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }
}
