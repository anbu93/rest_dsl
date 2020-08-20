package com.vova_cons.rest_dsl.generator.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by anbu on 20.08.20.
 **/
public class RestDslServerSemanthicModel {
    public RestDslSemanthicModel model;
    public String id;
    public String ip;
    public List<RestDslServiceSemanthicModel> services = new ArrayList<>();

    public void addService(RestDslServiceSemanthicModel service) {
        services.add(service);
        service.server = this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestDslServerSemanthicModel that = (RestDslServerSemanthicModel) o;
        return Objects.equals(ip, that.ip) &&
                Objects.equals(services, that.services);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, services);
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int tab) {
        StringBuilder sb = new StringBuilder();
        addTabs(tab, sb);
        sb.append("Server ").append(id).append(" {\n");
        addTabs(tab+1, sb);
        sb.append("ip = '").append(ip).append("'\n");
        for(RestDslServiceSemanthicModel service : services) {
            sb.append(service.toString(tab + 1)).append("\n");
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
