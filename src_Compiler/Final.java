package src_Compiler;

public class Final {
    static String[] inputLines;

    public static void main(String[] argv) {
        inputLines = Reader.read(argv);
        Solver solver = new Solver(inputLines);
        String output = solver.solve();
        System.out.printf("output = \n%s", output);
    }
}