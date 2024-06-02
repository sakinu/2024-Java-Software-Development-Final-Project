package src_Compiler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

public class Preprocessing {
    static private Map<Integer, Integer> endTable = new TreeMap<>();
    static private Map<Integer, Integer> end2Table = new TreeMap<>();
    static private Map<String, Function> funcTable = new TreeMap<>();
    static private List<Term> IdentTable = new ArrayList<>();

    static void init(int totalLines, String[] lines, String[][] lineTokens) {
        Stack<Integer> startStack = new Stack<>();
        List<Integer> list = new ArrayList<>();
        for(int lineId=0; lineId<totalLines; lineId++) {
            if(Api.getLineType(lineId, lineTokens[lineId]) == 1) {
                startStack.push(lineId);
            }
            if(Api.getLineType(lineId, lineTokens[lineId]) == 2) {
                endTable.put(startStack.peek(), lineId);
                lineId += 2;
                while(lineId < totalLines && Api.getLineType(lineId, lineTokens[lineId]) == 8){
                    lineId++;
                }
                end2Table.put(startStack.peek(), lineId-1);
                startStack.pop();
            }
        }

        for(var key : endTable.keySet()) {
            System.out.println(key + " " + endTable.get(key));
        }
        System.out.println();
        for(var key : end2Table.keySet()) {
            System.out.println(key + " " + end2Table.get(key));
        }

        for(int lineId = 0; lineId < totalLines; lineId++) {
            if(Api.getLineType(lineId, lineTokens[lineId]) == 7) {
                lineId += 1;
                while(lineId < totalLines && Api.getLineType(lineId, lineTokens[lineId]) == 8) {
                    Integer Addr = Integer.parseInt(lineTokens[lineId][3]);
                    if(Addr != -1) IdentTable.add(new Term(lineTokens[lineId]));
                    lineId++;
                }
            }
        }

        Queue<Integer> funcReturnType = new LinkedList<>();
        int lastDump = 0;

        for(int lineId = 0; lineId < totalLines; lineId++) {
            if(Api.getLineType(lineId, lineTokens[lineId]) != 2) continue;
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
            funcReturnType.offer(returnType);
        }
        
        for(int lineId = 0; lineId < totalLines; lineId++) {
            if(Api.getLineType(lineId, lineTokens[lineId]) == 11) {         //func 找到開頭
                int Level = 1;
                String funcName = lineTokens[lineId][1];
                int start = lineId + 2;     //到Creat
                lineId += 3;                //parameter
                List<Term> parameters = new ArrayList<Term>();
                while(Api.getLineType(lineId, lineTokens[lineId]) == 5) {
                    String parameterName = lineTokens[lineId][2].substring(1, lineTokens[lineId][2].length()-1);
                    parameters.add(new Term(true, parameterName, -1, null, Api.getAddrsFrom80(lineTokens[lineId])));
                    lineId++;
                }
                while(true) {
                    if(Api.getLineType(lineId, lineTokens[lineId]) == 1) Level ++;
                    if(Api.getLineType(lineId, lineTokens[lineId]) == 2) Level --;
                    if(Level == 0){
                        break;
                    }
                    lineId++;
                }
                int end = lineId+1;                 //Dump
                for(int i=0; i<parameters.size(); i++) {
                    parameters.get(i).setValType(lineTokens[end + i + 1][2]);
                }
                funcTable.put(funcName, new Function(funcName, start, end, funcReturnType.poll(), parameters));
                lineId = lineId + parameters.size();
            }
        }
        for(var key: funcTable.keySet()) {
            funcTable.get(key).print();
        }
    }

    static Integer getEnd(Integer start) {
        return endTable.get(start);
    }

    static Integer getEnd2(Integer start) {
        return end2Table.get(start);
    }

    static Function getFunction(String functionName) {
        return funcTable.get(functionName);
    }

    static Term getIdentUseAddrs(int IdentAddrs) {
        return IdentTable.get(IdentAddrs);
    }
}
