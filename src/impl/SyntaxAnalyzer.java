package impl;

import api.LexicalAnalyzer;
import api.LexicalAnalyzerExeption;
import api.LexicalAnalyzerProvider;
import api.SyntaxAnalyzerExeption;
import model.*;
import model.Number;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SyntaxAnalyzer {
    public static  ArrayList<Element> analyze(String expression) throws LexicalAnalyzerExeption, SyntaxAnalyzerExeption {
        LexicalAnalyzer analyzer = LexicalAnalyzerProvider.create();
        Logger logger=Logger.getLogger(SyntaxAnalyzer.class.getName());
        ArrayList<Element> elements = analyzer.analyze(expression);
        logger.log(Level.INFO,"Lexical analyze was successful");
        analyzeRec(elements,false);
        logger.log(Level.INFO,"Syntax analyze was successful");
        return elements;
    }

    private static int analyzeRec(ArrayList<Element> elements, final boolean brackets) throws SyntaxAnalyzerExeption {
        //check first
        int head = 0;
        Element current = elements.get(head);
        if (!(current instanceof First))
            throw new SyntaxAnalyzerExeption(current.getValue() + " cant be on position 1", current.getPosition());
        //
        Element next;
        while (true) {

            if (head == elements.size()) {
                if (brackets == true) {
                    throw new SyntaxAnalyzerExeption("All brackets must be closed", current.getPosition());
                } else {
                    return head;
                }
            }

            current = elements.get(head);

            if (head == elements.size() - 1) {
                if (!(current instanceof Last))
                    throw new SyntaxAnalyzerExeption(current.getValue() + " cant be on position " + current.getPosition(), current.getPosition());
                else {
                    if (current instanceof RightBracket) {
                        if (brackets == true) {
                            return head + 2;
                        } else {
                            throw new SyntaxAnalyzerExeption(current.getValue() + " on position " + current.getPosition()
                                    + " cant be without '('", current.getPosition());
                        }
                    } else {
                        if (brackets == true) {
                            throw new SyntaxAnalyzerExeption("All brackets must be closed", current.getPosition());
                        } else {
                            return elements.size();
                        }
                    }
                }
            }

            next = elements.get(head + 1);

            if (current instanceof Number || current instanceof Variable) {
                if (next instanceof LeftBracket || next instanceof Number || next instanceof Variable || next instanceof Function) {
                    throw new SyntaxAnalyzerExeption(next.getValue() + " cant be after "
                            + current.getValue() + " at position: "
                            + next.getPosition()
                            , next.getPosition());
                } else {
                    head++;
                    continue;
                }
            }

            if (current instanceof Operation || current instanceof Sign) {
                if (next instanceof RightBracket || next instanceof Operation || next instanceof Sign) {
                    throw new SyntaxAnalyzerExeption(next.getValue() + " cant be after "
                            + current.getValue() + " at position: "
                            + next.getPosition()
                            , next.getPosition());
                } else {
                    head++;
                    continue;
                }
            }

            if (current instanceof Function) {
                if (!(next instanceof LeftBracket)) {
                    throw new SyntaxAnalyzerExeption(next.getValue() + " cant be after "
                            + current.getValue() + " at position: "
                            + next.getPosition()
                            , next.getPosition());
                } else {
                    head++;
                    continue;
                }
            }

            if (current instanceof LeftBracket) {
                if (!(next instanceof First)) {
                    throw new SyntaxAnalyzerExeption(next.getValue() + " cant be after "
                            + current.getValue() + " at position: "
                            + next.getPosition()
                            , next.getPosition());
                } else {
                    head += analyzeRec(new ArrayList<>(elements.subList(head + 1, elements.size())), true);
                }
            }

            if (current instanceof RightBracket) {
                if (next instanceof LeftBracket || next instanceof Number || next instanceof Variable || next instanceof Function) {
                    throw new SyntaxAnalyzerExeption(next.getValue() + " cant be after "
                            + current.getValue() + " at position: "
                            + next.getPosition()
                            , next.getPosition());
                } else {
                    if (brackets == false) {
                        throw new SyntaxAnalyzerExeption(current.getValue() + " cant be after ", next.getPosition());
                    } else {
                        return head + 2;
                    }
                }
            }

        }
    }

}
