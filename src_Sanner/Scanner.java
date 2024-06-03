package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
	private static final String[][] TOKENS = {
			{"COMMENT", "//.*"},
	        {"MULTI_COMMENT", "/\\*[^*]*(?:\\*(?!/)[^*]*)*\\*/"},
	        {"SHR", ">>"},
	        {"SHL", "<<"},
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
	        {"EQL", "=="},
	        {"NEQ", "!="},
	        {"LAN", "&&"},
	        {"LOR", "\\|\\|"},
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
	        {"STRING_LIT", "\"(\\\\.|[^\"])*\""},
	        {"WHITESPACE", "[ \\t]+"}
	    };

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);

	        StringBuilder inputBuilder = new StringBuilder();
	        String line;
	        while (!(line = scanner.nextLine()).equals("END")) {
	            inputBuilder.append(line).append("\n");
	        }
	        String code = inputBuilder.toString();

	        StringBuilder regexBuilder = new StringBuilder();
	        for (String[] token : TOKENS) {
	            regexBuilder.append(token[1]).append("|");
	        }
	        regexBuilder.deleteCharAt(regexBuilder.length() - 1);
	        Pattern pattern = Pattern.compile(regexBuilder.toString());

	        
	        
	        Matcher matcher = pattern.matcher(code);
	        while (matcher.find()) {
	            String token = matcher.group();
	            for (String[] tokenInfo : TOKENS) {
	                if (token.matches(tokenInfo[1])) {
	                	if (tokenInfo[0].equals("BOOL_LIT")) 
	                        System.out.println(tokenInfo[0] + "\t" + token);
	                	else if(tokenInfo[0].equals("COMMENT"))
	                		System.out.println(tokenInfo[0] + "\t" + token);
	                	else if(tokenInfo[0].equals("MULTI_COMMENT"))
	                		System.out.println(tokenInfo[0] + "\t" + token);
	                    else if(tokenInfo[0].equals("IDENT"))
	                		System.out.println(tokenInfo[0] + "\t" + token);
	                    else if(tokenInfo[0].equals("FLOAT_LIT"))
	                		System.out.println(tokenInfo[0] + "\t" + token);
	                    else if(tokenInfo[0].equals("INT_LIT"))
	                		System.out.println(tokenInfo[0] + "\t" + token);
	                    else if(tokenInfo[0].equals("STRING_LIT"))
	                		System.out.println(tokenInfo[0] + "\t" + token);
	                    else if (!tokenInfo[0].equals("WHITESPACE") ) {
	                        System.out.println(tokenInfo[0]);
	                    }
	                    break;
	                }
	            }
	        }

	        scanner.close();
	    }
}
