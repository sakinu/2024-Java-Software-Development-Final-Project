package src_Compiler;

import java.io.*;
import java.nio.file.*;

public class Reader {
    static String[] read(String[] argv) {
        String filePath = "test/test.out";
        // String filePath = "test/subtask01-helloworld/testcase01.out";
        // String filePath = "test/subtask02-comment/testcase01.out";
        // String filePath = "test/subtask03-precedenc/testcase02.out";
        // String filePath = "test/subtask09-function/testcase01.out";
        if(argv.length != 0) filePath = argv[0];
        String inputString = "";
        try {
            inputString = Files.readString(Paths.get(filePath));
        }
        catch (IOException e) {
            System.err.println("無法讀取文件 " + filePath);
            e.printStackTrace();
        }
        String[] input = inputString.split("\n");
        String[] output = new String[input.length+1];
        for(int i=0;i<input.length;i++) output[i+1] = input[i];
        output[0] = "";
        return output;
    }
}