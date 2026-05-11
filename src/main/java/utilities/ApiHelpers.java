package utilities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.Map;
import java.util.function.Supplier;

public class ApiHelpers {


    public RequestSpecification requestSpecificationWithJSONHeader() {
        return new RequestSpecBuilder().setContentType(ContentType.JSON).build();
    }

    public RequestSpecification requestSpecificationWithMultiPart() {
        return new RequestSpecBuilder().setContentType(ContentType.MULTIPART).build();
    }

    public RequestSpecification requestSpecificationWithContentTypeEncoded() {
        return new RequestSpecBuilder().setContentType(ContentType.URLENC).build();
    }

    public RequestSpecification requestSpecificationWithXMLHeader() {
        return new RequestSpecBuilder().setContentType(ContentType.XML).build();
    }

    public RequestSpecification requestSpecificationWithTextHeader() {
        return new RequestSpecBuilder().setContentType(ContentType.TEXT).build();
    }

    public RequestSpecification requestSpecificationWithHTMLHeader() {
        return new RequestSpecBuilder().setContentType(ContentType.HTML).build();
    }

    public RequestSpecification requestSpecificationWithFormUrlEncoded() {
        return new RequestSpecBuilder().setContentType(ContentType.URLENC).build();
    }

    public RequestSpecification requestSpecificationWithBinaryHeader() {
        return new RequestSpecBuilder().setContentType(ContentType.BINARY).build();
    }

    public RequestSpecification requestSpecificationWithAnyHeader() {
        return new RequestSpecBuilder().setContentType(ContentType.ANY).build();
    }

    public RequestSpecification requestSpecificationWithApiKeyAuthentication(String apiKey) {
        return new RequestSpecBuilder().addHeader("api_key", apiKey).build();
    }

    public RequestSpecification requestSpecificationWithCustomHeader(String headerName, String headerValue) {
        return new RequestSpecBuilder().addHeader(headerName, headerValue).build();
    }

    public Response retryApi(Supplier<Response> apiCall, int maxRetries, long waitMilliSeconds) {
        Response response = null;

        for (int i = 1; i <= maxRetries; i++) {
            try {
                response = apiCall.get();
                if (response != null && response.statusCode() == Constants.SUCCESS_CODE) {
                    return response;
                }
                System.out.println("Attempt " + i + " failed with status " + (response != null ? response.statusCode() : "null") + ", retrying...");
            } catch (Exception e) {
                System.out.println("Attempt " + i + " threw exception: " + e.getMessage());
            }

            try {
                Thread.sleep(waitMilliSeconds);
            } catch (InterruptedException ignored) {
            }
        }

        return response;
    }

    public Response sendPostRequest(String apiEndpoint, Object request) {
        Response response = retryApi(() -> RestAssured.given()
                        .body(request)
                        .when()
                        .post(apiEndpoint),
                Constants.RETRY_COUNT,
                Constants.RETRY_INTERVAL);

        Assert.assertEquals(response.getStatusCode(), Constants.SUCCESS_CODE, "Status code does not matched");
        return response;
    }

    public Response sendPostRequestWithQueryParams(String apiEndpoint, Map<String, Object> queryParams) {
        Response response = retryApi(() -> RestAssured.given()
                        .queryParams(queryParams)
                        .when()
                        .post(apiEndpoint),
                Constants.RETRY_COUNT,
                Constants.RETRY_INTERVAL);

        Assert.assertEquals(response.getStatusCode(), Constants.SUCCESS_CODE,
                "Status code does not match");
        return response;
    }


    public Response sendGetRequest(String apiEndpoint, Object request) {
        Response response = retryApi(() -> RestAssured.given()
                        .body(request)
                        .when()
                        .get(apiEndpoint),
                Constants.RETRY_COUNT,
                Constants.RETRY_INTERVAL);

        Assert.assertEquals(response.getStatusCode(), Constants.SUCCESS_CODE, "Status code does not matched");
        return response;
    }

    public Response sendGetRequestWithQueryParams(String apiEndpoint, Map<String, Object> queryParams) {
        Response response = retryApi(() -> RestAssured.given()
                        .queryParams(queryParams)
                        .when()
                        .get(apiEndpoint),
                Constants.RETRY_COUNT,
                Constants.RETRY_INTERVAL);

        Assert.assertEquals(response.getStatusCode(), Constants.SUCCESS_CODE, "Status code does not match");
        return response;
    }


    public Response sendPutRequest(String apiEndpoint, Object request, boolean isJignectPortal) {
        Response response = retryApi(() -> RestAssured.given()
                        .body(request)
                        .when()
                        .put(apiEndpoint),
                Constants.RETRY_COUNT,
                Constants.RETRY_INTERVAL);

        Assert.assertEquals(response.getStatusCode(), Constants.SUCCESS_CODE, "Status code does not matched");
        return response;
    }


    public Response sendDeleteRequest(String apiEndpoint, Object request, boolean isJignectPortal) {
        Response response = retryApi(() -> RestAssured.given()
                        .body(request)
                        .when()
                        .delete(apiEndpoint),
                Constants.RETRY_COUNT,
                Constants.RETRY_INTERVAL);

        Assert.assertEquals(response.getStatusCode(), Constants.SUCCESS_CODE, "Status code does not matched");
        return response;
    }

    public Response sendDeleteRequest(String apiEndpoint, String pathParamKey, String pathParamValue) {
        Response response = retryApi(() -> RestAssured.given()
                        .pathParam(pathParamKey, pathParamValue)
                        .when()
                        .delete(apiEndpoint),
                Constants.RETRY_COUNT,
                Constants.RETRY_INTERVAL);

        Assert.assertEquals(response.getStatusCode(), Constants.SUCCESS_CODE, "Status code does not matched");
        return response;
    }

    public Response sendPutRequest(String apiEndpoint, String pathParamKey, String pathParamValue) {
        Response response = retryApi(() -> RestAssured.given()
                        .queryParam(pathParamKey, pathParamValue)
                        .when()
                        .put(apiEndpoint),
                Constants.RETRY_COUNT,
                Constants.RETRY_INTERVAL);

        Assert.assertEquals(response.getStatusCode(), Constants.SUCCESS_CODE, "Status code does not matched");
        return response;
    }
}
