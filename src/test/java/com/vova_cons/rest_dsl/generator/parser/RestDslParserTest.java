package com.vova_cons.rest_dsl.generator.parser;

import com.vova_cons.rest_dsl.generator.lexer.RestDslLexer;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by anbu on 20.08.20.
 **/
public class RestDslParserTest {

    @Test
    public void integrationTest() {
        RestDslSemanthicModel excepted = createExceptedModel();
        RestDslLexer lexer = new RestDslLexer();
        RestDslParser parser = new RestDslParser();
        RestDslSemanthicModel actual = parser.parse(lexer.process("test/test.rest_dsl"));
        assertEquals(excepted, actual);
    }

    private RestDslSemanthicModel createExceptedModel() {
        RestDslSemanthicModel model = new RestDslSemanthicModel();
        model.dtoClasessPackage = "com.vova_cons.rest_dsl.test";
        RestDslServerSemanthicModel server = new RestDslServerSemanthicModel();
        server.id = "Test";
        server.ip = "localhost";
        RestDslServiceSemanthicModel service = new RestDslServiceSemanthicModel();
        service.id = "Test";
        service.port = 8081;
        service.isSecure = true;
        service.keystoreFile = "keystore.jks";
        service.keystorePassword = "password";
        RestDslRouteSemanthicModel route = new RestDslRouteSemanthicModel();
        route.route = "test";
        route.method = "GET";
        route.requestType = "TestRequest";
        route.responseType = "TestResponse";
        service.routes.add(route);
        server.addService(service);
        model.addServer(server);
        return model;
    }
}