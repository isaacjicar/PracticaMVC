// src/main/java/org/example/mvcpractice/ui/HttpClientHelper.java
package org.example.mvcpractice.view;

import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;

public class HttpClientHelper {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static String get(String url) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> res = CLIENT.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (res.statusCode() >= 200 && res.statusCode() < 300) return res.body();
        throw new RuntimeException("GET " + url + " -> " + res.statusCode() + " : " + res.body());
    }

    public static String postJson(String url, String json) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> res = CLIENT.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (res.statusCode() >= 200 && res.statusCode() < 300) return res.body();
        throw new RuntimeException("POST " + url + " -> " + res.statusCode() + " : " + res.body());
    }

    public static String putJson(String url, String json) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> res = CLIENT.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (res.statusCode() >= 200 && res.statusCode() < 300) return res.body();
        throw new RuntimeException("PUT " + url + " -> " + res.statusCode() + " : " + res.body());
    }

    public static void delete(String url) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).DELETE().build();
        HttpResponse<String> res = CLIENT.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (res.statusCode() >= 200 && res.statusCode() < 300) return;
        throw new RuntimeException("DELETE " + url + " -> " + res.statusCode() + " : " + res.body());
    }
}
