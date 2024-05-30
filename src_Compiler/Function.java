package src;

import java.util.List;

public class Function {
    private String name;
    private int startLine;
    private int endLine;
    private int returnType;
    private List<Term> Parameters;
    Function(String name, int startLine, int endLine, int returnType, List<Term> Parameters) {
        this.name = name;
        this.startLine = startLine;
        this.endLine = endLine;
        this.returnType = returnType;
        this.Parameters = Parameters;
    }
    String getName() {
        return name;
    }
    int getStart() {
        return startLine;
    }
    int getEnd() {
        return endLine;
    }
    int getReturnType() {
        return returnType;
    }
    void print() {
        System.out.printf("printFuction %s %d %d %d\n", name, startLine, endLine, returnType);
        System.out.print("Parameters:");
        for(Term term : Parameters) {
            term.print();
        }
        System.out.println();
    }
}