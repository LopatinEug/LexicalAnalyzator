package api;

import impl.LexicalAnalyzerImpl;

public class LexicalAnalyzerProvider {

    public static LexicalAnalyzer create() {
        //change implementation
        return new LexicalAnalyzerImpl();
    }
}
