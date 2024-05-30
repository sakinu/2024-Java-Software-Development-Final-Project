package src;

public class Final {
    static String[] inputLines;

    public static void main(String[] argv) {
        inputLines = Reader.read(argv);
        Solver solver = new Solver(inputLines);
        solver.solve();
    }
}