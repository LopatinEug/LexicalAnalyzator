package api;

public class LexicalAnalyzerExeption extends Exception {
    int position;

    public LexicalAnalyzerExeption(String message) {
        super(message);
    }

    public LexicalAnalyzerExeption(String message, int pos) {
        super(message);
        position = pos;
    }

    public int getPosition() {
        return position;
    }
}
