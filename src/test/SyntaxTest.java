import api.LexicalAnalyzerExeption;
import api.SyntaxAnalyzerExeption;
import impl.SyntaxAnalyzer;

public class SyntaxTest {
    public static void main(String[] args) throws LexicalAnalyzerExeption, SyntaxAnalyzerExeption {
        SyntaxAnalyzer.analyze("3+3+3");
        System.out.println();
    }
}
