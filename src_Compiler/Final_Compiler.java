package src_Compiler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Final_Compiler {
    static String[] inputLines;

	private static String read(String filepath[]) {
		System.out.println(filepath[0] + filepath[1]);
		String inputString = "";
		try {
			inputString = Files.readString(Paths.get(filepath[0] + filepath[1]));
		}
		catch (IOException e) {
			System.err.println("無法讀取文件 " + Arrays.toString(filepath));
			e.printStackTrace();
		}
		String output = inputString.replaceAll("\n+", "\n");
		return output;
	}

    static void write(String[] path, String input) {
        String[] output = input.split("\n+");
		System.out.println("len = " + output.length);
		// System.out.println(Arrays.toString(path));
		String folderPath = "out_" + path[0];
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
    public static void main(String[] argv) {
        inputLines = read(argv).split("\n+");
        Solver solver = new Solver(inputLines);
        String output = solver.solve();
        System.out.printf("output = \n%s", output);
        write(argv, output);
    }
}