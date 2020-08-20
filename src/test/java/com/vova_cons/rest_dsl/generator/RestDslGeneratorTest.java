package com.vova_cons.rest_dsl.generator;

import org.junit.Test;

/**
 * Created by anbu on 20.08.20.
 **/
public class RestDslGeneratorTest {

    @Test
    public void integrationTest() {
        RestDslGenerator.generate("test/test.rest_dsl", "test/output/");
    }
}