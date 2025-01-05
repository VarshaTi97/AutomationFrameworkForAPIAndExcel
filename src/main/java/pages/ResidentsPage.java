package pages;

import io.restassured.response.Response;
import pojoModels.Residents;

import java.util.ArrayList;
import java.util.List;

public class ResidentsPage extends BasePage{
    private static final ResidentsPage residentsPage = new ResidentsPage();

    private ResidentsPage(){};
    public static ResidentsPage getResidentsPage(){
        return residentsPage;
    }

    public List<String> getResidentsNames(List<String> residentsUrls){
        List<String> residentNames = new ArrayList<>();

        for (String url : residentsUrls) {
            Response residentResponse = apiUtils.get(url);
            Residents residents = residentResponse.body().as(Residents.class);
            residentNames.add(residents.getName());
        }
        return residentNames;
    }
}
