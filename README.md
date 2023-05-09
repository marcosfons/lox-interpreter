
# Lox Interpreter

Interpretador da linguagem Lox feito em Java.

## Estrutura de pastas

```
.
├── pom.xml
├── README.md
└── src
    ├── main
    │   └── java
    │       └── edu
    │           └── ufsj
    │               ├── lox
    │               │   └──> Código do compilador
    │               └── tool
    │                   └──> Ferramentas de metaprogramação
    └── test
        └── java
            └── edu
                └── ufsj
                    └── lox
                        └──> Testes do compilador
```

## Como executar

### Interpretador

O interpretador da linguagem Lox pode ser executado a partir de um arquivo ou
como um `REPL` (read-eval-print loop).

```sh
javac -d ./target/classes src/main/java/edu/ufsj/**/*.java
java -cp target/classes edu.ufsj.lox.Lox
```

### GenerateAST

`GenerateAST` é um script responsável por criar as classes que representam as
expressões da linguagem Lox. Isso porque, o processo para criar essas classes é
repetitivo e bem padronizado.

```sh
javac -d ./target/classes src/main/java/edu/ufsj/**/*.java
java -cp target/classes edu.ufsj.tool.GenerateAST src/main/java/edu/ufsj/lox
```

### Autores

- Elias: [Elias](https://github.com/Eliasep)
- Marcos Fonseca: [marcosfons](https://github.com/marcosfons)

