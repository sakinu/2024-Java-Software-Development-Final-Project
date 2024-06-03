package src_Parser;

import java.util.HashMap;

public class Syntax {
    public static HashMap<String, String[][]> SyntaxTable = new HashMap<>(){{
        put("Program", new String[][] {
            {"GlobalStmtList"}
        });
        put("GlobalStmtList", new String[][] {
            {"GlobalStmt"},
            {"GlobalStmt", "GlobalStmtList"}
        });
        put("GlobalStmt", new String[][] {
            {"DefineVariableStmt"},
            {"FuncDefStmt"}
        });
        put("FuncDefStmt", new String[][]{
            {"VARIABLE_T", "IDENT", "(", "FuncParmStmtList", ")", "{", "StmtList" , "}"}
        });
        put("FuncParmStmtList", new String[][]{
            {"FuncParmStmt"},
            {"FuncParmStmt", ",", "FuncParmStmtList"}
        });
        put("FuncParmStmt", new String[][]{
            {"VARIABLE_T", "IDENT"}
        });
        put("StmtList", new String[][]{
            {"Stmt"},
            {"Stmt", "StmtList"}
        });
        put("Stmt", new String[][]{

        });
    }};
}
