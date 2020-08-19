package com.vova_cons.rest_dsl.client;

import org.eclipse.jetty.http.HttpMethod;

/**
 * Created by anbu on 19.08.20.
 **/
public interface HttpRequestProcessor {
    void sendHttpRequest(String requestBody, String url, HttpMethod httpMethod, ResultProcessor resultProcessor);

    interface ResultProcessor {
        void onSuccess(String responseBody);
        default void onError(Exception e) {
            e.printStackTrace();
        }
    }
}
