package impl;

import api.ExpressionAssertor;
import api.LexicalAnalyzer;
import api.LexicalAnalyzerExeption;
import model.*;
import model.Number;

import java.util.ArrayList;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
    @Override
    public ArrayList<Element> analyze(String expression) throws LexicalAnalyzerExeption {
        ExpressionAssertor assertor = new ExpressionAssertor();
        assertor.assertExpression(expression);

        ArrayList<Element> result = new ArrayList<>();
        int head = 0;
        char current;
        char[] chars = expression.toCharArray();

        boolean var = false;
        boolean number = false;
        boolean zero = false;
        boolean point = false;
        StringBuilder buffer = new StringBuilder();

        while (true) {
            if (head == chars.length) break;
            current = chars[head];

            if (var) {
                if (Character.isLetterOrDigit(current)) {
                    buffer.append(current);

                    if (head == chars.length - 1) {
                        Function.functionValue[] functions = Function.functionValue.class.getEnumConstants();
                        boolean func = false;
                        for (Function.functionValue f : functions) {
                            if (buffer.toString().equals(f.toString())) {
                                result.add(new Function(buffer.toString(), head - buffer.toString().length() + 1));
                                func = true;
                                buffer.setLength(0);
                                break;
                            }
                        }
                        if (!func) {
                            result.add(new Variable(buffer.toString(), head - buffer.toString().length() + 1));
                        }
                        var = false;
                    }
                } else if (current == '.') {
                    throw new LexicalAnalyzerExeption("Unexpected token " + current + " at position: " + (head + 1), head + 1);
                } else {
                    Function.functionValue[] functions = Function.functionValue.class.getEnumConstants();
                    boolean func = false;
                    for (Function.functionValue f : functions) {
                        if (buffer.toString().equals(f.toString())) {
                            result.add(new Function(buffer.toString(), head - buffer.toString().length()+1));
                            func = true;
                            buffer.setLength(0);
                            break;
                        }
                    }
                    if (!func) {
                        result.add(new Variable(buffer.toString(), head - buffer.toString().length()+1));
                    }
                    var = false;
                    buffer.setLength(0);
                }


            } else if (number) {

                if (Character.isDigit(current)) {
                    if (!zero) {
                        buffer.append(current);
                        if (head == chars.length - 1) {
                            result.add(new Number(buffer.toString(), head - buffer.toString().length() + 1));
                        }
                    } else {
                        throw new LexicalAnalyzerExeption("Unexpected token " + current + " at position: " + (head + 1), head + 1);
                    }
                } else if (current == '.') {
                    if (head == chars.length - 1) {
                        throw new LexicalAnalyzerExeption("Unexpected token " + current + " at position: " + (head + 1), head + 1);
                    }
                    if (point) {
                        throw new LexicalAnalyzerExeption("Unexpected token " + current + " at position: " + (head + 1), head + 1);
                    } else {
                        buffer.append(current);
                        point = true;
                        zero = false;
                    }
                } else if (Character.isLetter(current)) {
                    throw new LexicalAnalyzerExeption("Unexpected token " + current + " at position: " + (head + 1), head + 1);
                } else {
                    if (buffer.charAt(buffer.length() - 1) == '.') {
                        throw new LexicalAnalyzerExeption("Unexpected token " + current + " at position: " + (head + 1), head + 1);
                    }
                    point = false;
                    zero = false;
                    result.add(new Number(buffer.toString(), head - buffer.toString().length() + 1));
                    buffer.setLength(0);
                    number = false;
                }

            } else {
                if (Character.isLetter(chars[head])) {
                    if (head == chars.length - 1) {
                        result.add(new Variable(String.valueOf(current), head + 1));
                    } else {
                        buffer.append(current);
                        var = true;
                    }

                } else if (Character.isDigit(current)) {
                    if (current == '0') {
                        if (head == chars.length - 1) {
                            result.add(new Number(String.valueOf(current), head + 1));
                        } else {
                            buffer.append(current);
                            number = true;
                            zero = true;
                        }
                    } else {
                        if (head == chars.length - 1) {
                            result.add(new Number(String.valueOf(current), head + 1));
                        } else {
                            buffer.append(current);
                            number = true;
                        }
                    }
                } else if (current == '.') {
                    throw new LexicalAnalyzerExeption("Unexpected token " + current + " at position: " + (head + 1), head + 1);
                }
            }

            if (!Character.isWhitespace(current) && !Character.isLetterOrDigit(current) && (current != '.')) {
                if (current == '-' || current == '+') {
                    result.add(new Sign(String.valueOf(current), head + 1));
                } else if (current == '(') {
                    result.add(new LeftBracket(String.valueOf(current), head + 1));
                } else if (current == ')') {
                    result.add(new RightBracket(String.valueOf(current), head + 1));
                } else {
                    result.add(new Operation(String.valueOf(current), head + 1));
                }
            }

            head++;
        }

        return result;
    }
}
