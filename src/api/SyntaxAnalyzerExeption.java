package api;

public class SyntaxAnalyzerExeption extends Exception {
    int position;

    public SyntaxAnalyzerExeption(String message) {
        super(message);
    }

    public SyntaxAnalyzerExeption(String message, int pos) {
        super(message);
        position = pos;
    }

    public int getPosition() {
        return position;
    }
}
