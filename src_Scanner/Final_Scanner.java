package src_Scanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Final_Scanner {
	private static final String[][] TOKENS = {
			{"COMMENT", "//.*"},
	        {"MULTI_COMMENT", "/\\*[^*]*(?:\\*(?!/)[^*]*)*\\*/"},
	        {"SHR", ">>"},
	        {"SHL", "<<"},
	        {"LAN", "&&"},
	        {"LOR", "\\|\\|"},
	        {"EQL", "=="},
	        {"NEQ", "!="},
	        {"BAN", "\\&"},
	        {"BOR", "\\|"},
	        {"BNT", "\\~"},
	        {"BXO", "\\^"},
	        {"ADD", "\\+"},
	        {"SUB", "\\-"},
	        {"MUL", "\\*"},
	        {"DIV", "\\/"},
	        {"MOD", "%"},
	        {"NOT", "!"},
	        {"GTR", ">"},
	        {"LES", "<"},
	        {"GEQ", ">="},
	        {"LEQ", "<="},
	        {"VAL_ASSIGN", "="},
	        {"ADD_ASSIGN", "\\+="},
	        {"SUB_ASSIGN", "\\-="},
	        {"MUL_ASSIGN", "\\*="},
	        {"DIV_ASSIGN", "\\/="},
	        {"REM_ASSIGN", "%="},
	        {"SHR_ASSIGN", ">>="},
	        {"SHL_ASSIGN", "<<="},
	        {"BAN_ASSIGN", "&="},
	        {"BOR_ASSIGN", "\\|="},
	        {"INC_ASSIGN", "\\+\\+"},
	        {"DEC_ASSIGN", "\\-\\-"},
	        {"'('", "\\("},
	        {"')'", "\\)"},
	        {"'['", "\\["},
	        {"']'", "\\]"},
	        {"'{'", "\\{"},
	        {"'}'", "\\}"},
	        {"':'", ":"},
	        {"';'", ";"},
	        {"','", ","},
	        {"COUT", "cout"},
	        {"AUTO_T", "auto"},
	        {"VOID_T", "void"},
	        {"CHAR", "char"},
	        {"INT_T", "int"},
	        {"LONG_T", "long"},
	        {"FLOAT_T", "float"},
	        {"DOUBLE_T", "double"},
	        {"BOOL_T", "bool"},
	        {"STR_T", "string"},
	        {"IF", "if"},
	        {"ELSE", "else"},
	        {"FOR", "for"},
	        {"WHILE", "while"},
	        {"RETURN", "return"},
	        {"BREAK", "break"},
	        {"BOOL_LIT", "true|false"},
	        
	        {"IDENT", "[a-zA-Z_][a-zA-Z0-9_]*"},
	        {"FLOAT_LIT", "\\d+\\.\\d+"},
	        {"INT_LIT", "\\d+"},
	        {"CHAR_LIT", "'.'"},
	        {"STRING_LIT", "\"(\\\\.|[^\"])*\""},
	        {"WHITESPACE", "[ \\t]+"}
	    };

		private static String[][][] getFilePathList(String folderPath){
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
		
		private static String read(String filepath[]) {
			System.out.println(filepath[0] + "," + filepath[1]);
			String inputString = "";
			try {
				inputString = Files.readString(Paths.get(filepath[0] + filepath[1]));
			}
			catch (IOException e) {
				System.err.println("無法讀取文件 " + filepath);
				e.printStackTrace();
			}
			String output = inputString.replaceAll("\n+", "\n");
			return output;
		}

		static void write(String[] path, List<String> output) {
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

		private static void solve(String filepath[]) {
	        StringBuilder regexBuilder = new StringBuilder();
	        for (String[] token : TOKENS) {
	            regexBuilder.append(token[1]).append("|");
	        }
	        regexBuilder.deleteCharAt(regexBuilder.length() - 1);
	        Pattern pattern = Pattern.compile(regexBuilder.toString());

			String code = read(filepath);
	        
	        Matcher matcher = pattern.matcher(code);
			List<String> output = new ArrayList<String>();
	        while (matcher.find()) {
	            String token = matcher.group();
	            for (String[] tokenInfo : TOKENS) {
	                if (token.matches(tokenInfo[1])) {
	                	if (tokenInfo[0].equals("BOOL_LIT"))
	                        output.add(tokenInfo[0] + " " + token);
						else if (tokenInfo[0].equals("CHAR_LIT"))
							output.add(tokenInfo[0] + " " + token);
	                	else if(tokenInfo[0].equals("COMMENT"))
	                		output.add(tokenInfo[0] + " " + token);
	                	else if(tokenInfo[0].equals("MULTI_COMMENT"))
	                		output.add(tokenInfo[0] + " " + token);
	                    else if(tokenInfo[0].equals("IDENT"))
	                		output.add(tokenInfo[0] + " " + token);
	                    else if(tokenInfo[0].equals("FLOAT_LIT"))
	                		output.add(tokenInfo[0] + " " + token);
	                    else if(tokenInfo[0].equals("INT_LIT"))
	                		output.add(tokenInfo[0] + " " + token);
	                    else if(tokenInfo[0].equals("STRING_LIT"))
	                		output.add(tokenInfo[0] + " " + token);
	                    else if (!tokenInfo[0].equals("WHITESPACE") ) {
	                        output.add(tokenInfo[0]);
	                    }
	                    break;
	                }
	            }
	        }
			write(filepath, output);
		}

	    public static void main(String[] args) {
			String folderPath = "data_0";
			String[][][] FilePathList = getFilePathList(folderPath);
			for(String[][] Folder:FilePathList) {
				for(String[] File:Folder) {
					solve(File);
				}
			}
	    }
}