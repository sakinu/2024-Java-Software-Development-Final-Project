package src_Compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Solver {           //找出所有function，載入main
    private int totalLines;
    private String[] lines;
    private String[][] lineTokens;
    // private List<Term> IdentTable;              //用addrs去知道自己的資料
    // private HashMap<String, Function> funcTable;             //function

    private StringBuilder coutput = new StringBuilder("");
    private Stack<Stack<RunState>> runStack;            //一維：無相互依賴如func  二維：有相互依賴如if
    private Term retTerm;

    Solver(String[] inputLines) {
        this.totalLines = inputLines.length;
        this.lines = inputLines;
        this.lineTokens = new String[this.lines.length][];
        for(int lineId = 0; lineId < totalLines; lineId ++) {
            lineTokens[lineId] = lines[lineId].split("\s+");
        }
        runStack = new Stack<Stack<RunState>>();
        Preprocessing.init(totalLines, lines, lineTokens);
    }

    String getIdentVal(String IdentName) {
        String ret = null;
        for(int i=runStack.peek().size()-1; i>=0; i--) {
            ret = runStack.peek().get(i).getIdentVal(IdentName);
            if(ret != null) break;
        }
        return ret;
    }

    void printRunStack() {
        for(int i=0; i<runStack.size(); i++) {
            System.out.print(runStack.get(i).size());
        }
        System.out.println();
    }

    void addNewRunState(RunState runState) {
        RunState newState = new RunState(runState.getName(), runState.getNowLine()+1, Preprocessing.getEnd(runState.getNowLine()), runState.getNowLine()+1);
        runStack.peek().push(newState);
        runState.setLine(newState.getEndLine());
    }
    
    void runLine() {
        RunState runState = runStack.peek().peek();
        // runStack.pop();
        int lineType = Api.getLineType(runState.getNowLine(), lineTokens[runState.getNowLine()]);
        System.out.printf("line : %d\n", runState.getNowLine());
        System.out.printf("type : %-4d%s\n", lineType, lines[runState.getNowLine()]);
        String[] Tokens = lineTokens[runState.getNowLine()];
        String IdentVal;
        String IdentName;
        Integer IdentAddrs;
        if(30 <= lineType && lineType < 100) {
            int termNeed = Opt.getTermNeed(lineType);
            Term[] termToOpt = new Term[termNeed];
            for(int i = 0; i<termNeed; i++) {
                termToOpt[termNeed-i-1] = runState.getTopTerm();
                runState.popTopTerm();
            }
            Term termResult = Opt.getOptResult(lineType, termToOpt);
            if(lineType < 80) runState.pushTerm(termResult);
            else runState.updIdent(termResult.getName(),termResult);
        } else {
            switch(lineType) {
                case 0:
                    break;
                case 1:
                    if(runStack.peek().size() >= Api.getCreateLevel(Tokens)) break;   //如果是function
                    addNewRunState(runState);
                    break;
                case 2:
                    runStack.peek().pop();
                    break;
                case 5:
                    IdentName = lineTokens[runState.getNowLine()][2].substring(1, lineTokens[runState.getNowLine()][2].length()-1);
                    IdentVal = null;
                    if(!runState.noTerm()) {
                        IdentVal = runState.popTopTerm().getVal();
                    }
                    runState.updIdent(IdentName, new Term(true, IdentName, null, IdentVal, 1));
                    break;

                case 13:
                    retTerm = runState.getTopTerm();
                    System.out.println("return ");
                    break;
                case 21:
                    if(Api.converBool(runState.popTopTerm())) {     //if成立，則else要被跳過 $
                    }
                    else{
                        runState.setLine(Preprocessing.getEnd(runState.getNowLine()+1));
                        System.out.println("nowLine = " + runState.getNowLine());
                    }
                    
                case 101:
                    coutput.append(runState.getCout());
                    break;
                case 110:
                    IdentName = Tokens[1].substring(6, Tokens[1].length()-1);
                    IdentAddrs = Integer.parseInt(Tokens[2].substring(8, Tokens[2].length()-1));
                    runState.pushTerm(new Term(true, IdentName, Api.getIdentType(IdentAddrs, IdentName), getIdentVal(IdentName), IdentAddrs));
                    break;
                case 112:
                    runState.pushTerm(new Term(false, null, 1, Tokens[1].equals("TRUE") ? "1" : "0", -1));
                    break;
                case 113:
                    runState.pushTerm(new Term(false, null, 3, Tokens[1], -1));
                    break;
                case 114:
                    runState.pushTerm(new Term(false, null, 4, Tokens[1], -1));
                    break;
                case 115:
                    runState.pushTerm(new Term(false, null, 5, lines[runState.getNowLine()].substring(9, lines[runState.getNowLine()].length()-1), -1));
                    break;
                default:
                    System.out.println("WTF SOLVER");
            }
        }
        runState.addLine(1);
        if(runStack.peek().isEmpty()) runStack.pop();
        runState.print();
        printRunStack();
        System.out.println();
    }

    String solve() {
        runStack.push(new Stack<>());
        runStack.peek().push(new RunState(Preprocessing.getFunction("main")));
        while(!runStack.empty()) {
            runLine();
        }
        return coutput.toString();
    }
}