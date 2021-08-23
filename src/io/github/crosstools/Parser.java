package io.github.crosstools;

import java.util.NoSuchElementException;

public class Parser {
    private final Lexer lexer;
    private Token current;

    /**
     * Constructs a new Parser object to convert text into AST
     * @param text The text string to parse
     * @throws Throwable Checked exception thrown if string text is null (it cannot be null!)
     */
    public Parser(String text) throws Throwable {
        lexer = new Lexer(text);
        advance();
    }

    /**
     * Advances to the next token
     */
    private void advance() {
        try {
            current = lexer.next();
        } catch (NoSuchElementException e) {
            current = null;
        }
    }

    /**
     * Raises parse error
     * @throws RuntimeException runtime error
     */
    private void raiseError() throws RuntimeException {
        throw new RuntimeException("Syntax error");
    }

    public Node parse() throws RuntimeException {
        if (current == null) {
            return null;
        }

        Node result = expr();

        if (current != null) {
            raiseError();
        }

        return result;
    }

    private Node expr() {
        Node result = term();

        while (current != null && (current.type == Token.Type.PLUS || current.type == Token.Type.MINUS)) {
            switch (current.type) {
                case PLUS -> {
                    advance();
                    result = new Node(Node.Type.Add, result, term());
                }
                case MINUS -> {
                    advance();
                    result = new Node(Node.Type.Minus, result, term());
                }
            }
        }

        return result;
    }

    private Node term() throws RuntimeException {
        Node result = factor();

        while (current != null && (current.type == Token.Type.MULTIPLY || current.type == Token.Type.DIVIDE)) {
            switch (current.type) {
                case MULTIPLY -> {
                    advance();
                    result = new Node(Node.Type.Multiply, result, factor());
                }
                case DIVIDE -> {
                    advance();
                    result = new Node(Node.Type.Divide, result, factor());
                }
            }
        }

        return result;
    }

    private Node factor() throws RuntimeException {
        Token token = current;

        if (token == null) {
            raiseError();
        }

        switch (token.type) {
            case LPAREN -> {
                advance();
                Node result = expr();

                if (current.type != Token.Type.RPAREN) {
                    raiseError();
                }

                advance();
                return result;
            }
            case NUMBER -> {
                advance();
                return new Node(Node.Type.Number, token.value);
            }
            case PLUS -> {
                advance();
                return new Node(Node.Type.Plus, factor());
            }
            case MINUS -> {
                advance();
                return new Node(Node.Type.Minus, factor());
            }
        }

        raiseError();
        return null; // this will never return null because raiseError() throws an exception
    }
}
