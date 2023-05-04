package edu.ufsj.lox;

public class Token {
    
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        return type + " " + lexeme + " " + literal + " " + line;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Token)) {
            return false;
        }

        final Token otherToken = (Token) obj;

        return this.type == otherToken.type && 
               (this.lexeme != null) ? this.lexeme.equals(otherToken.lexeme) : otherToken.lexeme == null && 
               (this.literal != null) ? this.literal == otherToken.literal : otherToken.literal == null && 
               this.line == otherToken.line;
    }

}
