package petstore.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/pet.feature",
glue = "petstore.stepdefinitions",
snippets = SnippetType.CAMELCASE)
public class PetRunner {

}
