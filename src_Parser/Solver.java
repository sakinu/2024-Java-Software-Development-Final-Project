package src_Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Solver {
    private static String[][] tokens;
    static List<String> output = new ArrayList<>();
    static HashMap<String, HashMap<Integer, HashMap<Integer, ParserState>>> dp = new HashMap<>();      //某個語意在l~r的狀態

    static void setTokens(String[][] input) {
        tokens = input;
    }

    static boolean hasKey(String SyntaxName, int left, int right) {
        if(!dp.containsKey(SyntaxName)) return false;
        if(!dp.get(SyntaxName).containsKey(left)) return false;
        if(!dp.get(SyntaxName).get(left).containsKey(right)) return false;
        return true;
    }

    static void putValue(String SyntaxName, int left, int right, Integer SyntaxType, boolean ok, Integer[] posTable) {
        if(!dp.containsKey(SyntaxName)) dp.put(SyntaxName, new HashMap<>());
        if(!dp.get(SyntaxName).containsKey(left)) dp.get(SyntaxName).put(left, new HashMap<>());
        dp.get(SyntaxName).get(left).put(right, new ParserState(SyntaxName, ok, SyntaxType, 1, null, posTable));
    }

    static boolean checkTerm(String TermName, int dep, int left, int right) {
        if(left != right) return false;
        boolean ok = false;
        switch (TermName.substring(2)) {
            case "VARIABLE_T":
                if(tokens[left][0].equals("INT_T")) ok = true;
                if(tokens[left][0].equals("STR_T")) ok = true;
                break;

            case "Term":
                if(tokens[left][0].equals("IDENT")) ok = true;
                if(tokens[left][0].equals("BOOl_LIT")) ok = true;
                if(tokens[left][0].equals("CHAR_LIT")) ok = true;
                if(tokens[left][0].equals("INT_LIT")) ok = true;
                if(tokens[left][0].equals("FLOAT_LIT")) ok = true;
                if(tokens[left][0].equals("STRING_LIT")) ok = true;
                break;
            
            case "IDENT":
                ok = tokens[left][0].equals("IDENT");
                break;

            case "RETURN":
                ok = tokens[left][0].equals("RETURN");
                break;
            
            case "Cout":
                ok = tokens[left][0].equals("COUT");
                break;

            case ";":
                ok = tokens[left][0].equals("';'");
                break;
            case ",":
                ok = tokens[left][0].equals("','");
                break;
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

            case "SHL":
                ok = tokens[left][0].equals("SHL");
                break;
        }
        // System.out.println("T_, ok = " + ok);
        return ok;
    }

    static Integer counter = 0;

    static boolean parse(int dep, int left, int right, String SyntaxName) {             //l~r能否符合語意
        // System.out.println("\ninto parse " + dep + " " + left + "," + right + " " + SyntaxName);
        if(hasKey(SyntaxName, left, right)) {
            // System.out.println("\ninto parse " + dep + " " + left + "," + right + " " + SyntaxName);
            // System.out.println("dp vis");
            return dp.get(SyntaxName).get(left).get(right).getIsOk();
        }
        counter += 10;
        if(counter > 1000000) {
            System.out.println("counter >");
            return false;
        }
        if(left > right) {
            // System.out.println("l > r");
            return false;
        }
        boolean ok = false;
        System.out.println(SyntaxName);
        Integer SyntaxType = null;
        Integer[] posStack = null;
        switch (SyntaxName.substring(0,2)) {
            case "S_":          //向下遞迴
                String[][] SyntaxTable = Syntax.getSyntaxTable(SyntaxName);
                for(int i=0; i<SyntaxTable.length; i++) {           //每種語法
                    SyntaxType = i;
                    Integer TableSize = SyntaxTable[i].length;
                    posStack = new Integer[TableSize+1];        //枚舉每個語法的右界
                    for(int j=0; j<=TableSize; j++) {
                        posStack[j] = left-1+j;
                    }
                    while(true) {           //加上一個東西，找他最右邊，如果不能再右，就讓左邊加一
                        if(counter++ > 1000000) {
                            System.out.println("counter2 >");
                            break;
                        }
                        if(posStack[TableSize] == right) {
                            ok = true;
                            System.out.println("\ninto parse " + dep + " " + left + "," + right + " " + SyntaxName);
                            for(int j=0; j<=TableSize; j++) {
                                System.out.print(posStack[j] + " ");
                            } System.out.println();
                            for(int j=1; j<=TableSize; j++) {
                                if(!parse(dep, posStack[j-1]+1, posStack[j], SyntaxTable[i][j-1])) {
                                    ok = false;
                                    break;
                                }
                            }
                            if(ok) break;
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
                    }
                    if(ok) break;
                }
                // if(ok || true) {
                //     System.out.println("\nparse " + dep + " " + left + "," + right + " " + SyntaxName);
                //     System.out.println("S is " + ok);
                // }
                break;
            case "T_":          //check term是對的
                ok = checkTerm(SyntaxName, dep+1, left, right);
                // if(ok || true) {
                //     System.out.println("\nparse " + dep + " " + left + "," + right + " " + SyntaxName);
                //     System.out.println("T is " + ok);
                // }
                break;
                
            default:
                System.out.println("WTF parse");
                break;
        }
        putValue(SyntaxName, left, right, SyntaxType, ok, posStack);
        return ok;
    }

    static void getOutput(int dep, int left, int right, String SyntaxName) {             //l~r能否符合語意
        // System.out.println(left + " ~ " + right + " is " + SyntaxName.substring(2));
        output.add(left + " ~ " + right + " is " + SyntaxName.substring(2));
        ParserState now = dp.get(SyntaxName).get(left).get(right);
        switch (SyntaxName.substring(0,2)) {
            case "S_":          //向下遞迴
                String[][] SyntaxTable = Syntax.getSyntaxTable(SyntaxName);
                Integer[] posTable = now.getPosTable();
                for(int i=1; i<posTable.length; i++) {
                    getOutput(dep+1, posTable[i-1]+1, posTable[i], SyntaxTable[now.getSyntaxType()][i-1]);
                }
                break;
            case "T_":          //check term是對的
                break;
            case "D_":
                break;
            default:
                System.out.println("WTF parse");
                break;
        }
    }
    
    static List<String> solve() {
        for(int i=0; i<tokens.length; i++) {
            System.out.println(Arrays.toString(tokens[i]));
        }
        System.out.println();
        String startSyntax = "S_Program";
        boolean canRun = parse(0, 0, tokens.length-1, startSyntax);
        if(canRun) {
            getOutput(0, 0, tokens.length-1, startSyntax);
        }
        output.add(String.valueOf(canRun));
        System.out.println(canRun);
        return output;
    }
}