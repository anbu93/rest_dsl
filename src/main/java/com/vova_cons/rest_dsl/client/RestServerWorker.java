package com.vova_cons.rest_dsl.client;

import com.esotericsoftware.jsonbeans.Json;
import org.eclipse.jetty.http.HttpMethod;

/**
 * Created by anbu on 19.08.20.
 **/
public abstract class RestServerWorker {
    private Json json = new Json();
    private HttpRequestProcessor httpRequestProcessor = new JettyHttpRequestProcessor();

    public abstract String getUrl();

    public <T> void sendRequest(Object request, RequestCallback<T> callback, Class<T> responseType, String route, HttpMethod httpMethod) {
        String requestBody = json.toJson(request);
        httpRequestProcessor.sendHttpRequest(requestBody, getUrl() + route, httpMethod,
                new CallbackCallHttpRequestResultProcessor(json, callback, responseType));
    }
}
