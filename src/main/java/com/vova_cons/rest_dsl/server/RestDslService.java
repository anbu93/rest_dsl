package com.vova_cons.rest_dsl.server;

import spark.Service;

/**
 * Created by anbu on 19.08.20.
 **/
public class RestDslService {
    private final int port;
    private Service http;

    public RestDslService(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setHttp(Service http) {
        this.http = http;
    }

    public void stop() {
        http.stop();
    }

    public boolean isSecure() {
        return false;
    }

    public String getKeystoreFile() {
        return null;
    }

    public String getKeystorePassword() {
        return null;
    }
}
