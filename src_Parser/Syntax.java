package src_Parser;

import java.util.HashMap;

public class Syntax {
    public static HashMap<String, String[][]> SyntaxTableMap = new HashMap<>(){{
        put("S_test", new String[][]{
            {"T_{", "S_StmtList", "T_}"}
        });
        put("S_Program", new String[][] {
            {"S_GlobalStmtList"}
        });
        put("S_GlobalStmtList", new String[][] {
            {"S_GlobalStmt"},
            {"S_GlobalStmt", "S_GlobalStmtList"}
        });
        put("S_GlobalStmt", new String[][] {
            // {"S_DefineVariableStmt"},
            {"S_FuncDefStmt"}
        });
        put("S_FuncDefStmt", new String[][]{
            {"T_VARIABLE_T", "T_IDENT", "T_(", "S_FuncParmStmtList", "T_)", "T_{", "S_StmtList" , "T_}"}
        });
        put("S_FuncParmStmtList", new String[][]{
            {"S_FuncParmStmt"},
            {"S_FuncParmStmt", "T_,", "S_FuncParmStmtList"},
        });
        put("S_FuncParmStmt", new String[][]{
            {"T_VARIABLE_T", "T_IDENT"}
        });
        put("S_StmtList", new String[][] {
            {"S_Stmt", "S_StmtList"},
            {"S_Stmt"},
        });
        put("S_Stmt", new String[][] {
            {"T_Cout", "S_CoutStmtList"}
            // {"T_RETURN"}
        });
        put("S_CoutStmtList", new String[][] {
            {"T_SHL", "S_Expn"},
            {"T_SHL", "S_Expn", "S_CoutStmtList"}
        });
        put("Expn", new String[][] {
            {"T_Term"},
            {"T_Term", "T_", "Expn"}
        });
    }};

    static String[][] getSyntaxTable(String syntax) {
        return SyntaxTableMap.get(syntax);
    }
}