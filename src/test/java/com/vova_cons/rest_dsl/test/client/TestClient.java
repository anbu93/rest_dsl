package com.vova_cons.rest_dsl.test.client;

import com.vova_cons.rest_dsl.test.dto.TestRequest;

/**
 * Created by anbu on 19.08.20.
 **/
public class TestClient {
    public static void main(String[] args) {
        TestService testService = new TestService();
        TestRequest request = new TestRequest();
        request.uid = 123456;
        request.message = "Test";
        request.args = new int[] { 1, 2, 3, 4, 5 };
        testService.test(request, (response) -> {
            if (response.isSuccess) {
                System.out.println(response.answer);
            } else {
                System.out.println("Error: " + response.answer);
            }
        });
    }
}
