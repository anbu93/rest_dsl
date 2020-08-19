package com.vova_cons.rest_dsl.client;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;

/**
 * Created by anbu on 19.08.20.
 **/
public class JettyHttpRequestProcessor implements HttpRequestProcessor {
    @Override
    public void sendHttpRequest(String requestBody, String url, HttpMethod httpMethod, ResultProcessor resultProcessor) {
        try {
            HttpClient client = new HttpClient();
            client.start();
            ContentResponse response = client.newRequest(url)
                    .method(httpMethod)
                    .content(new StringContentProvider(requestBody), "application/json")
                    //.agent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:17.0) Gecko/20100101 Firefox/17.0")
                    .send();
            String responseBody = response.getContentAsString();
            resultProcessor.onSuccess(responseBody);
            client.stop();
        } catch (Exception e) {
            resultProcessor.onError(e);
        }
    }
}
