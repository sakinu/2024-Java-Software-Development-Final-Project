package src_Parser;

import java.util.List;

public class Final_Parser {
    public static void main(String argv[]) {
        String[][] input = Reader.read(argv);
        Solver.setTokens(input);
        List<String> output = Solver.solve();
        System.out.println("\nfinish");
        Writer.write(argv, output);
    }
}