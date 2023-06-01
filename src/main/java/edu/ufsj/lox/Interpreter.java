package edu.ufsj.lox;

import edu.ufsj.lox.Expr.Unary;
import edu.ufsj.lox.Expr.Binary;
import edu.ufsj.lox.Expr.Literal;
import edu.ufsj.lox.Expr.Grouping;

class Interpreter implements Expr.Visitor<Object> {

    @Override
    public Object visitBinaryExpr(Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
            case PLUS:
                if (left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                } else if (left instanceof String && right instanceof String) {
                    return (String) left + (String) right;
                }

                // TODO(marcosfons): Handle error using the PLUS operator
                return null;
            case MINUS:
                return (double) left - (double) right;
            case SLASH:
                return (double) left / (double) right;
            case STAR:
                return (double) left * (double) right;

            case GREATER:
                return (double) left > (double) right;
            case GREATER_EQUAL:
                return (double) left >= (double) right;
            case LESS:
                return (double) left < (double) right;
            case LESS_EQUAL:
                return (double) left <= (double) right;

            default:
                return null;
        }

    }

    @Override
    public Object visitGroupingExpr(Grouping expr) {
        return evaluate(expr.expression);
    }

    @Override
    public Object visitLiteralExpr(Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitUnaryExpr(Unary expr) {
        Object right = evaluate(expr.right);

        switch(expr.operator.type) {
            case MINUS:
                return -(double) right;    
            case BANG:
                return !isTruthy(right);
            default:
                return null;
        }
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    // All values are evaluated to true, EXCEPT for FALSE and NIL
    private boolean isTruthy(Object object) {
        if (object == null) {
            return false;
        }

        if (object instanceof Boolean) {
            return (boolean) object;
        }

        return true;
    }
    
}
