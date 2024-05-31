package src_Compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Solver {           //找出所有function，載入main
    private int totalLines;
    private String[] lines;
    private String[][] lineTokens;
    private List<Term> IdentTable;              //用addrs去知道自己的資料
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
        IdentTable = preprocessing.getIdentTable(totalLines, lines, lineTokens);
        funcTable = preprocessing.getFunctionTable(totalLines, lines, lineTokens);
        runStack = new Stack<RunState>();
    }
    
    void runLine() {
        RunState runState = runStack.peek();
        // runStack.pop();
        int lineType = Api.getLineType(runState.nowLine, lineTokens[runState.nowLine]);
        System.out.printf("line : %d\n", runState.nowLine);
        System.out.printf("type:%-3d%s\n", lineType, lines[runState.nowLine]);
        String[] Tokens = lineTokens[runState.nowLine];
        String IdentVal;
        String IdentName;
        Integer IdentAddrs;
        if(30 <= lineType && lineType < 80) {
            int termNeed = Opt.getTermNeed(lineType);
            Term[] termToOpt = new Term[termNeed];
            for(int i = 0; i<termNeed; i++) {
                termToOpt[termNeed-i-1] = runState.TermStack.peek();
                runState.TermStack.pop();
            }
            Term termResult = Opt.getOptResult(lineType, termToOpt);
            if(lineType < 62) runState.TermStack.push(termResult);
            else runState.symbolTable.peek().put(termResult.getName(),termResult);
        }
        switch(lineType) {
            case 1:
                runState.symbolTable.push(new HashMap<String,Term>());
                break;
            case 2:
                runState.symbolTable.pop();
                break;
            case 5:
                IdentName = lineTokens[runState.nowLine][2].substring(1, lineTokens[runState.nowLine][2].length()-1);
                IdentVal = null;
                if(!runState.TermStack.empty()) {
                    IdentVal = runState.TermStack.pop().getVal();
                }
                runState.symbolTable.peek().put(IdentName, new Term(true, IdentName, null, IdentVal, 1));
                break;

            case 25:
                retTerm = runState.TermStack.peek();
                System.out.println("return ");
                retTerm.print();
                break;
            case 81:
                for(int i=1; i<Tokens.length; i++) {
                    coutput.append("_" + Api.converOutput(runState.TermStack.get(i-1)) + "_");
                }
                runState.TermStack.clear();
                break;
            case 90:
                IdentName = Tokens[1].substring(6, Tokens[1].length()-1);
                IdentAddrs = Integer.parseInt(Tokens[2].substring(8, Tokens[2].length()-1));
                runState.TermStack.push(new Term(true, IdentName, Api.getIdentType(IdentTable, IdentAddrs, IdentName), runState.getIdentVal(IdentName), IdentAddrs));
                break;
            case 92:
                runState.TermStack.push(new Term(false, null, 1, Tokens[1].toLowerCase(), -1));
                break;
            case 93:
                runState.TermStack.push(new Term(false, null, 3, Tokens[1], -1));
                break;
            case 94:
                runState.TermStack.push(new Term(false, null, 4, Tokens[1], -1));
                break;
            case 95:
                runState.TermStack.push(new Term(false, null, 5, lines[runState.nowLine].substring(9, lines[runState.nowLine].length()-1), -1));
                break;
        }
        runState.nowLine++;
        if(runState.nowLine == runState.endLine) {
            // runStack.push(runState);
            runStack.pop();
        }
        runState.print();
        System.out.println();
    }

    String solve() {
        runStack.push(new RunState(funcTable.get("main")));
        while(!runStack.empty()) {
            runLine();
        }
        return coutput.toString();
    }
}