package com.vova_cons.rest_dsl.generator.parser;

import java.util.Objects;

/**
 * Created by anbu on 20.08.20.
 **/
public class RestDslRouteSemanthicModel {
    public String route;
    public String method;
    public String requestType;
    public String responseType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestDslRouteSemanthicModel that = (RestDslRouteSemanthicModel) o;
        return Objects.equals(route, that.route) &&
                Objects.equals(method, that.method) &&
                Objects.equals(requestType, that.requestType) &&
                Objects.equals(responseType, that.responseType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(route, method, requestType, responseType);
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int tab) {
        StringBuilder sb = new StringBuilder();
        addTabs(tab, sb);
        sb.append("Route {\n");
        addTabs(tab+1, sb);
        sb.append("route = ").append(route).append("\n");
        addTabs(tab+1, sb);
        sb.append("method = ").append(method).append("\n");
        addTabs(tab+1, sb);
        sb.append("request = ").append(requestType).append("\n");
        addTabs(tab+1, sb);
        sb.append("response = ").append(responseType).append("\n");
        addTabs(tab, sb);
        sb.append("}");
        return sb.toString();
    }

    private void addTabs(int count, StringBuilder sb) {
        for(int i = 0; i < count; i++) {
            sb.append('\t');
        }
    }
}
