package src;

public class Val {
    private int type;
    private Boolean boolVal;
    private Character charVal;
    private Integer intVal;
    private Float floatVal;
    private String stringVal;
    
    Val(int type, String val) {
        setType(type);
        setVal(val);
    }

    void setType(int type) {
        this.type = type;
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
}