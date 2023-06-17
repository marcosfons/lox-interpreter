package edu.ufsj.lox;

abstract class Expr {

    interface Visitor<R> {
        R visitBinaryExpr(Binary expr);
        R visitGroupingExpr(Grouping expr);
        R visitLiteralExpr(Literal expr);
        R visitUnaryExpr(Unary expr);
        R visitTernaryExpr(Ternary expr);
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
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Binary)) {
                return false;
            }
            
            Binary otherExpr = (Binary) obj;
            
            return 
                this.left.equals(otherExpr.left) &&
                this.operator.equals(otherExpr.operator) &&
                this.right.equals(otherExpr.right);
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
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Grouping)) {
                return false;
            }
            
            Grouping otherExpr = (Grouping) obj;
            
            return 
                this.expression.equals(otherExpr.expression);
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
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Literal)) {
                return false;
            }
            
            Literal otherExpr = (Literal) obj;
            
            return 
                this.value.equals(otherExpr.value);
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
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Unary)) {
                return false;
            }
            
            Unary otherExpr = (Unary) obj;
            
            return 
                this.operator.equals(otherExpr.operator) &&
                this.right.equals(otherExpr.right);
        }

    }

    static class Ternary extends Expr {

        Ternary(Expr comparison, Expr thenExpr, Expr elseExpr) {
            this.comparison = comparison;
            this.thenExpr = thenExpr;
            this.elseExpr = elseExpr;
        }

        final Expr comparison;
        final Expr thenExpr;
        final Expr elseExpr;
        
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitTernaryExpr(this);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Ternary)) {
                return false;
            }
            
            Ternary otherExpr = (Ternary) obj;
            
            return 
                this.comparison.equals(otherExpr.comparison) &&
                this.thenExpr.equals(otherExpr.thenExpr) &&
                this.elseExpr.equals(otherExpr.elseExpr);
        }

    }

    abstract <R> R accept(Visitor<R> visitor);

    abstract public boolean equals(Object obj);

}

