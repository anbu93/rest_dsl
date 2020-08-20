package com.vova_cons.rest_dsl.generator.generator;

import com.vova_cons.rest_dsl.StringUtils;
import com.vova_cons.rest_dsl.generator.parser.RestDslRouteSemanthicModel;
import com.vova_cons.rest_dsl.generator.parser.RestDslSemanthicModel;
import com.vova_cons.rest_dsl.generator.parser.RestDslServiceSemanthicModel;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import static com.vova_cons.rest_dsl.StringUtils.*;

/**
 * Created by anbu on 20.08.20.
 **/
public class ServerSideServiceGenerator {
    private static final String CLASS_TEMPLATE =
            "package ${PACKAGE};\n" +
            "\n" +
            "import com.vova_cons.rest_dsl.server.RestDslService;\n" +
            "import com.vova_cons.rest_dsl.server.SparkRoute;\n" +
            "${REQUEST_RESPONSE_IMPORTS}" +
            "import spark.route.HttpMethod;\n" +
            "\n" +
            "/**\n" +
            " * Created by RestDsl on ${DATE}\n" +
            " **/\n" +
            "public abstract class ${SERVICE_ID}Service extends RestDslService {\n" +
            "    public ${SERVICE_ID}Service() {\n" +
            "        super(${PORT});\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public boolean isSecure() {\n" +
            "        return ${IS_SECURE};\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public String getKeystoreFile() {\n" +
            "        return ${KEYSTORE_FILE};\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public String getKeystorePassword() {\n" +
            "        return ${KEYSTORE_PASSWORD};\n" +
            "    }\n" +
            "${ROUTES}" +
            "}\n";
    private static final String ROUTE_IMPORT_TEMPLATE =
            "import ${PACKAGE}.${REQUEST};\n" +
            "import ${PACKAGE}.${RESPONSE};\n";
    private static final String ROUTE_TEMPLATE = "\n" +
            "    @SparkRoute(route = \"/${ROUTE}\", method = HttpMethod.${METHOD})\n" +
            "    public abstract ${RESPONSE} ${ROUTE_METHOD}(${REQUEST} request);\n";

    public void generate(String path, RestDslServiceSemanthicModel model) {
        try {
            Writer writer = new FileWriter(new File(path + "server/" + toCamelCase(model.id) + "Service.java"));
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
            String javaFileContent = CLASS_TEMPLATE
                    .replaceAll("\\$\\{PACKAGE}", model.server.model.dtoClasessPackage)
                    .replaceAll("\\$\\{REQUEST_RESPONSE_IMPORTS}", requestResponseImports.toString())
                    .replaceAll("\\$\\{SERVICE_ID}", toCamelCase(model.id))
                    .replaceAll("\\$\\{PORT}", "" + model.port)
                    .replaceAll("\\$\\{IS_SECURE}", ""+model.isSecure)
                    .replaceAll("\\$\\{ROUTES}", routes.toString())
                    .replaceAll("\\$\\{DATE}", getDate());
            if (model.isSecure) {
                javaFileContent = javaFileContent
                        .replaceAll("\\$\\{KEYSTORE_FILE}", "\"" + model.keystoreFile + "\"")
                        .replaceAll("\\$\\{KEYSTORE_PASSWORD}", "\"" + model.keystoreFile + "\"");
            } else {
                javaFileContent = javaFileContent
                        .replaceAll("\\$\\{KEYSTORE_FILE}", "null")
                        .replaceAll("\\$\\{KEYSTORE_PASSWORD}", "null");

            }
            writer.write(javaFileContent);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
