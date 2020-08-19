package com.vova_cons.rest_dsl.server;

import com.esotericsoftware.jsonbeans.Json;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Method;

/**
 * Created by anbu on 19.08.20.
 **/
public class RestDslRouteProcessor implements Route {
    private Json json = new Json();
    private RestDslService service;
    private Method javaMethod;
    private Class<?> requestClass;

    public RestDslRouteProcessor(RestDslService service, Method javaMethod, Class<?> requestClass) {
        this.service = service;
        this.javaMethod = javaMethod;
        this.requestClass = requestClass;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {
            String body = request.body();
            System.out.println("Try handle " + javaMethod.getName() + " " + body);
            Object requestObject = json.fromJson(requestClass, body);
            Object responseObject = javaMethod.invoke(service, requestObject);
            response.header("Content-Type", "application/json");
            String responceString = json.toJson(responseObject);
            System.out.println("Success: " + responceString);
            return responceString;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Object responseObject = javaMethod.invoke(service, null);
                response.header("Content-Type", "application/json");
                return json.toJson(responseObject);
            } catch (Exception e2) {
                e2.printStackTrace();
                response.status(503);
                return e.getMessage();
            }
        }
    }
}
