package testScripts;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;
import pages.*;
import pojoModels.*;


public class APIBaseClass {

    protected PeoplePage peoplePage;
    protected PeopleResponse peopleResponse;
    protected PlanetsPage planetPage;
    protected PlanetResponse planetResponse;
    protected FilmsPage filmsPage;
    protected FilmsResponse filmsResponse;
    protected VehiclesPage vehiclesPage;
    protected VehiclesResponse vehiclesResponse;
    protected SoftAssert softAssert;
    protected People people;
    protected Vehicle vehicleObject;
    protected ResidentsPage residentsPage;

    @BeforeClass
    public void setUp(){
        //create single instance of util and pages classes
        peoplePage = PeoplePage.getPeoplePage();
        planetPage = PlanetsPage.getPlanetsPage();
        filmsPage = FilmsPage.getFilmsPage();
        vehiclesPage = VehiclesPage.getVehiclesPage();
        residentsPage = ResidentsPage.getResidentsPage();
        softAssert = new SoftAssert();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        softAssert.assertAll();
    }
}
