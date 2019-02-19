package app;


import api.LexicalAnalyzer;
import api.LexicalAnalyzerExeption;
import api.LexicalAnalyzerProvider;
import api.SyntaxAnalyzerExeption;
import impl.SyntaxAnalyzer;

import javax.swing.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleAnalyzerFrame extends JFrame {
    private JTextField textField;
    private JLabel label;
    private JLabel message;
    private JButton syntaxAnalyze;
    private JButton lexicalAnalyze;


    public SimpleAnalyzerFrame() {
        super("Syntax Analyzer");

        textField = new JTextField(40);
        label = new JLabel("Enter expression");
        message = new JLabel();
        syntaxAnalyze = new JButton("Syntax");
        lexicalAnalyze = new JButton("Lexical");


        syntaxAnalyze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SyntaxAnalyzer.analyze(textField.getText());
                    label.setText("Syntax analyze was successful");
                    message.setText("<html> <font color=green> " + textField.getText() + "</font> </html>");
                    pack();
                } catch (LexicalAnalyzerExeption lexicalAnalyzerExeption) {
                    label.setText(lexicalAnalyzerExeption.getMessage());
                    int position = lexicalAnalyzerExeption.getPosition();
                    String firstText, secondText, text;
                    firstText = textField.getText().substring(0, position - 1);
                    secondText = textField.getText().substring(position, textField.getText().length());
                    text = "<html>" + firstText
                            + "<font color=red>" + textField.getText().charAt(position - 1) + "</font>"
                            + secondText + "</html>";
                    message.setText(text);
                    pack();


                } catch (SyntaxAnalyzerExeption syntaxAnalyzerExeption) {
                    label.setText(syntaxAnalyzerExeption.getMessage());
                    int position = syntaxAnalyzerExeption.getPosition();
                    String firstText, secondText, text;
                    firstText = textField.getText().substring(0, position - 1);
                    secondText = textField.getText().substring(position, textField.getText().length());
                    text = "<html>" + firstText
                            + "<font color=red>" + textField.getText().charAt(position - 1) + "</font>"
                            + secondText + "</html>";
                    message.setText(text);
                    pack();
                }
            }
        });

        lexicalAnalyze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LexicalAnalyzer lexicalAnalyzer = LexicalAnalyzerProvider.create();
                    lexicalAnalyzer.analyze(textField.getText());
                    label.setText("Lexical analyze was successful");
                    message.setText("<html> <font color=green> " + textField.getText() + "</font> </html>");
                    pack();
                } catch (LexicalAnalyzerExeption lexicalAnalyzerExeption) {
                    label.setText(lexicalAnalyzerExeption.getMessage());
                    int position = lexicalAnalyzerExeption.getPosition();
                    String firstText, secondText, text;
                    firstText = textField.getText().substring(0, position - 1);
                    secondText = textField.getText().substring(position, textField.getText().length());
                    text = "<html>" + firstText
                            + "<font color=red> " + textField.getText().charAt(position - 1) + " </font>"
                            + secondText + "</html>";
                    message.setText(text);
                    pack();
                }
            }
        });


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        panel.add(label);
        panel.add(message);
        buttonsPanel.add(textField);
        buttonsPanel.add(syntaxAnalyze);
        buttonsPanel.add(lexicalAnalyze);
        panel.add(buttonsPanel);
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SimpleAnalyzerFrame app = new SimpleAnalyzerFrame();
        app.setVisible(true);
        app.pack();
    }


}
