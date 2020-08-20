package com.vova_cons.rest_dsl.generator.lexer;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

/**
 * Created by anbu on 20.08.20.
 **/
public class RestDslLexer {
    private List<RestDslToken> tokens;
    private int lineNumber;
    private String filename;

    public List<RestDslToken> process(String inputFilename) {
        tokens = new LinkedList<>();
        try {
            this.filename = inputFilename;
            Scanner reader = new Scanner(new File(inputFilename));
            lineNumber = 1;
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                parseLine(line);
                lineNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokens;
    }

    private void parseLine(String line) {
        line = line.trim();
        while(!line.isEmpty()) {
            String newLine = searchToken(line);
            if (newLine == null) {
                char skipChar = line.charAt(0);
                line = line.substring(1); // skip
                System.err.println(filename + ":" + lineNumber + " skip char " + skipChar);
            } else {
                line = newLine;
            }
            if (line.startsWith("//")) {
                return;
            }
        }
    }

    private String searchToken(String line) {
        for(RestDslTokenType tokenType : RestDslTokenType.values()) {
            Matcher matcher = tokenType.pattern.matcher(line);
            if (matcher.find()) {
                String value = matcher.group();
                RestDslToken token = new RestDslToken(tokenType, value);
                tokens.add(token);
                String newLine = line.substring(value.length()).trim();
                if (tokenType == RestDslTokenType.QUOTE_TEXT) {
                    token.value = value.substring(1).substring(0, value.length()-2);
                }
                return newLine;
            }
        }
        return null;
    }
}
