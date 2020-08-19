package com.vova_cons.rest_dsl.test.client;

import com.vova_cons.rest_dsl.client.RequestCallback;
import com.vova_cons.rest_dsl.client.RestServerWorker;
import com.vova_cons.rest_dsl.test.dto.TestRequest;
import com.vova_cons.rest_dsl.test.dto.TestResponse;
import org.eclipse.jetty.http.HttpMethod;

/**
 * Created by anbu on 19.08.20.
 **/
public class TestService extends RestServerWorker {
    @Override
    public String getUrl() {
        return "http://127.0.0.1:8081";
    }

    public void test(TestRequest request, RequestCallback<TestResponse> callback) {
        sendRequest(request, callback, TestResponse.class, "/test", HttpMethod.GET);
    }
}
