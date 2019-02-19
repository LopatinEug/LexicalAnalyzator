package api;

import model.Element;

import java.util.ArrayList;

public interface LexicalAnalyzer {

    ArrayList<Element> analyze(String expression) throws LexicalAnalyzerExeption;
}
