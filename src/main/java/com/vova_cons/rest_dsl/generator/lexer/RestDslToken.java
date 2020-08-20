package com.vova_cons.rest_dsl.generator.lexer;

import java.util.Objects;

/**
 * Created by anbu on 20.08.20.
 **/
public class RestDslToken {
    public RestDslTokenType type;
    public String value;

    public RestDslToken(RestDslTokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestDslToken that = (RestDslToken) o;
        return type == that.type &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        return "Token(" + type + ",'" + value + "')";
    }
}
