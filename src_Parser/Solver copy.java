package src_Parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Solver {
    private static String[][] tokens;
    static HashMap<String, HashMap<Integer, HashMap<Integer, Boolean>>> dp = new HashMap<>();      //某個語意在l~r的狀態

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
        dp.get(SyntaxName).get(left).put(right, Value);
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

    static boolean parse(int dep, int left, int right, String SyntaxName) {
        // System.out.println("\ninto parse " + dep + " " + left + "," + right + " " + SyntaxName);
        if(counter++ > 100) {
            System.out.println("counter >");
            return false;
        }
        if(left > right) {
            // System.out.println("l > r");
            return false;
        }
        if(hasKey(SyntaxName, left, right)) {
            System.out.println("\ninto parse " + dep + " " + left + "," + right + " " + SyntaxName);
            System.out.println("dp vis");
            return dp.get(SyntaxName).get(left).get(right);
        }
        boolean ok = false;
        switch (SyntaxName.substring(0,2)) {
            case "S_":          //向下遞迴
                String[][] SyntaxTable = Syntax.getSyntaxTable(SyntaxName);
                for(int i=0; i<SyntaxTable.length; i++) {           //每種語法
                    // System.out.println("\ninto for " + dep + " " + left + "," + right + " " + SyntaxName);
                    // System.out.println("i = " + i);
                    // if(SyntaxTable[i].length == 0) {        //可以沒有
                    //     ok = true;
                    //     break;
                    // }
                    Stack<Integer> posStack = new Stack<>();        //枚舉每個語法的右界
                    posStack.push(left-1);
                    while(true) {
                        if(counter > 100) break;
                        Integer nowLeft = posStack.peek()+1;         //枚舉下一個語法能放在哪
                        Integer nowRight = nowLeft;
                        if(posStack.size() == SyntaxTable[i].length) nowRight = right;
                        if(nowLeft > nowRight) {
                            if(posStack.size() == 1) {      //起始點需要移動，代表沒有合法選擇
                                break;
                            }
                            int newPos = posStack.pop()+1;
                            if(newPos <= right) posStack.push(newPos);
                        }
                        // System.out.println("\ninto while " + dep + " " + left + "," + right + " " + SyntaxName);
                        // System.out.println("now = " + nowLeft + "," + nowRight);
                        // System.out.print("posStack = ");
                        // for(int j=0; j<posStack.size(); j++) {
                        //     System.out.print(posStack.get(j) + " ");
                        // } System.out.println();
                        // System.out.println(Arrays.toString(SyntaxTable[i]));
                        while(nowRight <= right && !parse(dep+1, nowLeft, nowRight, SyntaxTable[i][posStack.size()-1])) {
                            nowRight++;
                            System.out.println("nowRight = " + nowRight);
                        }
                        if(nowRight <= right) {     //可以放新的語法，把新的放上去
                            posStack.push(nowRight);
                            if(posStack.size() == SyntaxTable[i].length+1 && nowRight == right) {
                                ok = true;
                                break;
                            }
                        }
                        else {                      //不能放新的語法，把自己+1
                            if(posStack.size() == 1) {      //起始點需要移動，代表沒有合法選擇
                                break;
                            }
                            int newPos = posStack.pop()+1;
                            if(newPos <= right) posStack.push(newPos);
                        }
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
        System.out.println("\n" + parse(0, 0, tokens.length-1, "S_test"));
        return null;
    }
}