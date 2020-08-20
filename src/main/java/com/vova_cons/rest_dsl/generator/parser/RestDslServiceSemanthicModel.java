package com.vova_cons.rest_dsl.generator.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by anbu on 20.08.20.
 **/
public class RestDslServiceSemanthicModel {
    public RestDslServerSemanthicModel server;
    public String id;
    public int port;
    public boolean isSecure = false;
    public String keystoreFile;
    public String keystorePassword;
    public List<RestDslRouteSemanthicModel> routes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestDslServiceSemanthicModel that = (RestDslServiceSemanthicModel) o;
        return port == that.port &&
                isSecure == that.isSecure &&
                Objects.equals(keystoreFile, that.keystoreFile) &&
                Objects.equals(keystorePassword, that.keystorePassword) &&
                Objects.equals(routes, that.routes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, isSecure, keystoreFile, keystorePassword, routes);
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int tab) {
        StringBuilder sb = new StringBuilder();
        addTabs(tab, sb);
        sb.append("Service ").append(id).append(" {\n");
        addTabs(tab+1, sb);
        sb.append("port = ").append(port).append("\n");
        if (isSecure) {
            addTabs(tab+1, sb);
            sb.append("keystoreFile = '").append(keystoreFile).append("'\n");
            addTabs(tab+1, sb);
            sb.append("keystorePassword = '").append(keystorePassword).append("'\n");
        }
        for(RestDslRouteSemanthicModel route : routes) {
            sb.append(route.toString(tab + 1)).append("\n");
        }
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
