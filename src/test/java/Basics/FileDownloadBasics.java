package Basics;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileDownloadBasics {

    /**
     There are chances when we download some file , it is very huge . In such case we have to write code in such a way
     that instead of loading entire file into the memory but to use a chunk transfer encoding feature HTTP 1.1 support.

     Chunk Transfer encoding : This is a streaming data transfer mechanism available in version 1.1 of HTTp. IN this
     the data streaming is divided into a series of non-overlapping chunks.These chunks are sent out and received
     independently of one another.
     Crucially, chunks are sent independently of one another, usually through a single persistent connection.
     The type is specified in the Transfer-Encoding header (in the first block).
     So we must test this header in response header.
     In other words, the receiver never sees the entire file.
     One use case for HTTP chunked encoding is in video streaming applications — breaking up video into smaller pieces
     also allows for near-instant load times for video, compared to a browser/client waiting for
     (a significant amount of) data before being able to render a single frame.

     How does it work?

     In a general sense, the client sees the first chunk almost immediately, resulting in a significantly improved
     user experience. While only video streaming and large file transfers have been used as examples thus far,
     HTTP Chunked Encoding works with any type of data, such as text.

     Data can also be compressed and chunked at the same time, though the compression algorithm used varies
     (gzip and deflate are commonly used compression formats).

     (NOTE: Servers will NEVER compress blocks individually -- data is first compressed then split up).
     Finally, the server (or sender) trasmits a final "empty" block:

     */

    RequestSpecification httpRequest;
    Response response;

    @BeforeMethod
    public void setUp(){

        RestAssured.baseURI = "http://localhost:5002";
        RestAssured.basePath = "/api/download";

//        Header contentTypeHeader = new Header("Content-Type", "multipart/form-data");
//        Header acceptTypeHeader = new Header("Accept", "application/json");
//        Header acceptEncoding = new Header("Accept-Encoding", "gzip" );
//        List<Header> headerList = new ArrayList<>();
//        headerList.add(contentTypeHeader);
//        headerList.add(acceptTypeHeader);
//
//        Headers allHeaders = new Headers(headerList);

        httpRequest = RestAssured.given().
                                  auth().
                                  basic("admin", "admin").queryParam("name", "Yey.jpg");
        response = httpRequest.
                            when().
                            get().
                            andReturn();
    }

    @Test(enabled = false)
    public void fileDownloadUsingByteArray() throws IOException {
    // Store the file into Byte array
        byte [] file = response.asByteArray();

    // Using FileOutputStream write byte array into physical File and close the resource.

        FileOutputStream fileOutputStream = new FileOutputStream((System.getProperty("user.dir")+"/src/test/resources/fileDownload/Deepika.jpg"));

        fileOutputStream.write(file);
        fileOutputStream.close();

    }

    @Test(enabled= false)
    public void fileDownloadUsingByteArrayAndTryBlockToCloseResource() throws IOException {
        //Store the file from response to BYte Array

        byte[] downloadFile = response.asByteArray();

        // Using FileOutstream, write byte array into file
        try (FileOutputStream fileOs = new FileOutputStream((System.getProperty("user.dir") + "/src/test/resources/fileDownload/Deepika.jpg")))
        {

            fileOs.write(downloadFile);

        }

    }

    @Test(enabled = true)
    public void fileDownloadUsingInputStream() throws IOException {
        InputStream fileDownloadIs = response.asInputStream();

        // Create the file object
        File fileLocationForDownload = new File(System.getProperty("user.dir")+"/src/test/resources/fileDownload/Test.jpg");
        // Using file copy the input stream to target path and close Input stream

        Files.copy(fileDownloadIs,fileLocationForDownload.toPath(), StandardCopyOption.REPLACE_EXISTING);
        fileDownloadIs.close();
    }


    @AfterMethod
    public void checkHeader(){
        response.then().header("Transfer-Encoding", "chunked");
    }
}
