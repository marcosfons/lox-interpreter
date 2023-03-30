package edu.ufsj.lox;

public enum TokenType {
    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACE,
    RIGHT_BRACE,
    COMMA,
    DOT,
    MINUS,
    PLUS,
    SEMICOLON,
    SLASH,
    STAR,
    BANG,
    BANG_EQUAL,
    EQUAL,
    EQUAL_EQUAL,
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,
    IDENTIFIER,
    STRING,
    NUMBER,

    // Reserved keywords
    OR,
    AND,
    TRUE,
    FALSE,
    IF,
    ELSE,
    FUN,
    RETURN,
    PRINT,
    FOR,
    NIL,
    SUPER,
    CLASS,
    THIS,

    EOF
}
