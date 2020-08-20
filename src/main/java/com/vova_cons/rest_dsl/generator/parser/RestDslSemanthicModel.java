package com.vova_cons.rest_dsl.generator.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by anbu on 20.08.20.
 **/
public class RestDslSemanthicModel {
    public String dtoClasessPackage;
    public List<RestDslServerSemanthicModel> servers = new ArrayList<>();

    public void addServer(RestDslServerSemanthicModel server) {
        servers.add(server);
        server.model = this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestDslSemanthicModel that = (RestDslSemanthicModel) o;
        return Objects.equals(dtoClasessPackage, that.dtoClasessPackage) &&
                Objects.equals(servers, that.servers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dtoClasessPackage, servers);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RestDslSemanthicModel {\n");
        sb.append("\tpackage = '").append(dtoClasessPackage).append("'\n");
        for(RestDslServerSemanthicModel server : servers) {
            sb.append(server.toString(1)).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
