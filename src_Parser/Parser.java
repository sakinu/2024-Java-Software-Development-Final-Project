package src_Parser;

public class Parser {
    public static void main(String argv[]) {
        String[][] input = Reader.read();
        Solver.setTokens(input);
        String[][] output = Solver.solve();
        System.out.println("\nfinish");
        Writer.write(output);
    }
}