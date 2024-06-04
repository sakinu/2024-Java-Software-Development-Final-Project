package src_Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    static String[][] read() {
        String filePath = "src_Parser/test.cpp";
        // String filePath = "data_1/subtask01-helloworld/testcase01.out";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.isEmpty() || line.charAt(0) == '/' && line.charAt(1) == '/') continue;
                lines.add(line);
            }
            String[][] output = new String[lines.size()][];
            for(int i=0;i<output.length;i++) {
                output[i] = lines.get(i).split("\s+");
            }
            return output;
        } catch (IOException e) {
            System.out.println("無法讀取檔案：" + e.getMessage());
        }
        return null;
    }
}