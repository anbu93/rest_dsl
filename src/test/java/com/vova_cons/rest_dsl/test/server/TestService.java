package com.vova_cons.rest_dsl.test.server;

import com.vova_cons.rest_dsl.server.RestDslService;
import com.vova_cons.rest_dsl.server.SparkRoute;
import com.vova_cons.rest_dsl.test.dto.TestRequest;
import com.vova_cons.rest_dsl.test.dto.TestResponse;
import spark.route.HttpMethod;

/**
 * Created by anbu on 19.08.20.
 **/
public class TestService extends RestDslService {
    public TestService() {
        super(8081);
    }

    @SparkRoute(route = "/test", method = HttpMethod.get)
    public TestResponse test(TestRequest request) {
        TestResponse response = new TestResponse();
        if (request == null) {
            response.isSuccess = false;
            response.answer = "Request are null";
            return response;
        }
        response.isSuccess = true;
        int summ = 0;
        for (int num : request.args) {
            summ += num;
        }
        response.answer = request.uid + "> " + request.message + ": " + summ;
        return response;
    }
}
