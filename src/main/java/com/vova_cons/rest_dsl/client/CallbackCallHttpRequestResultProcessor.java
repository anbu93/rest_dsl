package com.vova_cons.rest_dsl.client;

import com.esotericsoftware.jsonbeans.Json;

/**
 * Created by anbu on 19.08.20.
 **/
public class CallbackCallHttpRequestResultProcessor<T> implements HttpRequestProcessor.ResultProcessor {
    private final Class<T> responseType;
    private final Json json;
    private final RequestCallback<T> callback;

    public CallbackCallHttpRequestResultProcessor(Json json, RequestCallback<T> callback, Class<T> responseType) {
        this.json = json;
        this.callback = callback;
        this.responseType = responseType;
    }

    @Override
    public void onSuccess(String responseBody) {
        T responseObject = json.fromJson(responseType, responseBody);
        callback.onSuccess(responseObject);
    }

    @Override
    public void onError(Exception e) {
        callback.onException(e);
    }
}
