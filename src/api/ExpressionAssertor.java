package api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionAssertor {

    public void assertExpression(String s) throws LexicalAnalyzerExeption {
        if (s.equals("")) {
            String message = "There must be an expression";
            throw new LexicalAnalyzerExeption(message);
        }

        Pattern p = Pattern.compile("[a-z0-9+*/%^. ()-]+");
        Matcher m = p.matcher(s);

        if (!m.matches()) {
            int position = m.find() ? m.end() + 1 : 1;
            String message = "Invalid symbol at position: " + position;
            throw new LexicalAnalyzerExeption(message, position);
        }
    }

}
