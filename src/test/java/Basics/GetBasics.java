package Basics;

import io.restassured.RestAssured;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.when;

public class GetBasics {

    @BeforeMethod
    public void setup(){
       RestAssured.baseURI = "http://localhost:5002";
        RestAssured.basePath="/api/members";
    }

    @Test()
    public void getMembers(){
        //Arrange

        //Request Line : Base URI, Base Path-- Set in BeforeMethod

        // Headers
        RestAssured.authentication = RestAssured.basic("admin", "admin");
        RequestSpecification httpRequest = RestAssured.given();

        Header headerValues = new Header("Accept", "application/json");
        httpRequest.header(headerValues);

        //Body -- NA for get method
        //Act
        Response response = httpRequest.request(Method.GET);

        // Assert

        //Status Code and Status Line
        Assert.assertEquals(response.getStatusCode(), 200);
        // Status Line
        Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");

        //Headers
        Headers header1= response.headers();
        for (Header hd :
             header1) {
                System.out.println("Key is : "+hd.getName()+ " Value is : "+hd.getValue());
        }

        //Body
        String bodyContent = response.getBody().asPrettyString();
        System.out.println("***************************************************************");
        System.out.println(bodyContent);
    }

    @Test()
    public void getFemaleMembersBDD(){
        //Arrange

        // RequestLine

        //Header
        //auth
        //QueryParam

        Header hd = new Header("Accept", "application/json");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("gender", "Female");

        Response response =
                RestAssured.given()
                            .auth()
                            .basic("admin", "admin")
                            .header(hd)
                            .queryParams(queryParams).
                    when().
                            get();

        //body

        //Assert
        System.out.println(response.getBody().asPrettyString());
    }

    @Test()
    public  void getSpecificMemberBDD(){

        //Arrange
        //RequestLine -- Set in BeforeMethod
        RestAssured.basePath += "/{id}";
        // Request Specs

        Header hd = new Header("Accept", "application/json");
        Response response = RestAssured.given()
                                                .auth()
                                                .basic("admin", "admin")
                                                .header(hd)
                                                .pathParam("id",3)
                                        .when()
                                                .get();
        System.out.println(response.getBody().asPrettyString());
    }


}
