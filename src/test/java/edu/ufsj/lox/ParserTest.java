package edu.ufsj.lox;

import static edu.ufsj.lox.TokenType.*;    

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import edu.ufsj.lox.Parser.ParseError;

public class ParserTest {

    @Test
    public void testLiteralExpression() {
        Parser parser = new Parser(Arrays.asList(
            new Token(NUMBER, "1", 1, 1),
            new Token(EOF, null, null, 1)
        ));

        assertEquals(parser.parse(), new Expr.Literal(1));
    }

    @Test
    public void testUnaryExpression() {
        Parser parser = new Parser(Arrays.asList(
            new Token(BANG, "!", null, 1),
            new Token(NUMBER, "1", 1, 1),
            new Token(EOF, null, null, 1)
        ));

        assertEquals(
            parser.parse(), 
            new Expr.Unary(
                new Token(BANG, "!", null, 1),
                new Expr.Literal(1)
            )
        );
    }

    @Test
    public void testBinaryExpression() {
        Parser parser = new Parser(Arrays.asList(
            new Token(NUMBER, "10", 10, 1),
            new Token(GREATER, ">", null, 1),
            new Token(NUMBER, "15", 15, 1),
            new Token(EOF, null, null, 1)
        ));

        assertEquals(
            parser.parse(), 
            new Expr.Binary(
                new Expr.Literal(10),
                new Token(GREATER, ">", null, 1),
                new Expr.Literal(15)
            )
        );
    }

    @Test
    public void testSimpleGroupingExpression() {
        Parser parser = new Parser(Arrays.asList(
            new Token(LEFT_PAREN, "(", null, 1),
            new Token(NUMBER, "1", 1, 1),
            new Token(RIGHT_PAREN, ")", null, 1),
            new Token(EOF, null, null, 1)
        ));

        assertEquals(parser.parse(), new Expr.Grouping(new Expr.Literal(1)));
    }

    @Test
    public void testGroupingAndBinaryExpressions() {
        Parser parser = new Parser(Arrays.asList(
            new Token(LEFT_PAREN, "(", null, 1),
            new Token(NUMBER, "1", 1, 1),
            new Token(PLUS, "+", null, 1),
            new Token(NUMBER, "2", 2, 1),
            new Token(RIGHT_PAREN, ")", null, 1),
            new Token(PLUS, "+", null, 1),
            new Token(NUMBER, "3", 3, 1),
            new Token(EOF, null, null, 1)
        ));

        assertEquals(
            parser.parse(), 
            new Expr.Binary(
                new Expr.Grouping(
                    new Expr.Binary(
                        new Expr.Literal(1),
                        new Token(PLUS, "+", null, 1),
                        new Expr.Literal(2)
                    )
                ),
                new Token(PLUS, "+", null, 1),
                new Expr.Literal(3)
            )
        );
    }

    @Test
    public void testTernaryOperator() {
        Parser parser = new Parser(Arrays.asList( // 15 > 10 ? "TRUE" : "FALSE"
            new Token(NUMBER, "15", 15, 1),
            new Token(GREATER, ">", null, 1),
            new Token(NUMBER, "10", 10, 1),
            new Token(QUESTION_MARK, "?", null, 1),
            new Token(STRING, "TRUE", "TRUE", 1),
            new Token(COLON, ":", null, 1),
            new Token(NUMBER, "FALSE", "FALSE", 1),
            new Token(EOF, null, null, 1)
        ));

        assertEquals(
            parser.parse(),
            new Expr.Ternary(
                new Expr.Binary(
                    new Expr.Literal(15),
                    new Token(GREATER, ">", null, 1),
                    new Expr.Literal(10)
                ),
                new Expr.Literal("TRUE"),
                new Expr.Literal("FALSE")
            )
        );
    }

    @Test
    public void testTernaryMissingColon() {
        try {
            Parser parser = new Parser(Arrays.asList( // 15 > 10 ? "TRUE"
                new Token(NUMBER, "15", 15, 1),
                new Token(GREATER, ">", null, 1),
                new Token(NUMBER, "10", 10, 1),
                new Token(QUESTION_MARK, "?", null, 1),
                new Token(STRING, "TRUE", "TRUE", 1),
                new Token(EOF, null, null, 1)
            ));
            parser.parse();
        } catch(ParseError error) {
            return;
        }
        fail("Should throw ParseError");
    }
}
