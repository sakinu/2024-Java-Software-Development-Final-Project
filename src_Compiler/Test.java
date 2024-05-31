package src_Compiler;

import java.lang.Float;
import java.math.BigDecimal;

public class Test {
    public static void main(String[] argv) {
        Integer[] arr = new Integer[5];
        for(int i=0;i<5;i++) arr[i] = 0;
        f(arr);
        for(int i=0;i<5;i++) System.out.println(arr[i]);
    }
    static void f(Integer arr[]) {
        for(int i=0; i<arr.length; i++) arr[i] = 5;
    }
}