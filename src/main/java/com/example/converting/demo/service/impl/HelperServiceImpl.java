package com.example.converting.demo.service.impl;

import com.example.converting.demo.service.HelperService;
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

            while ((line = bufferedReader.readLine()) != null){

                if(line.contains("\"")){

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
                else{
                    String[] columns = line.split(DELIMITER);
                    sb.append(String.join("#", columns)).append("\n");
                }

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
