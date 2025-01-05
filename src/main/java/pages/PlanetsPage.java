package pages;

import constants.APIConstants;
import io.restassured.response.Response;
import pojoModels.Planet;
import pojoModels.PlanetResponse;

import java.util.ArrayList;
import java.util.List;

import static pages.ResidentsPage.getResidentsPage;


public class PlanetsPage extends BasePage {
    private static final PlanetsPage planetsPage = new PlanetsPage();

    private PlanetsPage(){};
    public static PlanetsPage getPlanetsPage(){
        return planetsPage;
    }

    public Response getAllPlanets(){
        return getFromEndpoint(APIConstants.PLANET);
    }

    public List<String> getPlanetNames(List<String> planetUrls){
        List<String> planetNames = new ArrayList<>();

        for (String url : planetUrls) {
            Response planetResponse = apiUtils.get(url);
            Planet planet = planetResponse.body().as(Planet.class);
            planetNames.add(planet.getName());
        }
        return planetNames;
    }

    public List<String> getListOfResidentsForPlanet(PlanetResponse planetResponse, String planetName){
        List<String> residentsName = new ArrayList<>();
        // filter out the residents name
        for(Planet planet: planetResponse.getResults()){
            if(planet.getName().equalsIgnoreCase(planetName)) {
                residentsName = getResidentsPage().getResidentsNames(planet.getResidents());
            }
        }
        return residentsName;
    }

    public String getDiameterOfPlanet(PlanetResponse planetResponse, String planetName){
        String diameterOfPlanet="";
        // filter out the diameter of a planet
        for(Planet planet: planetResponse.getResults()){
            if(planet.getName().equalsIgnoreCase(planetName)) {
                diameterOfPlanet = planet.getDiameter();
            }
        }
        return diameterOfPlanet;
    }
}
