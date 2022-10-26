package Basics;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUploadBasics {


    /** To Access the this form :
     * 1- Go to visio studio .
     * 2- add extension to visio code studio i.e. live server.
     * 3- Select "Public" under project.
     * 4- Right click on index.html and click open with live server.
     * 5- This will open a page to upload file.
     *
     *
     * MultiPart Form Data : One or more different sets of data are combined in a single body is known as "Multipartform data".
     * Example, in some cases you have pictures to upload and as well as some text to enter such types of request is
     * multipart form.
     * Important:
     * Multipart form data: The "ENCTYPE" attribute of " <form>"  tag specifies the method of encoding for the form data.
     * It is one of the two ways of encoding the HTML form. It is specifically used when file uploading is required
     * in HTML form.
     * It sends the form data to server in multiple parts because of large size of file.
     *
     * Syntax:
     *
     * <form action="login.php" method="post"
     *     enctype="multipart/form-data">
     * </form>
     */

    RequestSpecification httpRequest;
    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = "http://localhost:5002";
        RestAssured.basePath = "/api/upload";

        Header contentTypeHeader = new Header("Content-Type", "multipart/form-data");
        Header acceptTypeHeader = new Header("Accept", "application/json");

        List<Header> headersList = new ArrayList<>();
        headersList.add(contentTypeHeader);
        headersList.add(acceptTypeHeader);

        Headers headers = new Headers(headersList);

        httpRequest= RestAssured.given().
                                 auth().
                                 basic("admin", "admin").
                                 headers(headers);
    }

    @Test
    public void uploadFile(){

        File file = new File(System.getProperty("user.dir")+ "/src/test/resources/uploadFiles/index.png");
        Response response = httpRequest.
                                multiPart("file", file).
                                multiPart("name", "Deepika").
                                when().
                                post().
                                andReturn();
        System.out.println(response.asPrettyString());
    }

}
