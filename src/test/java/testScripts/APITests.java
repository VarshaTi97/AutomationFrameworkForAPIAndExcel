package testScripts;

import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojoModels.*;

import java.util.List;


public class APITests extends APIBaseClass {

    @Test
    /* Testcase1: Total number of planets returned by the API should be 61 and total number of people
    returned by API should be 87*/
    public void validateTotalNumberOfPlanetsAndPeople() {
        //check total count of planets
        Response responseOfPlanet = planetPage.getAllPlanets();
        planetResponse = responseOfPlanet.body().as(PlanetResponse.class);
        softAssert.assertEquals(planetResponse.getCount(), 61, "Count of the planets is not correct");

        //check total count of people
        Response responseOfPeople = peoplePage.getAllPeople();
        peopleResponse = responseOfPeople.body().as(PeopleResponse.class);
        softAssert.assertEquals(peopleResponse.getCount(), 87, "Count of the people is not correct");
    }

    //Test data to be used by testcase2
    @DataProvider(name = "filmDataProvider")
    public Object[][] filmDataProvider() {
        return new Object[][]{
                // Test Data: Film Title and Expected Planets
                {"A New Hope", new String[]{"Tatooine", "Alderaan", "Yavin IV"}},
        };
    }

    /* Testcase2: Planets in A new Hope film should be Tatooine ,Alderaan and Yavin IV.*/
    @Test(dataProvider = "filmDataProvider")
    public void testPlanetsInFilm(String filmTitle, String[] expectedPlanets) {

        Response responseOfFilms = filmsPage.getAllFilms();
        filmsResponse = responseOfFilms.body().as(FilmsResponse.class);
        List<String> listOfPlanetsForFilm = filmsPage.getListOfPlanetsForFilm(filmsResponse, filmTitle);

        // Assert the size of actual vs expected planets
        softAssert.assertEquals(
                expectedPlanets.length,
                listOfPlanetsForFilm.size(),
                "Mismatch in number of planets for film: " + filmTitle
        );

        // Assert each planet name
        for (String expectedPlanet : expectedPlanets) {
            softAssert.assertTrue(
                    listOfPlanetsForFilm.contains(expectedPlanet),
                    "Expected planet '" + expectedPlanet + "' not found in actual planets: " + listOfPlanetsForFilm
            );
        }
    }

    /* Testcase3: The first person returned by the API (people/1) should be Luke Skywalker and his vehicles
    should be 'Snowspeeder' with a 'max_atmosphering_speed'; of 650 and 'Imperial Speeder
    Bike' with a 'max_atmosphering_speed' of 360.*/
    @Test
    public void testToCheckFirstPersonVehicleDetails() {

        // Prepare the query parameters with film name

        Response responseOfPerson = peoplePage.getPeopleByIndex(1);
        people = responseOfPerson.body().as(People.class);

        //assert name of the first person
        softAssert.assertEquals(people.getName(), "Luke Skywalker", "Name of the first person is not correct");

        for (String vehicle : people.getVehicles()) {
            Response vehicleResponse = vehiclesPage.getVehicles(vehicle);
            vehicleObject = vehicleResponse.body().as(Vehicle.class);

            // assert max_atmosphering_speed for Snowspeeder
            if (vehicleObject.getName().equalsIgnoreCase("Snowspeeder")) {
                softAssert.assertEquals(Integer.parseInt(vehicleObject.getMaxAtmospheringSpeed()), 650, "Incorrect max for Snowspeeder");
            }
            // assert max_atmosphering_speed for Imperial Speeder Bike
            else if (vehicleObject.getName().equals("Imperial Speeder Bike")) {
                softAssert.assertEquals(Integer.parseInt(vehicleObject.getMaxAtmospheringSpeed()), 360, "Incorrect max speed for Imperial Speeder Bike");
            }
        }
    }

    /* Testcase4: Planet "Kamino" should have a diameter of 19720 and three residents: "Boba Fett", "Lama Su" and "Taun We"*/
    @Test(dataProvider = "planetDataProvider")
    public void testToCheckDetailsOfPlanet(String planetName, String expectedDiameter, String[] expectedResidents) {

        // Prepare the query parameters with film name

        Response responseOfPlanets = planetPage.getAllPlanets();
        planetResponse = responseOfPlanets.body().as(PlanetResponse.class);


        List<String> listOfResidentsForPlanet = planetPage.getListOfResidentsForPlanet(planetResponse, planetName);

        //Check Diameter of planet
        softAssert.assertEquals(expectedDiameter, planetPage.getDiameterOfPlanet(planetResponse, planetName), "Diameter of " + planetName + " is incorrect.");

        // Assert the size of actual vs expected residents
        softAssert.assertEquals(
                expectedResidents.length,
                listOfResidentsForPlanet.size(),
                "Mismatch in number of residents for planet: " + planetName
        );

        // Assert each planet name
        for (String expectedResident : expectedResidents) {
            softAssert.assertTrue(
                    listOfResidentsForPlanet.contains(expectedResident),
                    "Expected resident '" + expectedResident + "' not found in actual residents: " + listOfResidentsForPlanet
            );
        }
    }

    // Test data for testcase4
    @DataProvider(name = "planetDataProvider")
    public Object[][] planetDataProvider() {
        return new Object[][]{
                {"Kamino", "19720", new String[]{"Boba Fett", "Lama Su", "Taun We"}}};
    }
}
