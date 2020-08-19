package com.vova_cons.rest_dsl;

import com.vova_cons.rest_dsl.server.RestDslIgnition;
import com.vova_cons.rest_dsl.server.RestDslService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by anbu on 19.08.20.
 **/
public class RestDsl {
    public static RestDslIgnition ignition = new RestDslIgnition();

    public static void generate(String dslFilePath, String outputDirectory) {
        throw new NotImplementedException();
    }

    public static void ignite(RestDslService service) {
        ignition.ignite(service);
    }
}
