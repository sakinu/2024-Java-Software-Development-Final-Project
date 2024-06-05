package Demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Demo {
    static String[][][] paths = Reader.getFilePathList("data_0");
    static String[][] data_0 = Reader.getFiles("data_0");
    static String[][] data_1 = Reader.getFiles("data_1");
    static String[][] data_2 = Reader.getFiles("data_2");
    static String[][] data_3 = Reader.getFiles("data_3");
    // String[][] data_3 = Reader.getFiles("data_3");

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

    static void compJava(String javaPath) {
        try {
            ProcessBuilder builder = new ProcessBuilder("javac", javaPath+".java");
            Process process = builder.start();
            int exitCode = process.waitFor();
            // System.out.println("javac：" + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void runJava(String javaPath, String folderPath, String filePath) {
        try {
            ProcessBuilder builder = new ProcessBuilder("java", javaPath, folderPath, filePath);
            Process process = builder.start();
            int exitCode = process.waitFor();
            // System.out.println("java：" + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void run_Scanner(){
        compJava("src_Scanner/Final_Scanner");
        String[][] outputs = Reader.getFiles("out_data_0");
        for(int i=0; i<paths.length; i++) {
            System.out.println(paths[i][0][0].substring(7));
            boolean equal = true;
            for(int j=0; j<paths[i].length; j++) {
                runJava("src_Scanner/Final_Scanner", paths[i][j][0], paths[i][j][1]);
                equal = judge(paths[i][j], getTokens(data_1[i][j]), getTokens(outputs[i][j]));
                System.out.printf("%-10b%s\n", equal, paths[i][j][1]);
            }
            System.out.println();
        }
    }

    static void run_Parser(){
        compJava("src_Parser/Final_Parser");
        String[][] outputs = Reader.getFiles("out_data_0");
        for(int i=0; i<paths.length; i++) {
            System.out.println(paths[i][0][0].substring(7));
            boolean equal = true;
            for(int j=0; j<paths[i].length; j++) {
                String folder = "out_data_0" + paths[i][j][0].substring(6);
                String file = paths[i][j][1];
                System.out.println(folder+file);
                runJava("src_Parser/Final_Parser", folder, file);
                equal = judge(paths[i][j], getTokens(data_3[i][j]), getTokens(outputs[i][j]));
                // System.out.printf("%-10b%s\n", equal, file);
            }
            System.out.println();
        }
    }

    static void run_Compiler(){
        compJava("src_Compiler/Final_Compiler");
        String[][] outputs = Reader.getFiles("out_data_2");
        for(int i=0; i<paths.length; i++) {
            System.out.println(paths[i][0][0].substring(7));
            boolean equal = true;
            for(int j=0; j<paths[i].length; j++) {
                String folder = "data_2" + paths[i][j][0].substring(6);
                String file = paths[i][j][1].substring(0,paths[i][j][1].length()-4) + ".out";
                System.out.println(folder+file);
                runJava("src_Compiler/Final_Compiler", folder, file);
                equal = judge(paths[i][j], getTokens(data_3[i][j]), getTokens(outputs[i][j]));
                // System.out.printf("%-10b%s\n", equal, file);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        run_Scanner();
        run_Parser();
        run_Compiler();
    }
}