package com.vova_cons.rest_dsl.server;

import spark.route.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by anbu on 19.08.20.
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SparkRoute {
    String route();
    HttpMethod method();
}
