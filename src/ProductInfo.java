import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

public class ProductInfo {

    public String fetchProduct(String url) {
        String apiKey = System.getenv("CARTSCOUT_API_KEY");

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("X-RapidAPI-Key", apiKey)
                    .header("X-RapidAPI-Host", "cartscout-api.p.rapidapi.com")
                    .GET()
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