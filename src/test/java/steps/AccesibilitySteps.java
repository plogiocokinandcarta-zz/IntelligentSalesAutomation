package steps;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.Driver;

import java.util.ArrayList;
import java.util.List;

public class AccesibilitySteps extends BaseClass{

    private Results result;
    private String responseJson;
    String url;

    @Before("@Accessibility")
    public void setup(){

        driver = new Driver("Chrome");;
    }


    @After("@Accessibility")
    public  void tearDown(){
        driver.getDriver().quit();
    }

    @Given("the web page {string} is displayed")
    public void theWebPageIsDisplayed(String arg0) {
        driver.getDriver().get(arg0);
        url=arg0;
    }

    @When("I scan for accessibility concerns")
    public void iScanForAccessibilityConcerns() throws JsonProcessingException {
        driver.waitForPageLoad(5);

        ObjectMapper mapper = new ObjectMapper();
        List<String> rules = new ArrayList<String>();

        // rules to omit
        //rules.add("region");rules.add("landmark-one-main");

        AxeBuilder builder = new AxeBuilder();//.disableRules(rules);
        result = builder.analyze(driver.getDriver());
        List<Rule> violations = result.getViolations();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        responseJson = mapper.writeValueAsString(violations);
    }


    @Then("no accessibility issues should be found")
    public void noAccessibilityIssuesShouldBeFound() {
        AxeReporter.writeResultsToJsonFile("target/"+  result.getTimestamp(),result);
        Assert.assertTrue("accessibility issues : "+ responseJson,result.violationFree());
    }



}
