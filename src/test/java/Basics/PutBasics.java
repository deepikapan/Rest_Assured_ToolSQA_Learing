package Basics;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PutBasics {

Response response;
RequestSpecification httpRequest;

@BeforeMethod()
    public void setup(){

    RestAssured.baseURI = "http://localhost:5002";
    RestAssured.basePath = "/api/members/{id}";

    Header acceptHeader = new Header("Accept", "application/json");
    Header contentTypeHeader = new Header("Content-Type", "application/json");

    List<Header> allHeader = new ArrayList<Header>();
    allHeader.add(acceptHeader);
    allHeader.add(contentTypeHeader);

    Headers headerList = new Headers(allHeader);

    httpRequest = RestAssured.given().
                              auth().
                              basic("admin", "admin").
                              headers(headerList).
                              pathParam("id", 2);
}

@Test(enabled = true)
public void updateMembers(){
    Map<String, String> body = new HashMap<>();
    body.put("name", "Rehaan");
    body.put("gender", "male");

    response = httpRequest.body(body)
                            .when()
                            .put()
                            .andReturn();
    System.out.println(response.asPrettyString());
    System.out.println(+response.getStatusCode());
}
}
