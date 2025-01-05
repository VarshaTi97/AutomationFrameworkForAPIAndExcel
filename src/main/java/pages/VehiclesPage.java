package pages;

import constants.APIConstants;
import io.restassured.response.Response;

public class VehiclesPage extends BasePage{
    private static final VehiclesPage vehiclesPage = new VehiclesPage();

    private VehiclesPage(){};
    public static VehiclesPage getVehiclesPage(){
        return vehiclesPage;
    }

    public Response getAllVehicles(){
        return getFromEndpoint(APIConstants.PLANET);
    }

    public Response getVehicles(String url){
        return apiUtils.get(url);
    }
}
