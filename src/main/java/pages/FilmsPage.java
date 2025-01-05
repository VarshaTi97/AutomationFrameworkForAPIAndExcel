package pages;

import constants.APIConstants;
import io.restassured.response.Response;
import pojoModels.Films;
import pojoModels.FilmsResponse;

import java.util.ArrayList;
import java.util.List;

import static pages.PlanetsPage.getPlanetsPage;

public class FilmsPage extends BasePage{

    private static final FilmsPage filmsPage = new FilmsPage();

    private FilmsPage(){
    };
    public static FilmsPage getFilmsPage(){
        return filmsPage;
    }

    public Response getAllFilms(){
        return getFromEndpoint(APIConstants.FILMS);
    }


    public List<String> getListOfPlanetsForFilm(FilmsResponse filmsResponse, String filmTitle){
        List<String> planetNames = new ArrayList<>();
        // filter out the film name
        for(Films film: filmsResponse.getResults()){
            if(film.getTitle().equalsIgnoreCase(filmTitle)) {
                planetNames = getPlanetsPage().getPlanetNames(film.getPlanets());
            }
        }
        return planetNames;
    }
}
