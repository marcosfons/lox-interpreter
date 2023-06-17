package edu.ufsj.lox;

abstract class Expr {

    interface Visitor<R> {
        R visitTernaryExpr(Ternary expr);
        R visitBinaryExpr(Binary expr);
        R visitGroupingExpr(Grouping expr);
        R visitLiteralExpr(Literal expr);
        R visitUnaryExpr(Unary expr);
    }

    static class Binary extends Expr {

        Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        final Expr left;
        final Token operator;
        final Expr right;
        
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }

    }

    static class Ternary extends Expr {

        Ternary(Expr comparison, Expr ifTrue, Expr ifFalse) {
            this.comparison = comparison;
            this.ifTrue = ifTrue;
            this.ifFalse = ifFalse;
        }

        final Expr comparison;
        final Expr ifTrue;
        final Expr ifFalse;
        
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitTernaryExpr(this);
        }

    }

    static class Grouping extends Expr {

        Grouping(Expr expression) {
            this.expression = expression;
        }

        final Expr expression;
        
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }

    }

    static class Literal extends Expr {

        Literal(Object value) {
            this.value = value;
        }

        final Object value;
        
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }

    }

    static class Unary extends Expr {

        Unary(Token operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }

        final Token operator;
        final Expr right;
        
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitUnaryExpr(this);
        }

    }

    abstract <R> R accept(Visitor<R> visitor);

}

