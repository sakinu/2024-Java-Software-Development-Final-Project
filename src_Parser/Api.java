package src_Parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Api {
    private static HashMap<String, Integer> typeStrToInt = new HashMap<String,Integer>(){{
        put("bool", 1);
        put("char", 2);
        put("int", 3);
        put("float", 4);
        put("string", 5);
    }};
    private static HashMap<Integer, String> defualtVal = new HashMap<Integer, String>(){{
        put(1, "0");
        put(2, "");
        put(3, "0");
        put(4, "0.0");
        put(5, "");
    }};
    private static HashSet<Integer> integerType = new HashSet<>(){{
        add(1);
        add(2);
        add(3);
    }};
    private static HashSet<Integer> numberType = new HashSet<>(){{
        add(1);
        add(2);
        add(3);
        add(4);
    }};
    private static HashMap<String, Term> keywordTable = new HashMap<String, Term>(){{
        put("endl", new Term(true, "endl", 5, "\n", -1));
    }};

    static boolean isNumber(String str) {
        for(int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if(!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    static boolean isNumberType(int type) {
        return numberType.contains(type);
    }

    static boolean isIntegerType(int type) {
        return integerType.contains(type);
    }

    static Integer getIntType(int type) {
        return type;
    }

    static Integer getIntType(String type) {
        return isNumber(type) ? Integer.parseInt(type) : typeStrToInt.get(type);
    }
    
    static String getDefualtVal(int type) {
        if(defualtVal.containsKey(type)) {
            return defualtVal.get(type);
        }
        return null;
    }

    static Integer getKeywordType(String Ident) {
        if(keywordTable.containsKey(Ident)) {
            return keywordTable.get(Ident).getType();
        }
        return null;
    }

    static String getKeywordVal(String Ident) {
        if(keywordTable.containsKey(Ident)) {
            return keywordTable.get(Ident).getVal();
        }
        return null;
    }
    
    static Boolean getConvertBoolean(Integer nowType, String nowVal) {
        Boolean ret = null;
        switch (nowType) {
            case 1:
                ret = nowVal != "0";
                break;
            // case 2:
            //     ret = String.valueOf(strVal.charAt(0));
            //     break;
            case 3:
                ret = (Integer.parseInt(nowVal) != 0);
                break;
            case 4:
                ret = (Float.parseFloat(nowVal) != 0);      //$
                break;
            case 5:
                if(nowVal.equals("0"))
                    ret = false;
                else
                    ret = true;
                break;
        }
        return ret;
    }
    static Boolean converBool(Term term){         //
        return getConvertBoolean(term.getType(), term.getVal());
    }
    static Boolean converBool(int type, String val){         //
        return getConvertBoolean(type, val);
    }
    static Integer converInt(Term term){         //
        String nowVal = term.getVal();
        int nowType = term.getType();

        Integer ret = null;
        switch (nowType) {
            case 1:
                if(nowVal.equals("0") || nowVal.equals("0") || nowVal.equals("-0"))
                    ret = 0;
                else
                    ret = 1;
                break;
            case 3:
                if(nowVal.contains(".")) {
                    nowVal = String.valueOf((int)Math.floor(Float.parseFloat(nowVal)));
                }
                ret = Integer.valueOf(nowVal);
                break;
            case 4:
                ret = Float.valueOf(nowVal).intValue();
                break;
            case 5:
                ret = Integer.valueOf(nowVal);
                break;
        }
        return ret;
    }
    static Float converFloat(Term term){         //
        String nowVal = term.getVal();
        int nowType = term.getType();

        Float ret = null;
        switch (nowType) {
            case 1:
                if(nowVal.equals("0") || nowVal.equals("-0"))
                    ret = 0f;
                else
                    ret = 1f;
                break;
            // case 2:
            //     ret = String.valueOf(strVal.charAt(0));
            //     break;
            case 3:
                ret = Float.valueOf(nowVal);
                break;
            case 4:
                ret = Float.valueOf(nowVal);
                break;
            case 5:
                ret = Float.valueOf(nowVal);
                break;
        }
        return ret;
    }
    static String converString(Term term){         //
        String nowVal = term.getVal();
        int nowType = term.getType();

        String ret = null;
        switch (nowType) {
            case 1:
                if(nowVal.equals("0") || nowVal.equals("-0"))
                    ret = "0";
                else
                    ret = "1";
                break;
            // case 2:
            //     ret = String.valueOf(strVal.charAt(0));
            //     break;
            case 3:
                ret = nowVal;
                break;
            case 4:
                ret = nowVal;
                break;
            case 5:
                ret = nowVal;
                break;
        }
        return ret;
    }

    static String converOutput(Term term) {        //getString
        int nowType = term.getType();
        String val = term.getVal();

        String ret = null;
        switch (nowType) {
            case 1:
                ret = Api.converBool(term) ? "1" : "0";
                break;
            case 2:
                ret = String.valueOf(val.charAt(0));
                break;
            case 3:
                ret = String.valueOf(Integer.valueOf(val));
                break;
            case 4:
                ret = String.valueOf(Float.valueOf(val));
                if(ret.contains(".") && ret.length() > 7) ret = ret.substring(0, 7);
                break;
            case 5:
                ret = val;
                break;
        }
        return ret;
    }

    // static String getIdentNameUseAddrs(Integer addrs) {
    //     return Preprocessing.getIdentUseAddrs(addrs).getName();
    // }

    // static Integer getIdentType(Integer IdentAddrs, String IdentName) {
    //     Integer ret = Api.getKeywordType(IdentName);
    //     if(ret == null) ret = Preprocessing.getIdentUseAddrs(IdentAddrs).getType();
    //     return ret;
    // }

    static Integer getAddrsFrom80(String[] Tokens) {          //i n t a l f
        return Integer.parseInt(Tokens[4].substring(0, Tokens[4].length()-1));
    }

    static Integer getCreateLevel(String Tokens[]) {
        return Integer.valueOf(Tokens[6].substring(0, Tokens[6].length()-1));
    }

    static int getLineType(Integer lineId, String[] Tokens) {
        if(Tokens.length == 0 || Tokens.length == 1 && Tokens[0].isEmpty()) return 0;
        if(Tokens[0].equals(">")) {
            if(Tokens[1].equals("Create")) {
                return 1;
            }
            if(Tokens[1].equals("Dump")) {
                return 2;
            }
            if(Tokens[1].equals("Insert")) {
                return 5;
            }
        }
        if(Tokens[0].equals("Index")) {
            return 7;
        }
        if(Api.isNumber(Tokens[0]) && Tokens.length == 6){
            return 8;
        }

        if(Tokens[0].equals("func:")) {
            return 11;
        }
        if(Tokens[0].equals("RETURN")) {
            return 13;
        }
        if(Tokens[0].equals("IF")) {
            return 21;
        }
        if(Tokens[0].equals("ELSE")) {
            return 23;
        }
        if(Tokens[0].equals("WHILE")) {
            return 25;
        }
        if(Tokens[0].equals("for")) {
            return 27;
        }

        if(Tokens[0].equals("NOT")) {
            return 31;
        }
        if(Tokens[0].equals("BNT")) {
            return 32;
        }
        if(Tokens[0].equals("NEG")) {
            return 33;
        }
        if(Tokens[0].equals("INC_ASSIGN")) {
            return 35;
        }
        if(Tokens[0].equals("DEC_ASSIGN")) {
            return 36;
        }
        if(Tokens[0].equals("MUL")) {
            return 38;
        }
        if(Tokens[0].equals("DIV")) {
            return 39;
        }
        if(Tokens[0].equals("REM")) {
            return 40;
        }
        if(Tokens[0].equals("ADD")) {
            return 42;
        }
        if(Tokens[0].equals("SUB")) {
            return 43;
        }
        if(Tokens[0].equals("SHR")) {
            return 45;
        }
        if(Tokens[0].equals("SHL")) {
            return 46;
        }
        if(Tokens[0].equals("GTR")) {
            return 48;
        }
        if(Tokens[0].equals("LES")) {
            return 49;
        }
        if(Tokens[0].equals("GEQ")) {
            return 50;
        }
        if(Tokens[0].equals("LEQ")) {
            return 51;
        }
        if(Tokens[0].equals("EQL")) {
            return 53;
        }
        if(Tokens[0].equals("NEQ")) {
            return 54;
        }
        if(Tokens[0].equals("BAN")) {
            return 56;
        }
        if(Tokens[0].equals("BXO")) {
            return 57;
        }
        if(Tokens[0].equals("BOR")) {
            return 58;
        }
        if(Tokens[0].equals("LAN")) {
            return 60;
        }
        if(Tokens[0].equals("LOR")) {
            return 61;
        }
        if(Tokens[0].equals("Cast")) {
            return 70 + Api.getIntType(Tokens[2]);
        }

        if(Tokens[0].equals("EQL_ASSIGN")) {
            return 81;
        }
        if(Tokens[0].equals("ADD_ASSIGN")) {
            return 82;
        }
        if(Tokens[0].equals("SUB_ASSIGN")) {
            return 83;
        }
        if(Tokens[0].equals("MUL_ASSIGN")) {
            return 84;
        }
        if(Tokens[0].equals("DIV_ASSIGN")) {
            return 85;
        }
        if(Tokens[0].equals("REM_ASSIGN")) {
            return 86;
        }
        if(Tokens[0].equals("SHR_ASSIGN")) {
            return 87;
        }
        if(Tokens[0].equals("SHL_ASSIGN")) {
            return 88;
        }
        if(Tokens[0].equals("BAN_ASSIGN")) {
            return 89;
        }
        if(Tokens[0].equals("BOR_ASSIGN")) {
            return 90;
        }
        if(Tokens[0].equals("BXO_ASSIGN")) {
            return 91;
        }
        if(Tokens[0].equals("INC_ASSIGN")) {
            return 92;
        }
        if(Tokens[0].equals("DEC_ASSIGN")) {
            return 93;
        }

        if(Tokens[0].equals("cout")){
            return 101;
        }
        

        if(Tokens[0].equals("IDENT")) {
            return 110;
        }
        if(Tokens[0].equals("BOOL_LIT")) {
            return 112;
        }
        if(Tokens[0].equals("INT_LIT")) {
            return 113;
        }
        if(Tokens[0].equals("FLOAT_LIT")) {
            return 114;
        }
        if(Tokens[0].equals("STR_LIT")) {
            return 115;
        }
        return -1;
    }
}