package src_Parser;

public class Writer {
    static void write(String[][] output) {
        for(int i=0; i<output.length; i++) {
            for(int j=0; j<output[i].length; j++) {
                System.out.print(output[i][j]);
                if(j == output[i].length-1) System.out.println();
                else System.out.print(" ");
            }
        }
    }
}
