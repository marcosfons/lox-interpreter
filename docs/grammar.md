
# Gramática

```
〈expression〉 ::= <ternary>;

〈ternary〉 ::= <equality> ( ? <expression> : <expression> );

〈equality〉 ::= <comparison> ( ( != | == ) <comparison> )∗ ;

〈comparison〉 ::= <term> ( ( > | >= | < | <= ) <term> )∗;

〈term〉 ::= <factor> ( ( − | + ) <factor> )∗;

〈factor〉 ::= <unary> ( ( / | * ) <unary> )∗;

〈unary〉 ::= ( ! | − ) <unary> | <primary>;

〈primary〉 ::= NUMBER | STRING | true | false | nil | ( <expression> );
```


### Qual a precedẽncia permitida entre o "?" e ":" ?

O operador ternário na gramática possui menor precedência, um exemplo disso é a
seguinte expressão e o seu resultado:

```
> true == true == true == true == true ? "AQUI" : "NAO"
AQUI
```

Como é possível ver, primeiro o resultado de `(true == true == true == true ==
true)` é "executado" para somente apór ser retornado o resultado "AQUI".



### O operador como um todo é associativo à esquerda ou à direita?

Na seguinte expressão, primeiro é avaliado o resultado de `3 < 2` para depois a
execução do segundo operador ternário com a condição `5 < 8`, portanto o
operador é associativo à esquerda.

```
3 < 2 ? 1 : 5 < 8 ? 2 : 3
```