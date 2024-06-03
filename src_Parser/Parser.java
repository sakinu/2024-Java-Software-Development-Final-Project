package src_Parser;

public class Parser {
    public static void main(String argv[]) {
        String[][] input = Reader.read();
        String[][] output = Solver.solve(input);
        Writer.write(output);
    }
}