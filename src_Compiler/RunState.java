package src;
import java.util.Stack;
import java.util.HashMap;

public class RunState {                    //for遞迴
    String Name;                //我在什麼function內跑
    int startLine;
    int endLine;
    int nowLine;
    Stack<Term> TermStack;
    Stack<HashMap<String, Term>> symbolTable;
    
    RunState(String Name, int startLine, int endLine, int nowLine, Stack<HashMap<String, Term>> symbolTable) {
        this.Name = Name;
        this.startLine = startLine;
        this.endLine = endLine;
        this.nowLine = nowLine;
        this.TermStack = new Stack<Term>();
        this.symbolTable = symbolTable;
    }

    RunState(Function function) {
        this.Name = function.getName();
        this.startLine = function.getStart();
        this.endLine = function.getEnd();
        this.nowLine = this.startLine;
        this.TermStack = new Stack<Term>();
        this.symbolTable = new Stack<HashMap<String,Term>>();
    }

    void print() {
        System.out.printf("Name = %s, nowLine = %d ", Name, nowLine);
        System.out.printf("termStack = \n");
        for(Term term : TermStack) {
            term.print();
        }
    }

    Integer getIdentType(String IdentName) {
        Integer ret = Api.getKeywordType(IdentName);
        if(ret != null) return ret;
        for(int level = symbolTable.size()-1; level >= 0; level--) {
            if(symbolTable.get(level).containsKey(IdentName)) {
                return symbolTable.get(level).get(IdentName).getType();
            }
        }
        return null;
    }

    String getIdentVal(String IdentName) {
        String ret = Api.getKeywordVal(IdentName);
        if(ret != null) return ret;
        for(int level = symbolTable.size()-1; level >= 0; level--) {
            if(symbolTable.get(level).containsKey(IdentName)) {
                return symbolTable.get(level).get(IdentName).getVal();
            }
        }
        return null;
    }
}