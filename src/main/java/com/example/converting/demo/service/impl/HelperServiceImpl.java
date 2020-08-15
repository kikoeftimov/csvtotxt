package com.example.converting.demo.service.impl;

import com.example.converting.demo.service.HelperService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;


@Service
public class HelperServiceImpl implements HelperService {

    @Override
    public String readFiles(MultipartFile file) {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String DELIMITER = ";";
            String line;

            while ((line = bufferedReader.readLine()) != null){
                String[] columns = line.split(DELIMITER);

                writeFiles(String.join("#", columns));
                sb.append(String.join("#", columns)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    @Override
    public void writeFiles(String line) {

        BufferedWriter bw = null;
        File file = new File("converted.txt");
        if(file.length() > 0){
            file.delete();
        }
        else{
            try {
                FileWriter fw = new FileWriter(file, true);
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
                        file.delete();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
