package src_Parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {
    static void write(String[] path, List<String> output) {
    // System.out.println(Arrays.toString(path));
    String folderPath = path[0].replaceAll("data_0", "data_1");
    // File d = new File(folderPath);
    // d.mkdirs();
    try {
        String writePath =  folderPath + path[1];
        System.out.println(writePath);
        FileWriter fw = new FileWriter(writePath);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String s: output){
            bw.write(s);    // 寫入資料
            bw.newLine();   // 新增一行
        }
        bw.flush(); // 把記憶體資料寫進去
        bw.close();
    } catch (IOException e) {
        System.out.println("faild");
        e.printStackTrace();
    }
	}
}
