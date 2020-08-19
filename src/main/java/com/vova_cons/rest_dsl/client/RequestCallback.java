package com.vova_cons.rest_dsl.client;

/**
 * Created by anbu on 19.08.20.
 **/
public interface RequestCallback<T> {
    void onSuccess(T response);
    default void onError(String message) {
        System.err.println(message);
    }
    default void onException(Exception e) {
        e.printStackTrace();
    }
}
