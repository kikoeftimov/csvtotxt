package com.example.converting.demo.service.impl;

import com.example.converting.demo.service.HelperService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class HelperServiceImpl implements HelperService {

    @Override
    public File readFiles(MultipartFile file) throws IOException {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "windows-1251"))){
            String DELIMITER = ";";
            String line;
            boolean flag = false;
            int counter = 0;
            while ((line = bufferedReader.readLine()) != null){

                for (int i = 0; i < line.length(); i++) {
                    if(line.charAt(i) == '\"'){
                        counter++;
                    }
                }

                if(counter==0 && flag){
                    sb.append(line).append("\n");
                }

                if(counter == 0 && !flag){
                    String[] columns = line.split(DELIMITER); // ne sodrzi / i pocnuva so broj(id)
                    sb.append(String.join("#", columns)).append("\n");
                }

                if(counter % 2 == 0 && counter != 0){
                    List<String> result = new ArrayList<String>();
                    int start = 0;
                    boolean inQuotes = false;
                    for (int current = 0; current < line.length(); current++) {
                        if (line.charAt(current) == '\"')
                            inQuotes = !inQuotes; // toggle state
                        boolean atLastChar = (current == line.length() - 1);
                        if(atLastChar)
                                result.add(line.substring(start));
                        else if (line.charAt(current) == ';' && !inQuotes) {
                                result.add(line.substring(start, current));
                                start = current + 1;
                            }
                    }
                    sb.append(String.join("#", result)).append("\n");
                }

                if(counter % 2 == 1 && !flag){
                    List<String> result = new ArrayList<String>();
                    int start = 0;
                    boolean inQuotes = false;
                    for (int current = 0; current < line.length(); current++) {
                        if (line.charAt(current) == '\"')
                            inQuotes = !inQuotes; // toggle state
                        boolean atLastChar = (current == line.length() - 1);
                        if(atLastChar)
                            result.add(line.substring(start));
                        else if (line.charAt(current) == ';' && !inQuotes) {
                            result.add(line.substring(start, current));
                            start = current + 1;
                        }
                    }
                    sb.append(String.join("#", result)).append("\n");
                    flag=!flag;
                }

                else if(counter % 2 == 1 && flag){
                    List<String> result = new ArrayList<String>();
                    int end = line.length();
                    boolean inQuotes = false;
                    for (int current = line.length()-1; current >= 0; current--) {
                        if (line.charAt(current) == '\"') {
                            inQuotes = !inQuotes; // toggle state
                        }
                        int index = line.indexOf("\"");
                        boolean atFirstChar = (current == 0);
                        if(atFirstChar)
                            result.add(line.substring(current, index));
                        else if (line.charAt(current) == ';' && !inQuotes) {
                            result.add(line.substring(current+1, end));
                            end = current - 1;
                        }
                    }
                    Collections.reverse(result);
                    sb.append(String.join("#", result)).append("\n");
                    flag=!flag;
                }

                counter = 0;

//                if(line.contains("\"") && (Character.isDigit(line.charAt(0)) || !Character.isDigit(line.charAt(0)))){
//
//                    List<String> result = new ArrayList<String>();
//                    int start = 0;
//                    boolean inQuotes = false;
//                    for (int current = 0; current < line.length(); current++) {
//                        if (line.charAt(current) == '\"')
//                            inQuotes = !inQuotes; // toggle state
//                        boolean atLastChar = (current == line.length() - 1);
//                        if(atLastChar)
//                                result.add(line.substring(start));
//                        else if (line.charAt(current) == ';' && !inQuotes) {
//                                result.add(line.substring(start, current));
//                                start = current + 1;
//                            }
//                    }
//                    sb.append(String.join("#", result)).append("\n");
//                }
//
//                else if(!line.contains("\"") && !Character.isDigit(line.charAt(0))){ //ne sodrzi / i ne pocnuva so broj
//                    sb.append(line).append("\n");
//                }
//                else{
//                    String[] columns = line.split(DELIMITER); // ne sodrzi / i pocnuva so broj(id)
//                    sb.append(String.join("#", columns)).append("\n");
//                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        String filename = file.getOriginalFilename();
        String fileNameWithOutExt = filename.replaceFirst("[.][^.]+$", "");

        File newFile = new File(!fileNameWithOutExt.equals("") ? (fileNameWithOutExt + ".txt") : null);
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(newFile));
            writer.write(sb.toString());
        } finally {
            if (writer != null) writer.close();
        }
        return newFile;
    }
}
