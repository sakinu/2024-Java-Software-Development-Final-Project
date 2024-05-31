package src_Compiler;
import java.util.Stack;
import java.util.HashMap;

public class RunState {                    //for遞迴
    private String Name;                //我在什麼Scope內跑
    private int startLine;
    private int endLine;
    private int nowLine;
    private Stack<Term> TermStack;
    private HashMap<String, Term> symbolTable;
    
    RunState(String Name, int startLine, int endLine, int nowLine) {
        this.Name = Name;
        this.startLine = startLine;
        this.endLine = endLine;
        this.nowLine = nowLine;
        this.TermStack = new Stack<Term>();
        this.symbolTable = new HashMap<>();
    }

    RunState(Function function) {
        this.Name = function.getName();
        this.startLine = function.getStart();
        this.endLine = function.getEnd();
        this.nowLine = this.startLine;
        this.TermStack = new Stack<Term>();
        this.symbolTable = new HashMap<String,Term>();
    }

    void print() {
        System.out.printf("Name = %s, nowLine = %d ", Name, nowLine);
        System.out.printf("termStack = \n");
        for(Term term : TermStack) {
            term.print();
        }
    }

    int getNowLine() {
        return this.nowLine;
    }

    public int getEndLine() {
        return endLine;
    }

    void addLine(int add) {
        nowLine += add;
    }

    void setLine(int line) {
        nowLine = line;
    }

    boolean isEndLine() {
        return nowLine == endLine;
    }

    String getName() {
        return Name;
    }

    void pushTerm(Term term) {
        TermStack.push(term);
    }

    Term getTopTerm() {
        return TermStack.peek();
    }

    Term popTopTerm() {
        return TermStack.pop();
    }

    void updIdent(String IdentName, Term term) {
        symbolTable.put(IdentName, term);
    }

    boolean noTerm() {
        return TermStack.isEmpty();
    }

    String getCout() {
        StringBuilder cout = new StringBuilder();
        for(int i=0; i<TermStack.size(); i++){
            cout.append("_").append(Api.converOutput(TermStack.get(i))).append("_");
        }
        TermStack.clear();
        return cout.toString();
    }

    Integer getIdentType(String IdentName) {
        Integer ret = Api.getKeywordType(IdentName);
        if(ret != null) return ret;
        for(int level = symbolTable.size()-1; level >= 0; level--) {
            if(symbolTable.containsKey(IdentName)) {
                return symbolTable.get(IdentName).getType();
            }
        }
        return null;
    }

    String getIdentVal(String IdentName) {
        String ret = Api.getKeywordVal(IdentName);
        if(ret != null) return ret;
        for(int level = symbolTable.size()-1; level >= 0; level--) {
            if(symbolTable.containsKey(IdentName)) {
                return symbolTable.get(IdentName).getVal();
            }
        }
        return null;
    }
}