package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Solver {           //找出所有function，載入main
    private int totalLines;
    private String[] lines;
    private String[][] lineTokens;
    private HashMap<String, Function> funcTable;             //function

    StringBuilder coutput = new StringBuilder("");
    private Stack<RunState> runStack;
    Term retTerm;

    Solver(String[] inputLines) {
        this.totalLines = inputLines.length;
        this.lines = inputLines;
        this.lineTokens = new String[this.lines.length][];
        for(int lineId = 0; lineId < totalLines; lineId ++) {
            lineTokens[lineId] = lines[lineId].split("\s+");
        }
        funcTable = new HashMap<>();
        runStack = new Stack<RunState>();
    }

    int getLineType(int lineId) {
        String[] Tokens = lineTokens[lineId];
        if(Tokens.length == 0) return 0;
        if(Tokens[0].equals(">")) {
            if(Tokens[1].equals("Create")) {
                return 1;
            }
            if(Tokens[1].equals("Dump")) {
                return 2;
            }
            if(Tokens[1].equals("Insert")) {
                return 5;
            }
        }

        if(Tokens[0].equals("func:")) {
            return 21;
        }
        if(Tokens[0].equals("RETURN")) {
            return 25;
        }

        if(Tokens[0].equals("cout")){
            return 61;
        }

        if(Tokens[0].equals("IDENT")) {
            return 70;
        }
        if(Tokens[0].equals("BOOL_LIT")) {
            return 72;
        }
        if(Tokens[0].equals("INT_LIT")) {
            return 73;
        }
        if(Tokens[0].equals("FLOAT_LIT")) {
            return 74;
        }
        if(Tokens[0].equals("STR_LIT")) {
            return 75;
        }
        return -1;
    }

    void getFunctionTable() {
        HashMap<String, Integer> functionReturnTypeTable = new HashMap<>();
        HashMap<String, Integer> termTypeTable = new HashMap<>();
        int lastDump = 0;

        for(int lineId = 0; lineId < totalLines; lineId++) {
            if(getLineType(lineId) != 2) continue;
                lastDump = lineId;
        }

        for(int lineId=lastDump+1; lineId<totalLines-1; lineId++) {
            char last = lineTokens[lineId][5].charAt(lineTokens[lineId][5].length()-1);
            int returnType = -1;
            switch (last) {
                case 'I':
                    returnType = 3;
                    break;
            }
            functionReturnTypeTable.put(lineTokens[lineId][1], returnType);
        }
        
        for(int lineId = 0; lineId < totalLines; lineId++) {
            if(getLineType(lineId) == 21) {         //找到開頭
                int Level = 1;
                String funcName = lineTokens[lineId][1];
                int start = lineId + 2;     //到Creat
                lineId += 3;                //parameter
                List<Term> parameters = new ArrayList<Term>();
                while(getLineType(lineId) == 5) {
                    String parameterName = lineTokens[lineId][2].substring(1, lineTokens[lineId][2].length()-1);
                    parameters.add(new Term(true, parameterName, -1, ""));
                    lineId++;
                }
                while(true) {
                    if(getLineType(lineId) == 1) Level ++;
                    if(getLineType(lineId) == 2) Level --;
                    if(Level == 0){
                        break;
                    }
                    lineId++;
                }
                int end = lineId+1;                 //Dump
                for(int i=0; i<parameters.size(); i++) {
                    parameters.get(i).setValType(lineTokens[end + i + 1][2]);
                }
                funcTable.put(funcName, new Function(funcName, start, end, functionReturnTypeTable.get(funcName), parameters));
                lineId = lineId + parameters.size();
            }
        }
        for(var key: funcTable.keySet()) {
            funcTable.get(key).print();
        }
    }
    
    void runLine() {
        RunState runState = runStack.peek();
        runStack.pop();
        int lineType = getLineType(runState.nowLine);
        System.out.printf("line : %d\n", runState.nowLine);
        System.out.printf("type:%-3d%s\n", lineType, lines[runState.nowLine]);
        String[] Tokens = lineTokens[runState.nowLine];
        String IdentName;
        switch(lineType) {
            case 1:
                runState.symbolTable.push(new HashMap<String,Term>());
                break;
            case 2:
                runState.symbolTable.pop();
                break;

            case 25:
                retTerm = runState.TermStack.peek();
                System.out.println("return ");
                retTerm.print();
                break;

            case 61:
                for(int i=1; i<Tokens.length; i++) {
                    coutput.append("_" + Api.convertType(Tokens[i], runState.TermStack.get(i-1)) + "_");
                }
                runState.TermStack.clear();
                break;

            case 70:
                IdentName = Tokens[1].substring(6, Tokens[1].length()-1);
                runState.TermStack.push(new Term(true, IdentName, runState.getIdentType(IdentName), runState.getIdentVal(IdentName)));
                break;
            case 72:
                runState.TermStack.push(new Term(false, null, 2, Tokens[1]));
                break;
            case 73:
                runState.TermStack.push(new Term(false, null, 3, Tokens[1]));
                break;
            case 74:
                runState.TermStack.push(new Term(false, null, 4, Tokens[1]));
                break;
            case 75:
                runState.TermStack.push(new Term(false, null, 5, lines[runState.nowLine].substring(9, lines[runState.nowLine].length()-1)));
                break;
        }
        runState.nowLine++;
        if(runState.nowLine < runState.endLine) {
            runStack.push(runState);
        }
        // runState.print();
        System.out.println();
    }

    void solve() {
        getFunctionTable();
        runStack.push(new RunState(funcTable.get("main")));
        while(!runStack.empty()) {
            runLine();
        }
        System.out.printf("output = \n_%s", coutput.toString());
    }
}