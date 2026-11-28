import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

public class ProductInfo {

    public String retrieveProduct(String url) {
        // "CARTSCOUT_API_KEY" is declared as an environment variable to prevent the API key from being displayed on GitHub
        String apiKey = System.getenv("CARTSCOUT_API_KEY");

        JsonObject body = new JsonObject();
        body.addProperty("url", url);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://cartscout-api.p.rapidapi.com/api/v1/products/extract"))
                    .header("X-RapidAPI-Key", apiKey)
                    .header("X-RapidAPI-Host", "cartscout-api.p.rapidapi.com")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (IOException e) {
            IO.println("Failed to connect to the CartScout API");
        } catch (InterruptedException e) {
            IO.println("Request for url was interrupted");
        }
        return null;
    }

}