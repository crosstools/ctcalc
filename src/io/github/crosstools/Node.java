package io.github.crosstools;

public class Node {
    public enum Type {
        Number,
        Add,
        Subtract,
        Multiply,
        Divide,
        Plus,
        Minus,
    }

    public Type type;
    public Node nodeA, nodeB;
    public Double value;

    /**
     * Construct a new Node object with just Node type
     * @param type Node type
     */
    public Node(Type type) {
        this.type = type;
    }

    /**
     * Construct a new Node object with Node type and another self Node object (usually for Unary Ops)
     * @param type Node type
     * @param node Node object
     */
    public Node(Type type, Node node) {
        this.type = type;
        nodeA = node;
    }

    /**
     * Construct a new Node object with Node type and double value (usually for NumberNodes)
     * @param type Node type
     * @param value Double value
     */
    public Node(Type type, double value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Construct a new Node object with Node type and two other self Node objects (usually for Binary Ops)
     * @param type Node type
     * @param nodeA NodeA object (left node object)
     * @param nodeB NodeB object (right node object)
     */
    public Node(Type type, Node nodeA, Node nodeB) {
        this.type = type;
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    /**
     * Represents the node in string
     * @return represented node in string
     */
    @Override
    public String toString() {
        return switch (type) {
            case Number -> value.toString();
            case Add -> String.format("(%s + %s)", nodeA, nodeB);
            case Subtract -> String.format("(%s - %s)", nodeA, nodeB);
            case Multiply -> String.format("(%s * %s)", nodeA, nodeB);
            case Divide -> String.format("(%s / %s)", nodeA, nodeB);
            case Plus -> String.format("(+%s)", nodeA);
            case Minus -> String.format("(-%s)", nodeA);
        };
    }
}
