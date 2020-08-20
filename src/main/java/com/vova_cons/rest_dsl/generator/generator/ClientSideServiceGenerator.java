package com.vova_cons.rest_dsl.generator.generator;

import com.vova_cons.rest_dsl.StringUtils;
import com.vova_cons.rest_dsl.generator.parser.RestDslRouteSemanthicModel;
import com.vova_cons.rest_dsl.generator.parser.RestDslServiceSemanthicModel;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import static com.vova_cons.rest_dsl.StringUtils.toCamelCase;
import static com.vova_cons.rest_dsl.StringUtils.toFirstLetterLower;

/**
 * Created by anbu on 20.08.20.
 **/
public class ClientSideServiceGenerator {
    private static final String CLASS_TEMPLATE =
            "package ${PACKAGE};\n" +
            "\n" +
            "import com.vova_cons.rest_dsl.client.RequestCallback;\n" +
            "import com.vova_cons.rest_dsl.client.RestServerWorker;\n" +
            "${REQUEST_RESPONSE_IMPORTS}" +
            "import org.eclipse.jetty.http.HttpMethod;\n" +
            "\n" +
            "/**\n" +
            " * Created by RestDsl on ${DATE}\n" +
            " **/\n" +
            "public class ${SERVICE_ID}Service extends RestServerWorker {\n" +
            "    @Override\n" +
            "    public String getUrl() {\n" +
            "        return \"${URL}\";\n" +
            "    }\n" +
            "${ROUTES}" +
            "}\n";
    private static final String ROUTE_IMPORT_TEMPLATE =
            "import ${PACKAGE}.${REQUEST};\n" +
            "import ${PACKAGE}.${RESPONSE};\n";
    private static final String ROUTE_TEMPLATE = "\n" +
            "    public void ${ROUTE_METHOD}(${REQUEST} request, RequestCallback<${RESPONSE}> callback) {\n" +
            "        sendRequest(request, callback, ${RESPONSE}.class, \"/${ROUTE}\", HttpMethod.${METHOD});\n" +
            "    }\n";

    public void generate(String path, RestDslServiceSemanthicModel model) {
        try {
            Writer writer = new FileWriter(new File(path + "client/" + toCamelCase(model.id) + "Service.java"));
            StringBuilder requestResponseImports = new StringBuilder();
            StringBuilder routes = new StringBuilder();
            for(RestDslRouteSemanthicModel route : model.routes) {
                requestResponseImports.append(ROUTE_IMPORT_TEMPLATE
                        .replaceAll("\\$\\{PACKAGE}", model.server.model.dtoClasessPackage)
                        .replaceAll("\\$\\{REQUEST}", route.requestType)
                        .replaceAll("\\$\\{RESPONSE}", route.responseType)
                );
                routes.append(ROUTE_TEMPLATE
                        .replaceAll("\\$\\{ROUTE}", route.route)
                        .replaceAll("\\$\\{METHOD}", route.method)
                        .replaceAll("\\$\\{RESPONSE}", route.responseType)
                        .replaceAll("\\$\\{ROUTE_METHOD}", toFirstLetterLower(toCamelCase(route.route)))
                        .replaceAll("\\$\\{REQUEST}", route.requestType)
                );
            }
            String url = "";
            if (model.isSecure) {
                url = "https://" + model.server.ip + ":" + model.port;
            } else {
                url = "http://" + model.server.ip + ":" + model.port;
            }
            String javaFileContent = CLASS_TEMPLATE
                    .replaceAll("\\$\\{PACKAGE}", model.server.model.dtoClasessPackage)
                    .replaceAll("\\$\\{REQUEST_RESPONSE_IMPORTS}", requestResponseImports.toString())
                    .replaceAll("\\$\\{SERVICE_ID}", toCamelCase(model.id))
                    .replaceAll("\\$\\{ROUTES}", routes.toString())
                    .replaceAll("\\$\\{URL}", url)
                    .replaceAll("\\$\\{DATE}", StringUtils.getDate());
            writer.write(javaFileContent);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
