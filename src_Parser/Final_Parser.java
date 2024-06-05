package src_Parser;

import java.util.List;

public class Final_Parser {
    public static void main(String argv[]) {
        argv = new String[2];
        argv[0] = "data_1/subtask03-precedenc/";
        argv[1] = "testcase01.out";
        String[][] input = Reader.read(argv);
        Solver.setTokens(input);
        List<String> output = Solver.solve();
        System.out.println("\nfinish");
        Writer.write(argv, output);
    }
}