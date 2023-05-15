import com.google.gson.Gson;
import model.Api_crud_element;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Api_crud {
    private final static String BASE_URL = "https://jsonplaceholder.typicode.com/users/1/posts";
    private final static Gson GSON = new Gson();

    private HttpClient client = HttpClient.newBuilder().build();

    // "wyciągam" GET'em pojedynczy element, by podminić żądane pola do operacji POST
    public Optional<Api_crud_element> getById(Long idToGet){
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(BASE_URL + "/" + idToGet))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return Optional.ofNullable(GSON.fromJson(response.body(), Api_crud_element.class));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean delete(Long elementToDeleteId) {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(BASE_URL + "/" + elementToDeleteId))
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return GSON.fromJson(response.body(), Boolean.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List getAll(){   // Uwaga: można zrobić GET'a na konkretny element - URI/elementId
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(BASE_URL))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return GSON.fromJson(response.body(), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList();
    }

    public void sendOrPut(Api_crud_element elementToSend){

        String jsonElementToSend = GSON.toJson(elementToSend);

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(BASE_URL))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonElementToSend))    // z PUT'em nie ma żadnej odpowiedzi
                .header("Content-Type", "application/json")
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Api_crud_element elementToUpdate){

        String jsonElementToUpdate = GSON.toJson(elementToUpdate);

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(jsonElementToUpdate))
                .header("Content-Type", "application/json")
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
