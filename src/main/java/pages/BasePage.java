package pages;

import constants.APIConstants;
import io.restassured.response.Response;
import utils.APIUtils;

public class BasePage {
    protected APIUtils apiUtils = new APIUtils();

    public Response getFromEndpoint(String endpoint) {
        return apiUtils.get(APIConstants.BASE_URL + endpoint);
    }

}
