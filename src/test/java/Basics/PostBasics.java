package Basics;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostBasics {

    /*  Post- Post is used to send data to server to create / update a resource.
        The data send to the server with POST is stored in the request body of a HTTPRequest.
        Client has to inform server about the type of the request body using Content type Header eg application/json,
        for json payload Request object
            1- Request Line- URL, Port (in case of development environment and for production http: 80 and https: ),
                            Path parameter
            2- Headers- Content -type is must
            3- Body
    */
    RequestSpecification httpRequest;
    @BeforeMethod
    public void setup(){
        RestAssured.baseURI = "http://localhost:5002";
        RestAssured.basePath="/api/members";
        Header acceptHeader= new Header("Accept", "application/json");

        //Content-Type = application/json
        Header contentTypeHeader = new Header("Content-Type", "application/json");

        List<Header> headers = new ArrayList<>();
        headers.add(acceptHeader);
        headers.add(contentTypeHeader);
        Headers allHeaders = new Headers(headers);

        httpRequest = RestAssured.given().
                        auth().
                        basic("admin", "admin").
                        headers(allHeaders);

    }


    @Test(enabled = false)
    public void createMembers(){
    //Add body to http request
        String body = "{\n" +
                "        \"name\": \"Ganesh\",\n" +
                "        \"gender\":\"Male\"\n" +
                "}";
        httpRequest.body(body);

        //Send request and store the response

        Response response = httpRequest.post();

        //Display the output

        String responseBody = response.getBody().asPrettyString();
        System.out.println(responseBody);

    }

    @Test(enabled = false)
    public void createMembersBDD(){
        // add Body

        String body = "{\n" +
                "        \"name\": \"Ganesh\",\n" +
                "        \"gender\":\"Male\"\n" +
                "}";

        //Add body to httpRequest

        //Send Request to server and store the response

        Response response = httpRequest.body(body).when().post();
        System.out.println(response.asPrettyString());
    }

    @Test(enabled = false)
    public void createMembersUsingMapBDD() {
        // add Body
        Map<String, String> body = new HashMap<>();
        body.put("name", "Jhalak");
        body.put("gender", "Female");
        //Add body to httpRequest

        //Send Request to server and store the response
        Response response = httpRequest.body(body)
                                        .when()
                                        .post()
                                        .andReturn();
        System.out.println(response.asPrettyString());
    }

    @Test(enabled = false)
    public void createMembersUsingJsonObjectBDD() {
        // add Body
        JsonObject body = new JsonObject();
        body.addProperty("name", "Heer");
        body.addProperty("gender", "Female");
        //Add body to httpRequest

        //Send Request to server and store the response
        Response response = httpRequest.body(body)
                .when()
                .post()
                .andReturn();
        System.out.println(response.asPrettyString());
    }

    @Test(enabled = false)
    public void createMembersUsingJsonFile(){
        // Read file from location --> "/src/test/resources/payloads/inputMembers.json"

        File fileLocation = new File(System.getProperty("user.dir")+"/src/test/resources/payloads/inputMembers.json");
        Response response = httpRequest.body(fileLocation).
                                        when().
                                        post().
                                        andReturn();
        System.out.println(response.getBody().asPrettyString());

    }

    @Test(enabled = false)
    public void createMembersUsingJsonAsInputStreamBDD(){
        /** Read file from location --> "src/test/resources/payloads/inputMembers.json"
         * When we use getClass().getClassLoader().getResourceAsStream() method , It reads file from resources location.
         * So there no need to give path as given above.
         * Instead give path directly from "/payloads/inputMember.Json"
         */

        InputStream body = getClass().getClassLoader().getResourceAsStream("payloads/inputMembers.json");
        Response response = httpRequest.body(body).
                                        when().
                                        post();
        System.out.println(response.getBody().asPrettyString());
    }
    @Test(enabled = false)
    public void createMembersUsingJsonAsByteArrayBDD() throws IOException {
        /** Read file from location --> "src/test/resources/payloads/inputMembers.json"
         * When we use getClass().getClassLoader().getResourceAsStream() method , It reads file from resources location.
         * So there no need to give path as given above.
         * Instead give path directly from "/payloads/inputMember.Json"
         */
       byte[] body = Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/src/test/resources/payloads/inputMembers.json"));

       Response response = httpRequest.body(body).
                when().
                post();
        System.out.println(response.getBody().asPrettyString());
    }
}
