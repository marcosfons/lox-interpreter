package edu.ufsj.lox;

import static edu.ufsj.lox.TokenType.*;    

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ASTPrinterTest {

    @Test
    public void shouldPrettyPrintExpression() {
        final Expr expression = new Expr.Binary(
            new Expr.Unary(
                new Token(MINUS, "-", null, 1),
                new Expr.Literal(123)
            ),
            new Token(STAR, "*", null, 1),
            new Expr.Grouping(new Expr.Literal(45.67))
        );

        final ASTPrinter astPrinter = new ASTPrinter();

        assertEquals(astPrinter.print(expression), "(* (- 123) (group 45.67))");
    }
    
}
