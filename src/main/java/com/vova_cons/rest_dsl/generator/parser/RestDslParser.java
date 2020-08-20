package com.vova_cons.rest_dsl.generator.parser;

import com.vova_cons.rest_dsl.generator.lexer.RestDslToken;
import com.vova_cons.rest_dsl.generator.lexer.RestDslTokenType;
import com.vova_cons.rest_dsl.server.RestDslService;

import java.util.Collections;
import java.util.List;

/**
 * Created by anbu on 20.08.20.
 **/
public class RestDslParser {
    private List<RestDslToken> tokens;
    private int index;
    private RestDslSemanthicModel model;
    private RestDslServerSemanthicModel server;
    private RestDslServiceSemanthicModel service;
    private RestDslRouteSemanthicModel route;

    public RestDslSemanthicModel parse(List<RestDslToken> tokens) {
        this.tokens = tokens;
        this.index = 0;
        model = new RestDslSemanthicModel();
        document();
        return model;
    }

    // document = dtoPackage server*
    private void document() {
        if (dtoPackage()) {
            while (server()){}
        }
        if (index < tokens.size()) {
            for(int i = index; i < tokens.size(); i++) {
                System.err.println("Not process token " + tokens.get(i));
            }
        }
    }

    // dtoPackage = DTO PACKAGE QUOTE_STRING ENDLINE
    private boolean dtoPackage() {
        int save = index;
        if (nextIs(RestDslTokenType.DTO) && nextIs(RestDslTokenType.PACKAGE)) {
            RestDslToken token = nextToken();
            if (token != null && token.type == RestDslTokenType.QUOTE_TEXT && nextIs(RestDslTokenType.ENDLINE)) {
                model.dtoClasessPackage = token.value;
                return true;
            }
        }
        reset(save);
        return false;
    }

    // server = SERVER ID OPEN serverIp service* CLOSE
    private boolean server() {
        int save = index;
        if (nextIs(RestDslTokenType.SERVER)) {
            RestDslToken idToken = nextToken();
            if (idToken != null && idToken.type == RestDslTokenType.ID && nextIs(RestDslTokenType.OPEN)) {
                server = new RestDslServerSemanthicModel();
                server.id = idToken.value;
                if (serverIp()) {
                    while(service()) {}
                    if (nextIs(RestDslTokenType.CLOSE)) {
                        model.addServer(server);
                        return true;
                    }
                }
            }
        }
        reset(save);
        return false;
    }

    // serverIp = IP QUOTE_STRING ENDLINE
    private boolean serverIp() {
        int save = index;
        if (nextIs(RestDslTokenType.IP)) {
            RestDslToken ipToken = nextToken();
            if (ipToken != null && ipToken.type == RestDslTokenType.QUOTE_TEXT) {
                if (nextIs(RestDslTokenType.ENDLINE)) {
                    server.ip = ipToken.value;
                    return true;
                }
            }
        }
        reset(save);
        return false;
    }

    // service = SERVICE ID OPEN port secure? route* CLOSE
    private boolean service() {
        int save = index;
        if (nextIs(RestDslTokenType.SERVICE)) {
            RestDslToken serviceIdToken = nextToken();
            if (serviceIdToken != null && serviceIdToken.type == RestDslTokenType.ID) {
                service = new RestDslServiceSemanthicModel();
                service.id = serviceIdToken.value;
                if (nextIs(RestDslTokenType.OPEN)) {
                    if (port()) {
                        secure();
                        while(route()) {}
                        if (nextIs(RestDslTokenType.CLOSE)) {
                            server.addService(service);
                            return true;
                        }
                    }
                }
            }
        }
        reset(save);
        return false;
    }

    // port = PORT NUMBER ENDLINE
    private boolean port() {
        int save = index;
        if (nextIs(RestDslTokenType.PORT)) {
            RestDslToken numberToken = nextToken();
            if (numberToken != null && numberToken.type == RestDslTokenType.NUMBER) {
                if (nextIs(RestDslTokenType.ENDLINE)) {
                    try {
                        service.port = Integer.parseInt(numberToken.value);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
            }
        }
        reset(save);
        return false;
    }

    // secure = HTTPS OPEN KEYSTORE QUOTE_STRING ENDLINE PASSWORD QUOTE_STRING ENDLINE CLOSE
    private boolean secure() {
        int save = index;
        if (nextIs(RestDslTokenType.HTTPS) && nextIs(RestDslTokenType.OPEN) && nextIs(RestDslTokenType.KEYSTORE)) {
            RestDslToken keystoreToken = nextToken();
            if (keystoreToken != null && keystoreToken.type == RestDslTokenType.QUOTE_TEXT) {
                if (nextIs(RestDslTokenType.ENDLINE) && nextIs(RestDslTokenType.PASSWORD)) {
                    RestDslToken passwordToken = nextToken();
                    if (passwordToken != null && passwordToken.type == RestDslTokenType.QUOTE_TEXT) {
                        if (nextIs(RestDslTokenType.ENDLINE) && nextIs(RestDslTokenType.CLOSE)) {
                            service.isSecure = true;
                            service.keystoreFile = keystoreToken.value;
                            service.keystorePassword = passwordToken.value;
                            return true;
                        }
                    }
                }
            }
        }
        reset(save);
        return false;
    }

    // route = ROUTE ID OPEN (method|request|response)* CLOSE
    private boolean route() {
        int save = index;
        if (nextIs(RestDslTokenType.ROUTE)) {
            RestDslToken routeIdToken = nextToken();
            if (routeIdToken != null && routeIdToken.type == RestDslTokenType.ID) {
                route = new RestDslRouteSemanthicModel();
                route.route = routeIdToken.value;
                if (nextIs(RestDslTokenType.OPEN)) {
                    while(method() || request() || response()) {}
                    if (nextIs(RestDslTokenType.CLOSE)) {
                        service.routes.add(route);
                        return true;
                    }
                }
            }
        }
        reset(save);
        return false;
    }

    // method = METHOD ID ENDLINE
    private boolean method() {
        int save = index;
        if (nextIs(RestDslTokenType.METHOD)) {
            RestDslToken idToken = nextToken();
            if (idToken != null && idToken.type == RestDslTokenType.ID && nextIs(RestDslTokenType.ENDLINE)) {
                route.method = idToken.value;
                return true;
            }
        }
        reset(save);
        return false;
    }

    // request = REQUEST ID ENDLINE
    private boolean request() {
        int save = index;
        if (nextIs(RestDslTokenType.REQUEST)) {
            RestDslToken idToken = nextToken();
            if (idToken != null && idToken.type == RestDslTokenType.ID && nextIs(RestDslTokenType.ENDLINE)) {
                route.requestType = idToken.value;
                return true;
            }
        }
        reset(save);
        return false;
    }

    // response = RESPONSE ID ENDLINE
    private boolean response() {
        int save = index;
        if (nextIs(RestDslTokenType.RESPONSE)) {
            RestDslToken idToken = nextToken();
            if (idToken != null && idToken.type == RestDslTokenType.ID && nextIs(RestDslTokenType.ENDLINE)) {
                route.responseType = idToken.value;
                return true;
            }
        }
        reset(save);
        return false;
    }


    private RestDslToken getToken() {
        return tokens.get(index);
    }

    private RestDslToken nextToken() {
        if (index < tokens.size()) {
            index++;
            return tokens.get(index - 1);
        }
        return null;
    }

    private boolean nextIs(RestDslTokenType type) {
        RestDslToken token = index < tokens.size() ? nextToken() : null;
        if (token != null) {
            return token.type == type;
        }
        return false;
    }

    private void reset(int save) {
        index = save;
    }
}
