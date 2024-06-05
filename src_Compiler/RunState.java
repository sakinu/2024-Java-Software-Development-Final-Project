package src_Compiler;
import java.util.Stack;
import java.util.HashMap;

public class RunState {                    //for遞迴
    private String Name;                //我在什麼Scope內跑
    private int startLine;
    private int endLine;
    private int nowLine;
    private String runType;
    private Stack<Term> TermStack;
    private HashMap<String, Term> symbolTable;
    
    RunState(String Name, int startLine, int endLine, int nowLine) {
        this.Name = Name;
        this.startLine = startLine;
        this.endLine = endLine;
        this.nowLine = nowLine;
        this.runType = null;
        this.TermStack = new Stack<Term>();
        this.symbolTable = new HashMap<>();
    }

    RunState(Function function) {
        this.Name = function.getName();
        this.startLine = function.getStart();
        this.endLine = function.getEnd();
        this.nowLine = this.startLine;
        this.runType = null;
        this.TermStack = new Stack<Term>();
        this.symbolTable = new HashMap<String,Term>();
    }

    void myprint() {
        System.out.printf("Name = %s, nowLine = %d ", Name, nowLine);
        System.out.printf("termStack = \n");
        for(Term term : TermStack) {
            term.myprint();
        }
    }

    boolean containsKey(String name) {
        return symbolTable.containsKey(name);
    }

    void printsymbolTable() {
        for(var key : symbolTable.keySet()) {
            System.out.print(key + " ");
            symbolTable.get(key).myprint();
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

    void setRunType(String runType) {
        this.runType = runType;
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

    void updateIdent(String IdentName, Term term) {
        System.out.println("upd");
        term.myprint();
        symbolTable.put(IdentName, term);
    }

    boolean noTerm() {
        return TermStack.isEmpty();
    }

    Stack<Term> getTermStack() {
        return TermStack;
    }

    String getCout() {
        StringBuilder cout = new StringBuilder();
        for(int i=0; i<TermStack.size(); i++){
            // cout.append("_").append(Api.converOutput(TermStack.get(i))).append("_");
            cout.append(Api.converOutput(TermStack.get(i)));
        }
        TermStack.clear();
        return cout.toString();
    }

    Integer getIdentType(String IdentName) {
        Integer ret = Api.getKeywordType(IdentName);
        if(ret != null) return ret;
        if(symbolTable.containsKey(IdentName)) {
            return symbolTable.get(IdentName).getType();
        }
        return null;
    }

    String getIdentVal(String IdentName) {
        String ret = Api.getKeywordVal(IdentName);
        if(ret != null) return ret;
        if(symbolTable.containsKey(IdentName)) {
            return symbolTable.get(IdentName).getVal();
        }
        return null;
    }

    HashMap<String, Term> getSymbolTable() {
        return symbolTable;
    }

    String getRunType() {
        return this.runType;
    }
}