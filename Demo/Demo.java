package Demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Demo {
    static String[][] getTokens(String[] input) {
        String[][] output = new String[input.length][];
        for(int i=0; i<output.length; i++) output[i] = input[i].replaceAll("\t", "\s").split("\s+");
        return output;
    }
    static String[][] getTokens(String input) {
        String[] tmp = input.split("\n+");
        String[][] output = new String[tmp.length][];
        for(int i=0; i<output.length; i++) output[i] = tmp[i].replaceAll("\t", "\s").split("\s+");
        return output;
    }

    static boolean judge(String path[], String[][] answers, String[][] outputs) {
        // System.out.println();
        // System.out.println();
        // System.out.println();
        // System.out.println("paht = " + path[0]+path[1]);
        boolean check = true;
        if(answers.length != outputs.length) return false;
        for(int i=0; i<answers.length; i++) {
            if(answers[i].length != outputs[i].length) return false;
            for(int j=0; j<outputs[i].length; j++) {
                if(!answers[i][j].equals(outputs[i][j])) {
                    // System.out.println("not equal " + outputs[i][j] + "  |  " + answers[i][j]);
                    check = false;
                }
            }
        }
        // if(check == true) return true;
        // for(int i=0; i<answers.length; i++) {
        //     for(int j=0; j<answers[i].length; j++) {
        //         System.out.print("_" + answers[i][j] + "_");
        //     }
        //     System.out.println();
        //     for(int j=0; j<outputs[i].length; j++) {
        //         System.out.print("_" + outputs[i][j] + "_");
        //     }
        //     System.out.println();
        // }
        return check;
    }

    static void run_Scanner(){
        try {
            ProcessBuilder builder = new ProcessBuilder("javac", "src_Scanner/Final_Scanner.java");
            Process process = builder.start();
            int exitCode = process.waitFor();
            System.out.println("javac：" + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            ProcessBuilder builder = new ProcessBuilder("java", "src_Scanner/Final_Scanner");
            Process process = builder.start();
            int exitCode = process.waitFor();
            System.out.println("java：" + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        String[][][] paths = Reader.getFilePathList("data_0");
        String[][] answers = Reader.getFiles("data_1");
        String[][] outputs = Reader.getFiles("out_data_0");
        for(int i=0; i<paths.length; i++) {
            // System.out.println(paths[i][0][0]);
            boolean equal = true;
            for(int j=0; j<paths[i].length; j++) {
                equal = judge(paths[i][j], getTokens(answers[i][j]), getTokens(outputs[i][j]));
                System.out.println(equal);
                if(!equal) System.out.println(paths[i][j][0] + paths[i][j][1]);
                // if(!equal) break;
            }
            // if(!equal) break;
        }
    }
    public static void main(String[] args) {
        run_Scanner();
    }
}