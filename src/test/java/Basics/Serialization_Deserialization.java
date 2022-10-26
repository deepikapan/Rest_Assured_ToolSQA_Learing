package Basics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Member;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO = Plain old java object and this is used to increase the re-usability and readability of the program .
 * In general when we want to use Post method with request payload , It has to be in JSon format or it has to be
 * serialized in json before sending it to server.
 * Advantage of using POJo is that we can use it in both request and response body.
 * While sending in request, we have to serialize the POJO instance into JSon object using google JSon library .
 * Same way on receiving the response we can deserialize the response into POJO class instance and work with it.
 */

/**Serialization : Not limited to Java , It is available in most of the major languages.
 * It is a process in which we convert an object of a class into a byte stream.
 * Now this Byte stream can be stored as a file on the disk or can also be send to another computer via network.
 * So in this case, We are sending the request to server (another computer) using HTTP protocol which works on Network.
 * Serialization can be used to save the state of object when the program shuts down or Hybernates.
 * Once state is saved on disk using serialization , we can retrieve the state using deserialization .
 * Serializable object is a object which inherits from either of the two interface : java.io.serializable.
 *  since we are using Google JSon , we don't have to implement this explicitly.
 */

/** De-Serialization: It is just opposite to Serialization, In this, We read the Serialized Byte stream from the file
 * and convert it back into class state representation.
 */

public class Serialization_Deserialization {

    RequestSpecification httpRequest;
@BeforeMethod
public void setup(){
    RestAssured.baseURI = "http://localhost:5002";
    RestAssured.basePath = "/api/members";

    Header acceptHeader = new Header("Accept", "application/json");
    Header contentType = new Header("Content-Type","application/json");

    List<Header> header = new ArrayList<>();
    header.add(acceptHeader);
    header.add(contentType);

    Headers allHeaders = new Headers(header);
    httpRequest = RestAssured.given().
            auth().
            basic("admin", "admin").
            headers(allHeaders);
}

@Test(enabled = false)
    public void createMembersUsing_POJO_And_Transient_Keyword_BDD(){

    // Create  Body by instantiating Member models(POJO) class
    Member body = new Member("Steve", "male");

    System.out.println(body);
    Response response = httpRequest.body(body).
                                    log().
                                    all().
                                    when().
                                    post().
                                    andReturn();
    System.out.println(response.asPrettyString());
}

@Test(enabled = true)
    public void createMembersUsing_POJO_And_GSon_Exclusion_BDD(){

    // Create an instance of Member models (POJO) class

    Member member = new Member("Charlie", "Male");
    //Build a GSON object By excluding field without using expose annotation

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    // Convert the member instance into json
    String body = gson.toJson(member);

    System.out.print(body.toString());
    Response response = httpRequest.body(body).
                                    log().
                                    all().
                                    when().
                                    post().
                                    andReturn();
    System.out.print(response.asPrettyString());
}
}
