package com.example.converting.demo.service.impl;

import com.example.converting.demo.service.HelperService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Service
public class HelperServiceImpl implements HelperService {

    @Override
    public File readFiles(MultipartFile file) throws IOException {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String DELIMITER = ";";
            String line;

            while ((line = bufferedReader.readLine()) != null){
                String[] columns = line.split(DELIMITER);
                sb.append(String.join("#", columns)).append("\n");
            }
            System.out.println(sb.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }

        String filename = file.getOriginalFilename();
        String fileNameWithOutExt = filename.replaceFirst("[.][^.]+$", "");

        File newFile = new File(fileNameWithOutExt + ".txt");
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
