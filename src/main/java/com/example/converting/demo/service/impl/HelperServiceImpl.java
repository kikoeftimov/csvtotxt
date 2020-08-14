package com.example.converting.demo.service.impl;

import com.example.converting.demo.service.HelperService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class HelperServiceImpl implements HelperService {

    @Override
    public String readFiles() {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("C:\\Users\\User\\Downloads\\thisone.csv"))){

            String DELIMITER = ";";

            String line;

            while ((line = bufferedReader.readLine()) != null){
                String[] columns = line.split(DELIMITER);

                writeFiles(String.join("#", columns));
                sb.append(String.join("#", columns)).append("\n");
                //System.out.println(String.join("#", columns));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    public void writeFiles(String line) {

        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter("C:\\Users\\User\\Downloads\\newtext.txt", true);
            bw = new BufferedWriter(fw);
            bw.write(line);
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            try {
                if(bw!= null){
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
