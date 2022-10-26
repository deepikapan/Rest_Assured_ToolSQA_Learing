package Basics;

import io.restassured.http.Header;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class HamcrestLearning {


    @BeforeMethod
    public void setup(){
        baseURI = "http://localhost:5002";
        basePath="/api/members";
    }

    @Test
    public void getStaticSpecificMemberBDD(){
        basePath += "/{id}";

        Header hd = new Header("Accept", "application/json");

        given()
                .auth()
                .basic("admin", "admin")
                .header(hd)
                .pathParam("id",4)
        .when()
                .get()
        .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .header("X-Powered-By", "QA BOX LET'S TEST")
                .body(containsString("Shawn"))
                .body("name", equalTo("Shawn"));
    }
}
