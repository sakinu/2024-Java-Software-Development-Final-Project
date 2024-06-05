package src_Parser;

import java.util.ArrayList;
import java.util.List;

public class ParserState {                  //某個Sysnax可以有什麼右界
    private String id;
    private boolean isOk;
    private Integer Type;       //  1 Term
    private Term term;
    Integer SyntaxType;
    Integer[] posTable;

    ParserState(String id, boolean isOk, Integer SyntaxType, Integer Type, Term term, Integer[] posTable) {
        this.id = id;
        this.isOk = isOk;
        this.SyntaxType = SyntaxType;
        this.Type = Type;
        this.term = term;
        this.posTable = posTable;
    }

    String getId() {
        return id;
    }
    Boolean getIsOk() {
        return isOk;
    }
    Integer getType() {
        return Type;
    }
    Term term() {
        return term;
    }
    Integer getSyntaxType() {
        return SyntaxType;
    }
    Integer[] getPosTable() {
        return posTable;
    }
}