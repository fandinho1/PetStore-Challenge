package petstore.stepdefinitions;

import static net.serenitybdd.rest.SerenityRest.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static petstore.utils.JsonReader.readJsonAsClass;

import java.util.Random;

import org.hamcrest.Matchers;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.Serenity;
import petstore.models.Pet;

public class PetStepDefinition {

	public static final String BASE_URI = "http://localhost:8080";
	public static final String BASE_PATH = "/api/v3/pet/";

	Response response;
	RequestSpecification requestSpecification;

	@Given("the user wanted to consume pets apis")
	public void theUserWantedToConsumePetsApis() {
		requestSpecification = given().baseUri(BASE_URI).basePath(BASE_PATH).contentType(ContentType.JSON);
	}

	@When("he tries to get a pet by id {string}")
	public void heTriesToGetAPetById(String petId) {
		response = requestSpecification.when().get(petId).andReturn();
	}

	@Then("he should see the expected pet {string} for that id")
	public void heShouldSeeTheExpectedPetForThatId(String jsonExpectedPet) {
		Pet actualPet = response.getBody().as(Pet.class);
		Pet expectedPet = readJsonAsClass(jsonExpectedPet, Pet.class);
		response.then().assertThat().statusCode(200);
		assertEquals(expectedPet.getId(), actualPet.getId());
		assertEquals(expectedPet.getCategory().getName(), actualPet.getCategory().getName());
		assertEquals(expectedPet.getName(), actualPet.getName());
		assertEquals(expectedPet.getStatus(), actualPet.getStatus());
		assertTrue(expectedPet.getPhotoUrls().equals(actualPet.getPhotoUrls()));
	}

	@Then("he should see the message {string}")
	public void heShouldSeeThatThePetIsNotFound(String expectedMessage) {
		response.then().assertThat().statusCode(404);
		assertTrue(response.getBody().asString().contains(expectedMessage));
	}

	@Then("he should see an error due to a bad request {string}")
	public void heShouldSeeAnErrorDueToABadRequest(String petId) {
		response.then().assertThat().statusCode(400);
		response.then().assertThat().body("message", Matchers
				.equalTo(String.format("Input error: couldn't convert `%s` to type `class java.lang.Long`", petId)));
	}

	@When("he tries to update a pet using correct data {string}")
	public void heTriesToUpdateAPetUsingCorrectDataA(String data) {
		response = requestSpecification.body(readJsonAsClass(data, Pet.class)).when().put().andReturn();
	}

	@Then("he should see the pet is updated correctly")
	public void heShouldSeeThePetIsUpdatedCorrectly() {
		response.then().assertThat().statusCode(200);
	}

	@When("he adds a new pet using correct data {string}")
	public void heAddsANewPetUsingCorrectData(String data) {
		Random random = new Random();
		Pet petToAdd = readJsonAsClass(data, Pet.class);
		petToAdd.setId(random.nextInt(100, 10000));
		Serenity.setSessionVariable("petCreated").to(petToAdd);
		response = requestSpecification.body(petToAdd).when().post().andReturn();
	}

	@Then("he should see the pet is added correctly")
	public void heShouldSeeThePetIsAddedCorrectly() {
		response.then().assertThat().statusCode(200);
	}
	

	@When("he deletes the pet created")
	public void heDeletesThePetCreated() {
		Pet petCreaded = Serenity.sessionVariableCalled("petCreated");
		response = requestSpecification.when().delete(String.valueOf(petCreaded.getId())).andReturn();
	}
	
	@Then("he should see the pet is deleted correctly")
	public void heShouldSeeThePetIsDeletedCorrectly() {
		response.then().assertThat().statusCode(200);
		assertTrue(response.getBody().asString().contains("Pet deleted"));
	}

}
