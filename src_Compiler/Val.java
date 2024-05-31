package src_Compiler;

public class Val {
    private int type;
    private Boolean boolVal;
    private Character charVal;
    private Integer intVal;
    private Float floatVal;
    private String stringVal;

    Val() {

    }
    
    Val(int type, String val) {
        setType(type);
        setVal(val);
    }

    void setType(int type) {
        Integer preType = this.type;
        this.type = type;
        if(preType != null) {
            Object preVal = getVal(preType);
            setVal(preType,null);
            if(preVal != null) setVal(preVal.toString());
        }
    }

    Object getVal() {
        switch (type) {
            case 1:
                return boolVal;
            case 2:
                return charVal;
            case 3:
                return intVal;
            case 4:
                return floatVal;
            case 5:
                return stringVal;
        }
        return null;
    }

    Object getVal(int type) {
        switch (type) {
            case 1:
                return boolVal;
            case 2:
                return charVal;
            case 3:
                return intVal;
            case 4:
                return floatVal;
            case 5:
                return stringVal;
        }
        return null;
    }

    void setVal(String val) {
        switch (type) {
            case 1:
                boolVal = Boolean.valueOf(val);
                break;
            case 2:
                charVal = Character.valueOf(val.charAt(0));
                break;
            case 3:
                intVal = Integer.valueOf(val);
                break;
            case 4:
                floatVal = Float.valueOf(val);
                break;
            case 5:
                stringVal = val;
                break;
        }
    }

    void setVal(int type, String val) {
        switch (type) {
            case 1:
                boolVal = null;
                break;
            case 2:
                boolVal = null;
                break;
            case 3:
                boolVal = null;
                break;
            case 4:
                boolVal = null;
                break;
            case 5:
                boolVal = null;
                break;
        }
    }
}