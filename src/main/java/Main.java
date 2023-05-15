import com.google.gson.Gson;
import model.Api_crud_Response;
import model.Api_crud_element;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    /*
    Uwaga:
    W pom.xml w sekcji build skompilować javę 11!

    - np. uruchamiamy zapytanie GET
    - jeśli chcemy inne zapytanie, komentujemy GET'a, robimy inne (odkomentowujemy i puszczamy)
      i potem puszczamy GET'a, żeby zobaczyć zmiany
     */

    public static void main(String[] args) {

        System.out.println();

        Gson gson = new Gson();

        HttpClient httpClient = HttpClient
                .newBuilder()
                .build();   // obiekt, który odpowiada za wykonanie zapytania (reprezentuje "coś"
                            // np. przegladarke, POSTMANA'a co potrafi wykonać zapytanie)
                            // obiekt, który może wykonać request
                            // potrzebujemy taki obiekt po to, żeby użyć na nim metody send
                            // obiekt taen potrafi wywołać send i dostać odpowiedź

        //  uri = (url + urn); url - do portu, urn po porcie
        //  np. http://192.168.110.34:8080/task/34 -> url:http://192.168.110.34:8080; urn:/task/34


        //  GET
        //  zapytanie zwraca listę obiektów
        HttpRequest httpRequest = HttpRequest
                .newBuilder(URI.create("https://jsonplaceholder.typicode.com/users/1/posts"))
                .GET()
                .build();   // zapytanie GET

        // DELETE
        // Zapytanie o usunięcie, na końcu zapytania mamy identyfikator elementu usuwanego
        // w wyniku otrzymujemy: true / false
//            HttpRequest httpRequest = HttpRequest
//                    .newBuilder(URI.create("https://jsonplaceholder.typicode.com/users/1/posts/3")) // dodajemy id do usunięcia
//                    .DELETE()
//                    .build();   // zapytanie DELETE



        // POST (przy POST'cie dodajemy BodyPublisher'a
        // tworzę obiekt, który chcę edytować, np. id = 2
//        Api_crud_element elementToEdit = new Api_crud_element();
//        elementToEdit.setUserId(1);
//        elementToEdit.setId(1);
//        elementToEdit.setTitle("editedElement");
//        elementToEdit.setBody("Buba");
//
//        String jsonElement = gson.toJson(elementToEdit);  // serializacja obiektu do stringa (zamiana obiekt -> tekst)
//
//        HttpRequest httpRequest = HttpRequest
//                .newBuilder(URI.create("https://jsonplaceholder.typicode.com/users/1/posts"))
//                .POST(HttpRequest.BodyPublishers.ofString(jsonElement))
//                .header("Content-Type", "aplication/json")
//                .build();   // zapytanie POST


        try {
            // wysłanie przez clienta zapytania (request)
            // BodyHandlers, klasa, która mówi co ma się wydarzyć z wynikiem

            // client to obiekt, który może wywołać SEND i przesłać request, a w wyniku otrzymuje odpowiedź (zawartość strony)
            // HttpResponse.BodyHandlers.ofString() - to gotowy obiekt, który "radzi sobie" z odpowiedzią.
            //                                        został napisany w taki sposób by wyjście przepisać w postaci string'a
            //                                        i zwrócić go w body obiektu HttpResponse (tego, który pojawia sie po lewej stronie przypisania).
            HttpResponse<String> httpResponse = null;
            try {
                httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List objects = gson.fromJson(httpResponse.body(), List.class);  //  GET     (sout(httpResponse.body() - widzimy odpowiedź)

//            Boolean objects = gson.fromJson(httpResponse.body(), Boolean.class);    // DELETE     (zamiana tekst -> obiekt)
//            System.out.println(objects);

            System.out.println("Print: objects.");
            objects.forEach(System.out::println);

            System.out.println();
            System.out.println("Print: httpResponse.body()");
            System.out.println(httpResponse.body());

            System.out.println();
            System.out.println("Print: objects.get(1)");
            System.out.println(objects.get(1));

            System.out.println();
            String object1ToString = objects.get(1).toString();
            System.out.println("Print: objects.get(1).toString()");
            System.out.println(object1ToString);

            List<Api_crud_element> crud_elements = (List<Api_crud_element>) objects.stream()
                    .map(Api_crud_element::new)
                    .collect(Collectors.toList());

            System.out.println();
            System.out.println("Print: crud_elements/1");
            System.out.println(crud_elements.get(1));

            System.out.println();
            System.out.println("Title/1");
            System.out.println(crud_elements.get(1).getTitle());

            System.out.println();
            crud_elements.forEach(System.out::println);

            for (int i = 0; i < crud_elements.size(); i++) {
                String body = crud_elements.get(i).getBody() + " " + i;
                crud_elements.get(i).setBody(body);
            }
            System.out.println();
            crud_elements.forEach(System.out::println);

            List listOldStyle = gson.fromJson(new InputStreamReader(new URL("https://jsonplaceholder.typicode.com/users/1/posts").openStream()), List.class);
            System.out.println();
            System.out.println("ListOldStyle");
            System.out.println("ListOldStyle size: " + listOldStyle.size());
//            listOldStyle.forEach(System.out::println);

            List<Api_crud_element> crud_elements_old_style = (List<Api_crud_element>) listOldStyle.stream()
                    .map(Api_crud_element::new)
                    .collect(Collectors.toList());

            System.out.println();
            System.out.println("Crud elements old style.");
            crud_elements.forEach(System.out::println);




        } catch (IOException e) {
            e.printStackTrace();
        }


        // _____________________________________________________________________________________________

        System.out.println();
        System.out.println();
        System.out.println("Metody: GET");
        Api_crud api_crud = new Api_crud();
        List list = api_crud.getAll();
        list.forEach(System.out::println);


    }
}
