package src_Compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Solver {           //找出所有function，載入main
    private int totalLines;
    private String[] lines;
    private String[][] lineTokens;

    private StringBuilder coutput = new StringBuilder("");
    private Stack<Stack<RunState>> runStack;
    private Stack<Term> termStack;

    Solver(String[] inputLines) {
        this.totalLines = inputLines.length;
        this.lines = inputLines;
        this.lineTokens = new String[this.lines.length][];
        for(int lineId = 0; lineId < totalLines; lineId ++) {
            lineTokens[lineId] = lines[lineId].split("\s+");
        }
        runStack = new Stack<>();
        termStack = new Stack<>();
    }

    String getIdentVal(String IdentName) {
        for(String key : runStack.peek().peek().getSymbolTable().keySet()) {
            System.out.print(key + ",");
            runStack.peek().peek().getSymbolTable().get(key).myprint();
        }
        String ret = null;
        for(int i=runStack.peek().size()-1; i>=0; i--) {
            ret = runStack.peek().get(i).getIdentVal(IdentName);
            if(ret != null) break;
        }
        return ret;
    }

    void updateIdentVal(String IdentName, Term newTerm) {
        System.out.println("newTerm = ");
        newTerm.myprint();
        for(int i=runStack.peek().size()-1; i>=0; i--) {
            runStack.peek().get(i).printsymbolTable();
            if(!runStack.peek().get(i).containsKey(IdentName))
                continue;
            System.out.println("GOGO");
            runStack.peek().get(i).updateIdent(IdentName, newTerm);
        }
    }

    void assignIdent(String IdentName, Term newTerm) {
        runStack.peek().peek().updateIdent(IdentName, newTerm);
    }

    void myprintRunStack() {
        for(int i=0; i<runStack.size(); i++) {
            System.out.print(runStack.get(i).size());
        }
        System.out.println();
    }

    void addNewRunState(RunState runState, int startLine, int endLine) {
        RunState newState = new RunState(runState.getName(), startLine, endLine, startLine);
        runStack.peek().push(newState);
        runState.setLine(newState.getEndLine());
    }

    void addNewRunState(RunState runState, int endType) {
        Integer end = null;
        if(endType < 0) {
            end = -endType;
        }
        if(endType == 1) end = Preprocessing.getEnd(runState.getNowLine());
        if(endType == 2) end = Preprocessing.getEnd2(runState.getNowLine());
        RunState newState = new RunState(runState.getName(), runState.getNowLine()+1, end, runState.getNowLine()+1);
        runStack.peek().push(newState);
        runState.setLine(newState.getEndLine());
    }

    void runStackPeekPop() {
        Stack<Term> tmp = runStack.peek().peek().getTermStack();
        System.out.println("runStackPeekPop");
        for(int i=0; i<tmp.size(); i++) {
            termStack.push(tmp.get(i));
            tmp.get(i).myprint();
        }
        runStack.peek().pop();
    }
    
    void runLine() {
        RunState runState = runStack.peek().peek();
        if(runState.getNowLine() == runState.getEndLine()) {        //不該繼續跑
            runStackPeekPop();
            return;
        }
        // runStack.pop();
        int lineType = Api.getLineType(runState.getNowLine(), lineTokens[runState.getNowLine()]);
        System.out.printf("line : %d\n", runState.getNowLine());
        System.out.printf("type : %-4d%s\n", lineType, lines[runState.getNowLine()]);
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
            termResult.checkBoolStr();
            if(lineType < 80) {
                termResult.setIsIdent(false);
                termResult.setName(null);
                runState.pushTerm(termResult);
            }
            else {
                updateIdentVal(termResult.getName(), termResult);
            }
        } else {
            switch(lineType) {
                case 0:
                    break;
                case 1:
                    if(runStack.peek().size() >= Api.getCreateLevel(lineTokens[runState.getNowLine()])) break;   //如果是function
                    addNewRunState(runState, 1);
                    break;
                case 2:
                runStackPeekPop();
                    break;
                case 5:
                    IdentName = lineTokens[runState.getNowLine()][2].substring(1, lineTokens[runState.getNowLine()][2].length()-1);
                    IdentVal = null;
                    if(!runState.noTerm()) {
                        IdentVal = runState.popTopTerm().getVal();
                    }
                    assignIdent(IdentName, new Term(true, IdentName, null, IdentVal, 1));
                    break;
                case 7:
                    break;
                case 13:
                    termStack.push(runState.getTopTerm());
                    System.out.println("return ");
                    break;
                case 21:
                    if(Api.converBool(runState.popTopTerm())) {     //if成立，則開一個新的runState否則else要被跳過 $
                        runState.addLine(1);
                        addNewRunState(runState, 2);
                        System.out.println("IfLine = " + runState.getNowLine());
                        if(Api.getLineType(runState.getNowLine()+1, lineTokens[runState.getNowLine()+1]) == 23) {
                            runState.setLine(Preprocessing.getEnd2(runState.getNowLine()+2));
                        }
                        System.out.println("IfLine = " + runState.getNowLine());
                    }
                    else{
                        runState.setLine(Preprocessing.getEnd(runState.getNowLine()+1));
                        System.out.println("nowLine = " + runState.getNowLine());
                    }
                    break;
                case 25:
                    Integer whileLine = runState.getNowLine();
                    Integer nextStart = runState.getNowLine();
                    while(Api.getLineType(null, lineTokens[nextStart]) != 1) {
                        nextStart++;
                    }
                    if(runState.getRunType() != "WHILE") {     //第一次遇到，則放新的runState跑判斷，否則判斷一下要不要跑進去
                        System.out.println("WHILE TRUE");
                        addNewRunState(runState, -nextStart);
                        runState.setRunType("WHILE");
                        runState.setLine(whileLine-1);
                    }
                    else {                                      //否則判一下上次的結果如何，true進迴圈，false結束while
                        runState.setRunType(null);
                        if(Api.converBool(termStack.pop())) {
                            System.out.println("WIHLE TRUE");
                            System.out.println(nextStart+1 + " _ " + Preprocessing.getEnd2(nextStart));
                            addNewRunState(runState, nextStart+1, Preprocessing.getEnd2(nextStart));
                            runState.setLine(whileLine-1);
                        }
                        else{
                            System.out.println("WIHLE FALSE");
                            runState.setLine(Preprocessing.getEnd2(nextStart));
                        }
                    }
                    break;
                case 101:
                    coutput.append(runState.getCout());
                    break;
                case 110:
                    IdentName = lineTokens[runState.getNowLine()][1].substring(6, lineTokens[runState.getNowLine()][1].length()-1);
                    IdentAddrs = Integer.parseInt(lineTokens[runState.getNowLine()][2].substring(8, lineTokens[runState.getNowLine()][2].length()-1));
                    runState.pushTerm(new Term(true, IdentName, Api.getIdentType(IdentAddrs, IdentName), getIdentVal(IdentName), IdentAddrs));
                    break;
                case 112:
                    runState.pushTerm(new Term(false, null, 1, lineTokens[runState.getNowLine()][1].equals("TRUE") ? "1" : "0", -1));
                    break;
                case 113:
                    runState.pushTerm(new Term(false, null, 3, lineTokens[runState.getNowLine()][1], -1));
                    break;
                case 114:
                    runState.pushTerm(new Term(false, null, 4, lineTokens[runState.getNowLine()][1], -1));
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
        System.out.println();
    }

    String solve() {
        Preprocessing.init(totalLines, lines, lineTokens);
        runStack.push(new Stack<>());
        runStack.peek().push(new RunState(Preprocessing.getFunction("main")));
        Integer runTimeCounter = 0;
        // while(!runStack.empty() && runTimeCounter++ < 100) {
        while(!runStack.empty()) {
            runLine();
            myprint();
        }
        return coutput.toString();
    }
    void myprint() {
        if(!runStack.isEmpty())
            runStack.peek().peek().myprint();
        myprintRunStack();
    }
}