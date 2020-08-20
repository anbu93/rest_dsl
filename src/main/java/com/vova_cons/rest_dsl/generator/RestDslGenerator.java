package com.vova_cons.rest_dsl.generator;

import com.vova_cons.rest_dsl.generator.generator.RestDslCodeGenerator;
import com.vova_cons.rest_dsl.generator.lexer.RestDslLexer;
import com.vova_cons.rest_dsl.generator.lexer.RestDslToken;
import com.vova_cons.rest_dsl.generator.parser.RestDslParser;
import com.vova_cons.rest_dsl.generator.parser.RestDslSemanthicModel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by anbu on 20.08.20.
 **/
public class RestDslGenerator {
    private String dslFilePath;
    private String outputDirectory;

    public RestDslGenerator(String dslFilePath, String outputDirectory) {
        this.dslFilePath = dslFilePath;
        this.outputDirectory = outputDirectory;
    }

    //region interface
    public static void generate(String dslFilePath, String outputDirectory) {
        RestDslGenerator generator = new RestDslGenerator(dslFilePath, outputDirectory);
        generator.generate();
    }

    public void generate() {
        RestDslLexer lexer = new RestDslLexer();
        List<RestDslToken> tokens = lexer.process(dslFilePath);
        RestDslParser parser = new RestDslParser();
        RestDslSemanthicModel semanthicModel = parser.parse(tokens);
        RestDslCodeGenerator codeGenerator = new RestDslCodeGenerator();
        codeGenerator.generate(outputDirectory, semanthicModel);
    }
    //endregion
}
