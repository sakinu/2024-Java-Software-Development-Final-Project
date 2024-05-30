package src;

public class Term {
    private boolean isIdent;
    private String IdentName;

    private Integer valType;
    // private T val;
    private String val;

    Term(boolean isIdent, String IdentName, Integer valType, String val) {
        this.isIdent = isIdent;
        if(isIdent) {
            this.IdentName = IdentName;
            this.valType = valType;
            this.val = val;
            if(val == null) this.val = Api.getKeywordVal(IdentName);
        }
        else{
            this.valType = valType;
            this.val = val;
            if(val == null) val = Api.getDefualtVal(valType);
        }
    }

    void print() {
        System.out.printf(",%b, %s, %d, %s, \n", isIdent, IdentName, valType, val);
    }

    void setValType(String type) {
        this.valType = Api.getIntType(type);
    }
    void setValType(int type) {
        this.valType = type;
    }
    int getType() {
        return valType;
    }
    String getVal() {
        return val;
    }
}