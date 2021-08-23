package io.github.crosstools;

public class Interpreter {
    public Values.Number visit(Node node) {
        return switch (node.type) {
            case Number -> new Values.Number(node.value);
            case Add -> new Values.Number(visit(node.nodeA).value() + visit(node.nodeB).value());
            case Subtract -> new Values.Number(visit(node.nodeA).value() - visit(node.nodeB).value());
            case Multiply -> new Values.Number(visit(node.nodeA).value() * visit(node.nodeB).value());
            case Divide -> new Values.Number(visit(node.nodeA).value() / visit(node.nodeB).value());
            case Plus -> new Values.Number(+visit(node.nodeA).value());
            case Minus -> new Values.Number(-visit(node.nodeA).value());
        };
    }
}
