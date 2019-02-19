import api.ExpressionAssertor;
import api.LexicalAnalyzerExeption;

public class AsssertTest {
    public static void main(String[] args) throws LexicalAnalyzerExeption {
        ExpressionAssertor assertor = new ExpressionAssertor();
        assertor.assertExpression("123");

    }
}
