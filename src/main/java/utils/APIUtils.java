package utils;

import constants.APIConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class APIUtils {

    //Get method
    public Response get(String url){
        RequestSpecification requestSpecification = RestAssured.given();
        return requestSpecification.when().get(url);
    }

    //Get method with query params
    public Response get(String url, Map<String, String> queryParams){
        RequestSpecification requestSpecification = RestAssured.given();
        if (queryParams != null && !queryParams.isEmpty()) {
            requestSpecification.queryParams(queryParams);
        }
        return requestSpecification.when().get(url);
    }

}
