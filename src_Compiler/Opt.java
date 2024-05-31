package src_Compiler;

import java.util.HashMap;

public class Opt {
    static Integer err;
    private static HashMap<Integer, Integer> termOptNeed = new HashMap<>(){{
        put(31, 1);
        put(32, 1);
        put(33, 1);
        put(35, 1);
        put(36, 1);
        put(38, 2);
        put(39, 2);
        put(40, 2);
        put(42, 2);
        put(43, 2);
        put(44, 2);
        put(45, 2);
        put(46, 2);
        put(48, 2);
        put(49, 2);
        put(50, 2);
        put(51, 2);
        put(53, 2);
        put(54, 2);
        put(56, 2);
        put(57, 2);
        put(58, 2);
        put(60, 2);
        put(61, 2);
        put(71, 1);
        put(72, 1);
        put(73, 1);
        put(74, 1);
        put(75, 1);
        put(81, 2);
        put(82, 2);
        put(83, 2);
        put(84, 2);
        put(85, 2);
        put(86, 2);
        put(87, 2);
        put(88, 2);
        put(89, 2);
        put(90, 2);
        put(91, 2);
        put(92, 2);
        put(93, 2);
    }};

    static Integer getTermNeed(int Type) {
        return termOptNeed.get(Type);
    }

    static Term getOptResult(int Type, Term[] Terms) {
        Term preTerm = Terms[0];
        switch (Type) {
            case 31:
                Terms[0].NOT();
                break;
            case 32:
                Terms[0].BNT();
                break;
            case 33:
                Terms[0].NEG();
                break;
            case 38:
                Terms[0].MUL(Terms[1]);
                break;
            case 39:
                Terms[0].DIV(Terms[1]);
                break;
            case 40:
                Terms[0].REM(Terms[1]);
                break;
            case 42:
                Terms[0].ADD(Terms[1]);
                break;
            case 43:
                Terms[0].SUB(Terms[1]);
                break;
            case 45:
                Terms[0].SHR(Terms[1]);
                break;
            case 46:
                Terms[0].SHL(Terms[1]);
                break;
            case 48:
                Terms[0].GTR(Terms[1]);
                break;
            // case 49:
            //     Terms[0].LES(Terms[1]);
            //     break;
            // case 50:
            //     Terms[0].GEQ(Terms[1]);
            //     break;
            // case 51:
            //     Terms[0].LEQ(Terms[1]);
            //     break;
            case 53:
                Terms[0].EQL(Terms[1]);
                break;
            case 54:
                Terms[0].NEQ(Terms[1]);
                break;
            case 56:
                Terms[0].BAN(Terms[1]);
                break;
            case 57:
                Terms[0].BXO(Terms[1]);
                break;
            case 58:
                Terms[0].BOR(Terms[1]);
                break;
            case 60:
                Terms[0].LAN(Terms[1]);
                break;
            case 61:
                Terms[0].LOR(Terms[1]);
                break;
                
            case 71:
                Terms[0].CastBool();
                break;
            case 73:
                Terms[0].CastInt();
                break;
            case 74:
                Terms[0].CastFloat();
                break;

            case 81:
                Terms[0].EQL_ASSIGN(Terms[1]);
                break;
            case 82:
                Terms[0].ADD_ASSIGN(Terms[1]);
                break;
            case 83:
                Terms[0].SUB_ASSIGN(Terms[1]);
                break;
            case 84:
                Terms[0].MUL_ASSIGN(Terms[1]);
                break;
            case 85:
                Terms[0].DIV_ASSIGN(Terms[1]);
                break;
            case 86:
                Terms[0].REM_ASSIGN(Terms[1]);
                break;
            case 87:
                Terms[0].SHR_ASSIGN(Terms[1]);
                break;
            case 88:
                Terms[0].SHL_ASSIGN(Terms[1]);
                break;
            case 89:
                Terms[0].BAN_ASSIGN(Terms[1]);
                break;
            case 90:
                Terms[0].BOR_ASSIGN(Terms[1]);
                break;
            case 91:
                Terms[0].BXO_ASSIGN(Terms[1]);
                break;
            // case 74:
            //     Terms[0].INC_ASSIGN(Terms[1]);
            //     break;
            // case 75:
            //     Terms[0].DEC_ASSIGN(Terms[1]);
            //     break;
            default:
                System.out.println("WTF, NO THIS Terms opts");
                break;
        }
        if(Type >= 63) {
            int preType = preTerm.getType();
            switch(preType) {
                case 1:
                    preTerm.setVal(String.valueOf(Api.converBool(Terms[0])));
                    break;
                case 3:
                    preTerm.setVal(String.valueOf(Api.converInt(Terms[0])));
                    break;
                case 4:
                    preTerm.setVal(String.valueOf(Api.converFloat(Terms[0])));
                    break;
                case 5:
                    preTerm.setVal(Api.converString(Terms[0]));
                    break;
                default:
                    System.out.println("WTF");
                    break;
            }
            return preTerm;
        }
        return Terms[0];
    }
}