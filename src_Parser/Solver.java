package src_Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Solver {
    private static String[][] tokens;
    static String[] output;
    static HashMap<String, HashMap<Integer, HashMap<Integer, ParserState>>> dp = new HashMap<>();      //某個語意在l~r的狀態

    public static void setTokens(String[][] input) {
        tokens = input;
    }

    static boolean hasKey(String SyntaxName, int left, int right) {
        if(!dp.containsKey(SyntaxName)) return false;
        if(!dp.get(SyntaxName).containsKey(left)) return false;
        if(!dp.get(SyntaxName).get(left).containsKey(right)) return false;
        return true;
    }

    static void putValue(String SyntaxName, int left, int right, boolean Value) {
        if(!dp.containsKey(SyntaxName)) dp.put(SyntaxName, new HashMap<>());
        if(!dp.get(SyntaxName).containsKey(left)) dp.get(SyntaxName).put(left, new HashMap<>());
        dp.get(SyntaxName).get(left).put(right, new ParserState(Value, counter, null));
    }

    static boolean checkTerm(String TermName, int dep, int left, int right) {
        if(left != right) return false;
        boolean ok = false;
        switch (TermName.substring(2)) {
            case "VARIABLE_T":
                if(tokens[left][0].equals("INT_T")) ok = true;
                if(tokens[left][0].equals("STR_T")) ok = true;
                break;
            
            case "IDENT":
                ok = tokens[left][0].equals("IDENT");
                break;

            case "RETURN":
                ok = tokens[left][0].equals("RETURN");
                break;

            case ",":
                ok = tokens[left][0].equals("','");
            case "{":
                ok = tokens[left][0].equals("'{'");
                break;
            case "}":
                ok = tokens[left][0].equals("'}'");
                break;
            case "(":
                ok = tokens[left][0].equals("'('");
                break;
            case ")":
                ok = tokens[left][0].equals("')'");
                break;
        }
        // System.out.println("T_, ok = " + ok);
        return ok;
    }

    static Integer counter = 0;

    static boolean parse(int mod, int dep, int left, int right, String SyntaxName) {             //l~r能否符合語意
        // System.out.println("\ninto parse " + dep + " " + left + "," + right + " " + SyntaxName);
        if(counter++ > 10000) {
            System.out.println("counter >");
            return false;
        }
        if(left > right) {
            // System.out.println("l > r");
            return false;
        }
        if(hasKey(SyntaxName, left, right)) {
            // System.out.println("\ninto parse " + dep + " " + left + "," + right + " " + SyntaxName);
            // System.out.println("dp vis");
            return dp.get(SyntaxName).get(left).get(right);
        }
        boolean ok = false;
        switch (SyntaxName.substring(0,2)) {
            case "S_":          //向下遞迴
                String[][] SyntaxTable = Syntax.getSyntaxTable(SyntaxName);
                for(int i=0; i<SyntaxTable.length; i++) {           //每種語法
                    Integer TableSize = SyntaxTable[i].length;
                    Integer[] posStack = new Integer[TableSize+1];        //枚舉每個語法的右界
                    for(int j=0; j<=TableSize; j++) {
                        posStack[j] = left-1+j;
                    }
                    while(true) {           //加上一個東西，找他最右邊，如果不能再右，就讓左邊加一
                        if(posStack[TableSize] == right) {
                            ok = true;
                            System.out.println("\ninto parse " + dep + " " + left + "," + right + " " + SyntaxName);
                            for(int j=0; j<=TableSize; j++) {
                                System.out.print(posStack[j] + " ");
                            } System.out.println();
                            for(int j=1; j<=TableSize; j++) {
                                if(!parse(mod, dep, posStack[j-1]+1, posStack[j], SyntaxTable[i][j-1])) {
                                    ok = false;
                                    break;
                                }
                            }
                        }
                        int checkIdx = TableSize;
                        posStack[checkIdx]++;
                        while(checkIdx > 0 && posStack[checkIdx] > right) {
                            checkIdx--;
                            posStack[checkIdx]++;
                        }
                        if(checkIdx == 0) break;
                        while(checkIdx < TableSize) {
                            posStack[++checkIdx] = posStack[checkIdx-1];
                        }
                        if(ok) break;
                    }
                    if(ok) break;
                }
                if(ok || true) {
                    System.out.println("\nparse " + dep + " " + left + "," + right + " " + SyntaxName);
                    System.out.println("S is " + ok);
                }
                break;
            case "T_":          //check term是對的
                ok = checkTerm(SyntaxName, dep+1, left, right);
                if(ok || true) {
                    System.out.println("\nparse " + dep + " " + left + "," + right + " " + SyntaxName);
                    System.out.println("T is " + ok);
                }
                break;
            case "D_":
                
            default:
                System.out.println("WTF parse");
                break;
        }
        putValue(SyntaxName, left, right, ok);
        return ok;
    }
    
    static String[][] solve() {
        for(int i=0; i<tokens.length; i++) {
            System.out.println(Arrays.toString(tokens[i]));
        }
        System.out.println();
        System.out.println("\n" + parse(0, 0, 0, tokens.length-1, "S_Program"));
        return null;
    }
}