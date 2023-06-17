package edu.ufsj.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class GenerateAST {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: \n\tGenerateAST <output directory>");
            System.exit(64);
        }

        final String outputDir = args[0];

        defineAST(outputDir, "Expr",
            Arrays.asList(
                "Binary   : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal  : Object value",
                "Unary    : Token operator, Expr right",
                "Ternary  : Expr condition, Expr thenExpr, Expr elseExpr"
            ),
            "    "
        );
    }

    private static void defineAST(String outputDir, String baseName, List<String> types, String indentation) throws IOException {
        final Path path = Paths.get(outputDir, baseName + ".java");
        final PrintWriter writer = new PrintWriter(path.toString(), "UTF-8");

        writer.println(String.join("\n", 
            "package edu.ufsj.lox;",
            "",
            "abstract class " + baseName + " {"
        ));

        defineVisitor(writer, baseName, types, indentation);

        for (final String type : types) {
            final String[] splittedType = type.split(":");
            final String className = splittedType[0].trim();
            final String fields = splittedType[1].trim();

            defineType(writer, baseName, className, fields, indentation);
        }

        writer.println(String.join("\n", 
            "",
            indentation.repeat(1) + "abstract <R> R accept" + "(Visitor<R> visitor);",
            "",
            indentation.repeat(1) + "abstract public boolean equals(Object obj);",
            "",
            "}",
            ""
        ));

        writer.close();
    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types, String indentation) {
        writer.println("\n" + indentation.repeat(1) + "interface Visitor<R> {");

        for (final String type : types) {
            final String typeName = type.split(":")[0].trim();
            writer.println(indentation.repeat(2) + "R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");
        }

        writer.println(indentation.repeat(1) + "}");
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList, String indentation) {
        writer.println(String.join("\n", 
            "",
            indentation + "static class " + className + " extends " + baseName + " {",
            "",
            indentation.repeat(2) + className + "(" + fieldList + ") {"
        ));

        final String[] fields = fieldList.split(", ");

        for (final String field : fields) {
            final String name = field.split(" ")[1];

            writer.println(indentation.repeat(3) + "this." + name + " = " + name + ";");
        }

        writer.println(indentation.repeat(2) + "}" + "\n");

        for (final String field : fields) {
            writer.println(indentation.repeat(2) + "final " + field + ";");
        }

        writer.println(String.join("\n", 
            indentation.repeat(2),
            indentation.repeat(2) + "@Override",
            indentation.repeat(2) + "<R> R accept(Visitor<R> visitor) {",
            indentation.repeat(3) + "return visitor.visit" + className + baseName + "(this);",
            indentation.repeat(2) + "}",
            indentation.repeat(2) + "",
            indentation.repeat(2) + "@Override",
            indentation.repeat(2) + "public boolean equals(Object obj) {",
            indentation.repeat(3) + "if (obj == null || !(obj instanceof " + className + ")) {",
            indentation.repeat(4) + "return false;",
            indentation.repeat(3) + "}",
            indentation.repeat(3) + "",
            indentation.repeat(3) + className + " otherExpr = (" + className + ") obj;",
            indentation.repeat(3) + "",
            indentation.repeat(3) + "return "
        ));

        for (int i = 0; i < fields.length; i++) {
            final String fieldName = fields[i].split(" ")[1];
            writer.println(indentation.repeat(4) + "this." + fieldName + ".equals(otherExpr." + fieldName + (i == fields.length - 1 ? ");" : ") &&"));
        }

        writer.println(String.join("\n",
            indentation.repeat(2) + "}",
            "",
            indentation + "}"
        ));
    }
    
}
