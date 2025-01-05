package pages;

import constants.APIConstants;
import io.restassured.response.Response;

public class PeoplePage extends BasePage{
    private static final PeoplePage peoplePage = new PeoplePage();

    private PeoplePage(){};
    public static PeoplePage getPeoplePage(){
        return peoplePage;
    }

    public Response getAllPeople(){
        return getFromEndpoint(APIConstants.PEOPLE);
    }

    public Response getPeopleByIndex(int index){
        return getFromEndpoint(APIConstants.PEOPLE+index+"/");
    }
}
