package com.vova_cons.rest_dsl.generator.generator;

import com.vova_cons.rest_dsl.generator.parser.RestDslSemanthicModel;
import com.vova_cons.rest_dsl.generator.parser.RestDslServerSemanthicModel;
import com.vova_cons.rest_dsl.generator.parser.RestDslServiceSemanthicModel;

/**
 * Created by anbu on 20.08.20.
 **/
public class RestDslCodeGenerator {

    public void generate(String outputDirectory, RestDslSemanthicModel model) {
        ServerSideServiceGenerator serverCodeGenerator = new ServerSideServiceGenerator();
        ClientSideServiceGenerator clientCodeGenerator = new ClientSideServiceGenerator();
        for(RestDslServerSemanthicModel server : model.servers) {
            for(RestDslServiceSemanthicModel service : server.services) {
                serverCodeGenerator.generate(outputDirectory, service);
                clientCodeGenerator.generate(outputDirectory, service);
            }
        }
    }
}
