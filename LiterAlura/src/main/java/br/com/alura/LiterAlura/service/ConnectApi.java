package br.com.alura.LiterAlura.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConnectApi {

//TODO    String book
    public String connect(String book) {

        final String URLCONNECTION = "https://gutendex.com/books/?search=" + book.replace(" ", "+").trim();

        URI urlConnection = URI.create(URLCONNECTION);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlConnection)
                .build();

        String json;

        try {
            HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            json = response.body();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }

        return json;
    }

}
