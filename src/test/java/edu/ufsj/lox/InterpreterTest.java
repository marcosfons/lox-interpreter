package edu.ufsj.lox;

import static edu.ufsj.lox.TokenType.*;    

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InterpreterTest {

    @Test
    public void testLiteralEval() {
        assertEquals(
            new Interpreter().evaluate(new Expr.Literal(1.0)),
            1.0
        );
    }

    @Test
    public void testBinaryEval() { // 2 + 2
        assertEquals(
            new Interpreter().evaluate( // 
                new Expr.Binary(
                    new Expr.Literal(2.0),
                    new Token(PLUS, "+", null, 1),
                    new Expr.Literal(2.0)
                )
            ),
            4.0
        );
    }

    @Test
    public void testUnaryEval() { // !false
        assertEquals(
            new Interpreter().evaluate(
                new Expr.Unary(new Token(BANG, "!", null, 1), new Expr.Literal(false))
            ),
            true
        );
    }

    @Test
    public void testBangWithTruthyValue() { // !25
        assertEquals(
            new Interpreter().evaluate(
                new Expr.Unary(new Token(BANG, "!", null, 1), new Expr.Literal(25.0))
            ),
            false
        );
    }

    @Test
    public void testGroupingAndBinaryEval() { // 2 + (3 * 5)
        assertEquals(
            new Interpreter().evaluate(
                new Expr.Binary(
                    new Expr.Literal(2.0),
                    new Token(PLUS, "+", null, 1),
                    new Expr.Grouping(
                        new Expr.Binary(
                            new Expr.Literal(3.0),
                            new Token(STAR, "*", null, 1),
                            new Expr.Literal(5.0)
                        )
                    )
                )
            ),
            17.0
        );
    }

    @Test
    public void testTrueTernaryEval() { // 15 > 10 ? "YES" : "NO"
        assertEquals(
            new Interpreter().evaluate(
                new Expr.Ternary(
                    new Expr.Binary(
                        new Expr.Literal(15.0),
                        new Token(GREATER, ">", null, 1),
                        new Expr.Literal(10.0)
                    ),
                    new Expr.Literal("YES"),
                    new Expr.Literal("NO")
                )
            ),
            "YES"
        );
    }

    @Test
    public void testFalseTernaryEval() { // 15 < 10 ? "YES" : "NO"
        assertEquals(
            new Interpreter().evaluate(
                new Expr.Ternary(
                    new Expr.Binary(
                        new Expr.Literal(15.0),
                        new Token(LESS, "<", null, 1),
                        new Expr.Literal(10.0)
                    ),
                    new Expr.Literal("YES"),
                    new Expr.Literal("NO")
                )
            ),
            "NO"
        );
    }

    @Test
    public void testTruthyTernaryEval() { // 15 ? "YES" : "NO"
        assertEquals(
            new Interpreter().evaluate(
                new Expr.Ternary(
                    new Expr.Literal(15.0),
                    new Expr.Literal("YES"),
                    new Expr.Literal("NO")
                )
            ),
            "YES"
        );
    }

    @Test
    public void testNilTernaryEval() { // nil ? "YES" : "NO"
        assertEquals(
            new Interpreter().evaluate(
                new Expr.Ternary(
                    new Expr.Literal(null),
                    new Expr.Literal("YES"),
                    new Expr.Literal("NO")
                )
            ),
            "NO"
        );
    }

}
