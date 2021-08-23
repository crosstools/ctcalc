package io.github.crosstools;

public class Token {
    public enum Type {
        NUMBER,
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        LPAREN,
        RPAREN,
    }

    public Type type;
    public Double value = null;

    public Token(Type type) {
        this.type = type;
    }

    public Token(Type type, double value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
