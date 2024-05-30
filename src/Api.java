package src;

import java.util.HashMap;

public class Api {
    private static HashMap<String, Integer> typeStrToInt = new HashMap<String,Integer>(){{
        put("bool", 1);
        put("char", 2);
        put("int", 3);
        put("float", 4);
        put("string", 5);
    }};
    private static HashMap<Integer, String> defualtVal = new HashMap<Integer, String>(){{
        put(1, "false");
        put(2, "");
        put(3, "0");
        put(4, "0.0");
        put(5, "");
    }};
    private static HashMap<String, Term> keywordTable = new HashMap<String, Term>(){{
        put("endl", new Term(true, "endl", 5, "\n"));
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

    static String convertType(String type, Term term) {
        int nowType = term.getType();
        Val val = new Val(nowType, term.getVal());
        int targetType = getIntType(type);
        String strVal = term.getVal();

        String ret = null;
        switch (nowType) {
            case 1:
                ret = String.valueOf(Boolean.valueOf(strVal));
                break;
            case 2:
                ret = String.valueOf(strVal.charAt(strVal.charAt(0)));
                break;
            case 3:
                ret = String.valueOf(Integer.valueOf(strVal));
                break;
            case 4:
                ret = String.valueOf(Float.valueOf(strVal));
                break;
            case 5:
                ret = strVal;
                break;
        }

        return ret;
    }
}