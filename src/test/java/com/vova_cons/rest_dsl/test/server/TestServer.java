package com.vova_cons.rest_dsl.test.server;

import com.vova_cons.rest_dsl.server.RestDsl;

/**
 * Created by anbu on 19.08.20.
 **/
public class TestServer {
    public static void main(String[] args) {
        TestService testService = new TestService();
        RestDsl.ignite(testService);
    }
}
