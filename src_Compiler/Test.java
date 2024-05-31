package src_Compiler;

import java.lang.Float;
import java.math.BigDecimal;

public class Test {
    public static void main(String[] argv) {
        String x = "37.6";
        System.out.println((int)Math.ceil(Float.parseFloat(x)));
    }
}