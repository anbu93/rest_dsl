package com.vova_cons.rest_dsl.generator.lexer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by anbu on 20.08.20.
 **/
public class RestDslLexerTest {
    @Test
    public void integrationTest() {
        String filename = "test/test.rest_dsl";
        RestDslLexer lexer = new RestDslLexer();
        List<RestDslToken> exceptedTokens = Arrays.asList(
                new RestDslToken(RestDslTokenType.DTO, "dto"),
                new RestDslToken(RestDslTokenType.PACKAGE, "package"),
                new RestDslToken(RestDslTokenType.QUOTE_TEXT, "com.vova_cons.rest_dsl.test"),
                new RestDslToken(RestDslTokenType.ENDLINE, ";"),
                new RestDslToken(RestDslTokenType.SERVER, "server"),
                new RestDslToken(RestDslTokenType.ID, "Test"),
                new RestDslToken(RestDslTokenType.OPEN, "{"),
                new RestDslToken(RestDslTokenType.IP, "ip"),
                new RestDslToken(RestDslTokenType.QUOTE_TEXT, "localhost"),
                new RestDslToken(RestDslTokenType.ENDLINE, ";"),
                new RestDslToken(RestDslTokenType.SERVICE, "service"),
                new RestDslToken(RestDslTokenType.ID, "Test"),
                new RestDslToken(RestDslTokenType.OPEN, "{"),
                new RestDslToken(RestDslTokenType.PORT, "port"),
                new RestDslToken(RestDslTokenType.NUMBER, "8081"),
                new RestDslToken(RestDslTokenType.ENDLINE, ";"),
                new RestDslToken(RestDslTokenType.HTTPS, "https"),
                new RestDslToken(RestDslTokenType.OPEN, "{"),
                new RestDslToken(RestDslTokenType.KEYSTORE, "keystore"),
                new RestDslToken(RestDslTokenType.QUOTE_TEXT, "keystore.jks"),
                new RestDslToken(RestDslTokenType.ENDLINE, ";"),
                new RestDslToken(RestDslTokenType.PASSWORD, "password"),
                new RestDslToken(RestDslTokenType.QUOTE_TEXT, "password"),
                new RestDslToken(RestDslTokenType.ENDLINE, ";"),
                new RestDslToken(RestDslTokenType.CLOSE, "}"),
                new RestDslToken(RestDslTokenType.ROUTE, "route"),
                new RestDslToken(RestDslTokenType.ID, "test"),
                new RestDslToken(RestDslTokenType.OPEN, "{"),
                new RestDslToken(RestDslTokenType.METHOD, "method"),
                new RestDslToken(RestDslTokenType.ID, "GET"),
                new RestDslToken(RestDslTokenType.ENDLINE, ";"),
                new RestDslToken(RestDslTokenType.REQUEST, "request"),
                new RestDslToken(RestDslTokenType.ID, "TestRequest"),
                new RestDslToken(RestDslTokenType.ENDLINE, ";"),
                new RestDslToken(RestDslTokenType.RESPONSE, "response"),
                new RestDslToken(RestDslTokenType.ID, "TestResponse"),
                new RestDslToken(RestDslTokenType.ENDLINE, ";"),
                new RestDslToken(RestDslTokenType.CLOSE, "}"),
                new RestDslToken(RestDslTokenType.CLOSE, "}"),
                new RestDslToken(RestDslTokenType.CLOSE, "}")
        );
        List<RestDslToken> actualTokens = new ArrayList<>(lexer.process(filename));
        assertEquals(exceptedTokens, actualTokens);
    }
}