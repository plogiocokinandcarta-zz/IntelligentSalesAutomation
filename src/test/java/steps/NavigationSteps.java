package steps;

import com.fasterxml.jackson.databind.ser.Serializers;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import pages.Login;
import pages.MyRegion;
import utils.Driver;

public class NavigationSteps  {

    boolean logged = false;
    MyRegion region;
    Login login;
    BaseClass base ;

    public NavigationSteps(BaseClass base){
        this.base = base;
    }

    @Before()
    public void setup(Scenario scenario){
        base.snr = scenario;
    }

    @After()
    public  void tearDown(){

        base.driver.getDriver().quit();
    }


    @Given("I'm a signed-in user {string}")
    public void iMASignedInUser(String arg0)  {
        try{
            base.driver = new Driver(arg0);
            region = new MyRegion(base.driver.getDriver());

            if (!logged){
                base.driver.getDriver().get("https://kc-pep-is-dev.web.app/");
                login = new Login(base.driver.getDriver());
                logged = login.LoginIntelligntSales(base.driver.properties.getProperty("user"),base.driver.properties.getProperty("pass"));
            }
        }
        catch(Exception e ){
            base.snr.log(e.getLocalizedMessage());
        }

    }

    @Then("I want to see toolbar options to view My Profile section \\(Hamburger stack), Search, and Filter")
    public void iWantToSeeToolbarOptionsToViewMyProfileSectionHamburgerStackSearchAndFilter() throws Exception {
        try{
            Assert.assertTrue("The profile section is not showing",region.CheckButtons());
            base.driver.screenshot(base.snr,"MenuSearchAndFilter");
        }
        catch(AssertionError e){
            base.driver.screenshot(base.snr,"Erorr");
            throw new Exception(e.getMessage());

        }

    }

    @When("I click on the hamburger stack menu icon")
    public void iClickOnTheHamburgerStackMenuIcon() {
        try{
            region.clickOnMenu();

        } catch(Exception e ){
            base.driver.screenshot(base.snr,"Erorr");
        }

    }

    @Then("a navigation drawer swipes right to reveal the My Profile section.")
    public void aNavigationDrawerSwipesRightToRevealTheMyProfileSection() {
        try{
            boolean isProfile = region.GoToProfile();
            Assert.assertTrue("The profile section is not showing",isProfile);
        }catch(NoSuchElementException e){   base.driver.screenshot(base.snr,"Erorr");}


        base.driver.screenshot(base.snr,"MyProfile");
    }

    @Then("a right swipe drawer appears with placeholder sections for User info, Profile,Terms of Use,Help,Logout")
    public void aRightSwipeDrawerAppearsWithPlaceholderSectionsForUserInfoProfileTermsOfUseHelpLogout() {
        base.driver.screenshot(base.snr,"Menu Buttons");
        region.VerifyButtons(base.driver.properties.getProperty("user"));

    }

    @When("I want to logout of the App")
    public void iWantToLogoutOfTheApp() {
        region.clickOnMenu();
        base.driver.screenshot(base.snr,"LogOutCompleted");

    }

    @Then("I navigate to the My Profile drawer where I can click on Logout towards the bottom of the menu.")
    public void iNavigateToTheMyProfileDrawerWhereICanClickOnLogoutTowardsTheBottomOfTheMenu() {

            region.logOut.click();

    }
}

