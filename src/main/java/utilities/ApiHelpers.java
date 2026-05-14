package utilities;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.Map;
import java.util.function.Supplier;

public class ApiHelpers {


    private static final ThreadLocal<String> BASE_URI = new ThreadLocal<>();

    public static void setBaseUri(String baseUri) {
        BASE_URI.set(baseUri);
    }

    public static void clearBaseUri() {
        BASE_URI.remove();
    }

    private static RequestSpecBuilder baseRequestSpecBuilder() {

        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI.get())
                .addFilter(new AllureRestAssured());
    }

    public RequestSpecification requestSpecificationWithJSONHeader() {
        return baseRequestSpecBuilder().setContentType(ContentType.JSON).build();
    }

    public RequestSpecification requestSpecificationWithMultiPart() {
        return baseRequestSpecBuilder().setContentType(ContentType.MULTIPART).build();
    }

    public RequestSpecification requestSpecificationWithContentTypeEncoded() {
        return baseRequestSpecBuilder().setContentType(ContentType.URLENC).build();
    }

    public RequestSpecification requestSpecificationWithXMLHeader() {
        return baseRequestSpecBuilder().setContentType(ContentType.XML).build();
    }

    public RequestSpecification requestSpecificationWithTextHeader() {
        return baseRequestSpecBuilder().setContentType(ContentType.TEXT).build();
    }

    public RequestSpecification requestSpecificationWithHTMLHeader() {
        return baseRequestSpecBuilder().setContentType(ContentType.HTML).build();
    }

    public RequestSpecification requestSpecificationWithFormUrlEncoded() {
        return baseRequestSpecBuilder().setContentType(ContentType.URLENC).build();
    }

    public RequestSpecification requestSpecificationWithBinaryHeader() {
        return baseRequestSpecBuilder().setContentType(ContentType.BINARY).build();
    }

    public RequestSpecification requestSpecificationWithAnyHeader() {
        return baseRequestSpecBuilder().setContentType(ContentType.ANY).build();
    }

    public RequestSpecification requestSpecificationWithApiKeyAuthentication(String apiKey) {
        return baseRequestSpecBuilder().addHeader("api_key", apiKey).build();
    }

    public RequestSpecification requestSpecificationWithAuthorization(String headerValue) {
        return baseRequestSpecBuilder().addHeader("Authorization", headerValue).build();
    }

    public RequestSpecification requestSpecificationWithCustomHeader(String headerName, String headerValue) {
        return baseRequestSpecBuilder().addHeader(headerName, headerValue).build();
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


    public Response sendPutRequest(String apiEndpoint, Object request) {
        Response response = retryApi(() -> RestAssured.given()
                        .body(request)
                        .when()
                        .put(apiEndpoint),
                Constants.RETRY_COUNT,
                Constants.RETRY_INTERVAL);

        Assert.assertEquals(response.getStatusCode(), Constants.SUCCESS_CODE, "Status code does not matched");
        return response;
    }


    public Response sendDeleteRequest(String apiEndpoint, Object request) {
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
