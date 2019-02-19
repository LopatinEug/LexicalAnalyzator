import api.LexicalAnalyzer;
import api.LexicalAnalyzerExeption;
import api.LexicalAnalyzerProvider;
import model.Element;

import java.util.ArrayList;

public class AnalizatorTest {
    public static void main(String[] args) {
        LexicalAnalyzer analyzer=LexicalAnalyzerProvider.create();
        ArrayList<Element> elements;
        try {
         elements= analyzer.analyze("cos(45)+45+4.6+21331^ a +2 *bc + ( ) +2");
         System.out.println();
        } catch (LexicalAnalyzerExeption syntaxAnalyzerExeption) {
            System.out.println(syntaxAnalyzerExeption.getMessage());
        }
        System.out.println();
    }
}
