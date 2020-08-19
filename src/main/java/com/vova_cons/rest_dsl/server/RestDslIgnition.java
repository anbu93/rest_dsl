package com.vova_cons.rest_dsl.server;

import spark.Service;

import java.lang.reflect.Method;

/**
 * Created by anbu on 19.08.20.
 **/
public class RestDslIgnition {
    public void ignite(RestDslService service) {
        Service http = igniteHttp(service);
        service.setHttp(http);
        parseRoutes(service, http);
    }

    protected Service igniteHttp(RestDslService service) {
        Service http = Service.ignite();
        http.port(service.getPort());
        if (service.isSecure()) {
            http.secure(service.getKeystoreFile(), service.getKeystorePassword(), null, null);
        }
        return http;
    }

    protected void parseRoutes(RestDslService service, Service http) {
        for(Method method : service.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(SparkRoute.class)) {
                SparkRoute route = method.getDeclaredAnnotation(SparkRoute.class);
                RestDslRouteProcessor routeProcessor = createRouteProcessor(method, service);
                switch(route.method()) {
                    case get:
                        http.get(route.route(), routeProcessor);
                        break;
                    case post:
                        http.post(route.route(), routeProcessor);
                        break;
                    case put:
                        http.put(route.route(), routeProcessor);
                        break;
                    case patch:
                        http.patch(route.route(), routeProcessor);
                        break;
                    case delete:
                        http.delete(route.route(), routeProcessor);
                        break;
                    case head:
                        http.head(route.route(), routeProcessor);
                        break;
                    case trace:
                        http.trace(route.route(), routeProcessor);
                        break;
                    case connect:
                        http.connect(route.route(), routeProcessor);
                        break;
                    case options:
                        http.options(route.route(), routeProcessor);
                        break;
                    case before:
                    case after:
                    case afterafter:
                    case unsupported:
                        throw new RuntimeException("Not valid htppMethod=" + route.method()
                                + " for method " + method.getName() + " route=" + route.route());
                }
                System.out.println("Add route " + route.route() + " " + route.method());
            }
        }
    }

    protected RestDslRouteProcessor createRouteProcessor(Method javaMethod, RestDslService service) {
        Class<?>[] parameterTypes = javaMethod.getParameterTypes();
        if (parameterTypes.length == 1) {
            Class<?> requestType = parameterTypes[0];
            return new RestDslRouteProcessor(service, javaMethod, requestType);
        } else {
            throw new RuntimeException("method " + javaMethod.getName()
                    + " not valid signature (more than one arg)");
        }
    }
}
