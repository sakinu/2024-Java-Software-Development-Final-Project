package src_Compiler;

public class Term {
    private boolean isIdent;
    private String IdentName;

    private Integer valType;
    private String val;

    private Integer addrs;
    // private Val ObjVal = new Val();

    Term() {
        
    }

    Term(String[] Dump) {
        isIdent = true;
        IdentName = Dump[1];
        valType = Api.getIntType(Dump[2]);
        val = null;
        addrs = Integer.parseInt(Dump[3]);
    }

    Term(boolean isIdent, String IdentName, Integer valType, String val, Integer addrs) {
        this.addrs = addrs;
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
            if(val == null) this.val = Api.getDefualtVal(valType);
        }
        // ObjVal.setType(this.valType);
        // ObjVal.setVal(this.val);
    }

    void print() {
        System.out.printf(",%b, %s, %d, %s, \n", isIdent, IdentName, valType, val);
    }

    void setVal(String val) {
        this.val = val;
        // ObjVal.setVal(this.val);
    }
    void setValType(String type) {
        valType = Api.getIntType(type);
        // ObjVal.setType(this.valType);
    }
    void setValType(int type) {
        this.valType = type;
        // ObjVal.setType(this.valType);
    }
    String getName() {
        return this.IdentName;
    }
    int getType() {
        return valType;
    }
    String getVal() {
        return val;
    }
    // Object getObjVal() {
    //     return ObjVal.getVal();
    // }

    void NOT() {
        Boolean tmp = Api.converBool(this);
        this.val = String.valueOf(!tmp);
        // ObjVal.setVal(this.val);
    }
    void NEG() {
        String retVal = this.val.charAt(0) == '-' ? this.val.substring(1) : ("-" + this.val);
        this.val = retVal;
        // ObjVal.setVal(this.val);
    }
    void MUL(Term otherTerm) {
        int mxType = Math.max(this.getType(),otherTerm.getType());
        switch(mxType) {
            case 3:
                this.val = String.valueOf(Api.converInt(this) * Api.converInt(otherTerm));
                break;
            case 4:
                this.val = String.valueOf(Api.converFloat(this) * Api.converFloat(otherTerm));
                break;
            default:
                System.out.println("WTF MUL");
                break;
        }
        this.valType = mxType;
        // ObjVal.setVal(this.val);
    }
    void DIV(Term otherTerm) {
        int mxType = Math.max(this.getType(),otherTerm.getType());
        switch(mxType) {
            case 3:
                if(Api.converInt(otherTerm) == 0) {
                    System.out.println("DIV BY 0\n");
                    return;
                }
                this.val = String.valueOf(Api.converInt(this) / Api.converInt(otherTerm));
                break;
            case 4:
                if(Api.converFloat(otherTerm) == 0) {
                    System.out.println("DIV BY 0\n");
                    return;
                }
                this.val = String.valueOf(Api.converFloat(this) / Api.converFloat(otherTerm));
                break;
            default:
                System.out.println("WTF DIV");
                break;
        }
        this.valType = mxType;
        // ObjVal.setVal(this.val);
    }
    void REM(Term otherTerm) {
        int mxType = Math.max(this.getType(),otherTerm.getType());
        if(mxType >= 4) {
            System.out.println("WTF DEM");
            return;
        }
        switch(mxType) {
            case 3:
                this.val = String.valueOf(Api.converInt(this) % Api.converInt(otherTerm));
                break;
            default:
                System.out.println("WTF MUL");
                break;
        }
        this.valType = mxType;
        // ObjVal.setVal(this.val);
    }
    void ADD(Term otherTerm) {
        int mxType = Math.max(this.getType(),otherTerm.getType());
        switch(mxType) {
            case 3:
                this.val = String.valueOf(Api.converInt(this) + Api.converInt(otherTerm));
                break;
            case 4:
                this.val = String.valueOf(Api.converFloat(this) + Api.converFloat(otherTerm));
                break;
            case 5:
                this.val = this.val + otherTerm.val;
                break;
            default:
                System.out.println("WTF ADD");
                break;
        }
        this.valType = mxType;
        // ObjVal.setVal(this.val);
    }
    void SUB(Term otherTerm) {
        int mxType = Math.max(this.getType(),otherTerm.getType());
        switch(mxType) {
            case 3:
                this.val = String.valueOf(Api.converInt(this) - Api.converInt(otherTerm));
                break;
            case 4:
                this.val = String.valueOf(Api.converFloat(this) - Api.converFloat(otherTerm));
                break;
            default:
                System.out.println("WTF SUB");
                break;
        }
        this.valType = mxType;
        // ObjVal.setVal(this.val);
    }
    void SHR(Term otherTerm) {
        if(!Api.isIntegerType(this.getType()) || !Api.isIntegerType(otherTerm.getType())){
            System.out.println("\nWTF\n");
            return;
        }
        this.val = String.valueOf(Api.converInt(this) >> Api.converInt(otherTerm));
        this.valType = 3;
    }
    void SHL(Term otherTerm) {
        if(!Api.isIntegerType(this.getType()) || !Api.isIntegerType(otherTerm.getType())){
            System.out.println("\nWTF\n");
            return;
        }
        this.val = String.valueOf(Api.converInt(this) << Api.converInt(otherTerm));
        this.valType = 3;
    }
    void GTR(Term otherTerm) {
        int mxType = Math.max(this.getType(),otherTerm.getType());
        switch(mxType) {
            case 1:
                this.val = String.valueOf(Api.converInt(this) > Api.converInt(otherTerm));
                break;
            case 3:
                this.val = String.valueOf(Api.converInt(this) > Api.converInt(otherTerm));
                break;
            case 4:
                this.val = String.valueOf(Api.converFloat(this) > Api.converFloat(otherTerm));
                break;
            case 5:
                this.val = String.valueOf(this.val.compareTo(otherTerm.val) > 0);
            default:
                System.out.println("WTF MUL");
                break;
        }
        this.valType = 1;
        // ObjVal.setVal(this.val);
    }
    void LEQ(Term otherTerm) {
        String retVal = "";
        Term term1,term2;
        if(this.valType < otherTerm.valType) {
            term1 = otherTerm;
            term2 = this;
        }
        else{
            term1 = this;
            term2 = otherTerm;
        }
        if(term1.valType == 5){
            if(term2.valType != 5){
                System.out.println("WTF LEQ");
            }
            retVal = term1.equals(term2) ? "true" : "false";
        }
        int mxType = Math.max(this.getType(),otherTerm.getType());
        switch(mxType) {
            case 1:
                retVal = String.valueOf(Api.converBool(term1) == Api.converBool(otherTerm));
                break;
            case 3:
                retVal = String.valueOf(Api.converInt(term1) == Api.converInt(otherTerm));
                break;
            case 4:
                retVal = String.valueOf(Api.converFloat(term1) == Api.converFloat(otherTerm));
                break;
            default:
                System.out.println("WTF MUL");
                break;
        }
        this.val = retVal;
        this.valType = 1;
    }
    void NEQ(Term otherTerm) {
        this.LEQ(otherTerm);
        this.val = String.valueOf(!Boolean.valueOf(this.val));
    }
    void BAN(Term otherTerm) {
        this.val = String.valueOf(Api.converInt(this) & Api.converInt(otherTerm));
        this.valType = 3;
    }
    void BXO(Term otherTerm) {
        this.val = String.valueOf(Api.converInt(this) ^ Api.converInt(otherTerm));
        this.valType = 3;
    }
    void BOR(Term otherTerm) {
        this.val = String.valueOf(Api.converInt(this) | Api.converInt(otherTerm));
        this.valType = 3;
    }
    void LAN(Term otherTerm) {
        this.val = String.valueOf(Api.converBool(this) && Api.converBool(otherTerm));
        this.valType = 1;
        // ObjVal.setVal(this.val);
    }
    void LOR(Term otherTerm) {
        this.val = String.valueOf(Api.converBool(this) || Api.converBool(otherTerm));
        this.valType = 1;
        // ObjVal.setVal(this.val);
    }
    void EQL_ASSIGN(Term otherTerm) {
        this.val = otherTerm.val;
    }
    void ADD_ASSIGN(Term otherTerm) {
        this.ADD(otherTerm);
    }
    void SUB_ASSIGN(Term otherTerm) {
        this.SUB(otherTerm);
    }
    void MUL_ASSIGN(Term otherTerm) {
        this.MUL(otherTerm);
    }
    void DIV_ASSIGN(Term otherTerm) {
        this.DIV(otherTerm);
    }
    void REM_ASSIGN(Term otherTerm) {
        this.REM(otherTerm);
    }
    void SHR_ASSIGN(Term otherTerm) {
        this.SHR(otherTerm);
    }
    void SHL_ASSIGN(Term otherTerm) {
        this.SHL(otherTerm);
    }
    void BAN_ASSIGN(Term otherTerm) {
        this.BAN(otherTerm);
    }
    void BXO_ASSIGN(Term otherTerm) {
        this.BXO(otherTerm);
    }
    void BOR_ASSIGN(Term otherTerm) {
        this.BOR(otherTerm);
    }
}