package src_Parser;

import java.util.ArrayList;
import java.util.List;

public class ParserState {                  //某個Sysnax可以有什麼右界
    boolean isOk;
    Integer Type;       //  1 Term
    Term term;

    ParserState(boolean isOk, Integer Type, Term term) {
        this.isOk = isOk;
        this.Type = Type;
        this.term = term;
    }
}