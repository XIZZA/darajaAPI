package com.apache.darajaAPI.routes.A;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Component
public class Router extends RouteBuilder {

    @Autowired
    private AuthConfig authConfig;

    @Override
    public void configure() throws Exception {
        // Configure the HTTP component
        // HttpComponent http = getContext().getComponent("http4"); // No need to create HttpComponent

        // Build the HTTP request using OkHttpClient
        String url = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";

        from("http://localhost:8080/v1/api/daraja")
                .process(exchange -> {
                    // Use OkHttpClient to make the request
                    OkHttpClient client = new OkHttpClient().newBuilder().build();
                    String authorizationHeader = "Basic SWZPREdqdkdYM0FjWkFTcTdSa1RWZ2FTSklNY001RGQ6WUp4ZVcxMTZaV0dGNFIzaA==";

                    Request request = new Request.Builder()
                            .url(url)
                            .method("GET", null)
                            .addHeader("Authorization", authorizationHeader)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        String responseBody = response.body().string();

                        // Process the response body (existing logic)
                        String token = extractToken(responseBody);
                        exchange.getIn().setHeader("Authorization", "Bearer " + token);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Handle exception appropriately (e.g., throw error)
                    }
                });
    }

    // This method needs to be implemented based on Safaricom's response format
    private String extractToken(String responseBody) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> responseMap = mapper.readValue(responseBody, Map.class);
            return (String) responseMap.get("access_token"); // Return the extracted token
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // Handle exception appropriately, e.g., throw an error
        }
    }
}