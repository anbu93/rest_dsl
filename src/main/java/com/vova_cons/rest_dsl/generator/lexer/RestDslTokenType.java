package com.vova_cons.rest_dsl.generator.lexer;

import java.util.regex.Pattern;

/**
 * Created by anbu on 20.08.20.
 **/
public enum RestDslTokenType {
    DTO(Pattern.compile("^dto")),
    PACKAGE(Pattern.compile("^package")),
    SERVER(Pattern.compile("^server")),
    SERVICE(Pattern.compile("^service")),
    IP(Pattern.compile("^ip")),
    HTTPS(Pattern.compile("^https")),
    KEYSTORE(Pattern.compile("^keystore")),
    PASSWORD(Pattern.compile("^password")),
    METHOD(Pattern.compile("^method")),
    ROUTE(Pattern.compile("^route")),
    PORT(Pattern.compile("^port")),
    REQUEST(Pattern.compile("^request")),
    RESPONSE(Pattern.compile("^response")),
    ENDLINE(Pattern.compile("^;")),
    OPEN(Pattern.compile("^\\{")),
    CLOSE(Pattern.compile("^}")),
    EQUAL(Pattern.compile("^=")),

    NUMBER(Pattern.compile("^[0-9]+")),
    ID(Pattern.compile("^[a-zA-Z0-9_]+")),
    QUOTE_TEXT(Pattern.compile("^\"[^\"]*\""));

    public Pattern pattern;

    RestDslTokenType(Pattern pattern) {
        this.pattern = pattern;
    }
}
