package io.github.crosstools;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Lexer implements Iterator<Token> {

    private final String text;
    private char current;
    private int pos = 0;

    /**
     * Construct a new Lexer object
     * @param text The text string to lexically analyze
     * @throws Throwable Checked exception thrown if string text is null (it cannot be null!)
     */
    public Lexer(String text) throws Throwable {
        if (text == null) {
            throw new Throwable("String text cannot be null");
        }

        this.text = text;
        advance();
    }

    /**
     * Advances to the next character in string
     */
    private void advance() {
        try {
            current = text.charAt(pos++);
        } catch (IndexOutOfBoundsException e) {
            current = '\0';
        }
    }

    /**
     * Generate a number token
     * @return the number token
     */
    private Token generateNumber() {
        int decimalPointCount = 0;
        StringBuilder number = new StringBuilder(Character.toString(current));
        advance();

        while (current != '\0' && (Character.isDigit(current) || current == '.')) {
            if (current == '.') {
                if (++decimalPointCount > 1) {
                    break;
                }
            }

            number.append(current);
            advance();
        }

        if (number.toString().startsWith(".")) {
            number.insert(0, '0');
        }

        if (number.toString().endsWith(".")) {
            number.append('0');
        }

        return new Token(Token.Type.NUMBER, Double.parseDouble(number.toString()));
    }

    /**
     * Checks if there is an upcoming next token
     * @return True if upcoming next token else false
     */
    @Override
    public boolean hasNext() {
        return current != '\0';
    }

    /**
     * Returns the next token, else null (if no token left)
     * @return Next token or null
     */
    @Override
    public Token next() {
        while (current != '\0') {
            switch (current) {
                case ' ', '\t', '\n':
                    advance();
                    break;
                case '+':
                    advance();
                    return new Token(Token.Type.PLUS);
                case '-':
                    advance();
                    return new Token(Token.Type.MINUS);
                case '*':
                    advance();
                    return new Token(Token.Type.MULTIPLY);
                case '/':
                    advance();
                    return new Token(Token.Type.DIVIDE);
                case '(':
                    advance();
                    return new Token(Token.Type.LPAREN);
                case ')':
                    advance();
                    return new Token(Token.Type.RPAREN);
                default:
                    if (Character.isDigit(current) || current == '.') {
                        return generateNumber();
                    } else {
                        throw new RuntimeException("Illegal character '" + Character.toString(current) + "'");
                    }
            }
        }

        throw new NoSuchElementException();
    }
}
