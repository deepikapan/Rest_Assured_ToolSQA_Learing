package com.restassured;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

import java.util.List;
import java.util.Map;

public class ValidatingHttpResponse {
    RequestSpecification httpRequest;
    Response response;
    int statusCode;

    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = "https://demoqa.com";
        //RestAssured.basePath = "/BookStore/v1/Books";
        httpRequest = RestAssured.given();
        response = httpRequest.get("");
    }
    @Test
    public void getBookDetailsAndValidateStatusCode() {
        response = httpRequest.get("");

        statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "correct Status Code returned");

    }


    @Test
    public void getBookDetailsAndValidateStatusLine() {
        String statusLine = response.getStatusLine();

        Assert.assertEquals(statusLine, "HTTP/1.1 200 OK", "correct Status Line returned");

    }

    @Test
    public void validateResponseBody() {
        ResponseBody resBody = response.getBody();
        System.out.println(resBody.asPrettyString());
    }

    @Test
    public void validateSomeStringFromResponseBody(){

        ResponseBody resBody= response.getBody();
        String resBodyString = resBody.asString();
        Assert.assertEquals(resBodyString.contains("Git Pocket Guide"), true,
                "Response Body contains Git Pocket Guide" +
                "" );
    }

    @Test
    public void extractNodeTextFromResponseBody(){

        /** Get object of JsonPAth from response */
        JsonPath jPath = response.jsonPath();

        /** Simply query the JsonPath object to get String of the node. */
       String authorName= jPath.get("books[0].author");
       Assert.assertEquals(authorName, "Richard E. Silverman", "Response body has author name : "+authorName);

    }

}
