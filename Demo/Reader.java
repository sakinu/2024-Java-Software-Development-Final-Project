package Demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Reader {
    static String readALL(String filepath) {
        String inputString = "";
        try {
            inputString = Files.readString(Paths.get(filepath));
        }
        catch (IOException e) {
            System.err.println("無法讀取文件 " + filepath);
            e.printStackTrace();
        }
        return inputString;
    }

    static String[][][] getFilePathList(String folderPath){
        String [][][] fileList = null;			//fol file name/fol
        try{
            java.io.File folders = new java.io.File(folderPath);
            String[] folderList = folders.list();
            Arrays.sort(folderList);
            // for(int i=0;i<folderList.length;i++) System.out.println(folderList[i]);
            fileList = new String[folderList.length][][];
            for(int i = 0; i < folderList.length; i++){
                java.io.File files = new java.io.File(folderPath + "/" + folderList[i]);
                String[] fileNames = files.list();
                Arrays.sort(fileNames);
                fileList[i] = new String[fileNames.length][2];
                fileList[i] = new String[fileNames.length][2];
                for(int j=0; j<fileNames.length; j++) {	
                    fileList[i][j][0] = folderPath + "/" + folderList[i] + "/";
                    fileList[i][j][1] = fileNames[j];
                    // System.out.println(fileList[i][j][0] + "," + fileList[i][j][1]);
                }
            }
        }catch(Exception e){
            // System.out.println("'"+folderPath+"'此資料夾不存在");
        }
        return fileList;
    }

    static String[][] getFiles(String root){
        System.out.println("root = " + root);
        String [][] fileList = null;			//fol file name/fol
        try{
            java.io.File folders = new java.io.File(root);
            String[] folderList = folders.list();
            Arrays.sort(folderList);
            fileList = new String[folderList.length][];
            // System.out.println("lenth = " + folderList.length);
            for(int i = 0; i < folderList.length; i++){
                // System.out.println("folderList = " + folderList[i]);
                java.io.File files = new java.io.File(root + "/" + folderList[i]);
                String[] fileNames = files.list();
                Arrays.sort(fileNames);
                fileList[i] = new String[fileNames.length];
                fileList[i] = new String[fileNames.length];
                for(int j=0; j<fileNames.length; j++) {	
                    // System.out.println("path = " + root + "/" + folderList[i] + "/" + fileNames[j]);
                    fileList[i][j] = readALL(root + "/" + folderList[i] + "/" + fileNames[j]);
                }
            }
        }catch(Exception e){
            System.out.println("此資料夾不存在");
        }
        return fileList;
    }
}
