package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.LeadCardPage;
import pages.MyRegion;

public class LeadsSteps{

    MyRegion region;
    LeadCardPage leads;

    private BaseClass base;

    public LeadsSteps(BaseClass base){
        this.base = base;
    }

    @When("I view a Leads card in either the My Region: List: All or My Region: List: My Priorities tabs")
    public void iViewALeadsCardInEitherTheMyRegionListAllOrMyRegionListMyPrioritiesTabs() throws Exception {
        try{
            region = new MyRegion( base.driver.getDriver());
            Thread.sleep(4000);
            Assert.assertTrue("My priorities link is not present",region.myPriorities.isDisplayed());
            base.driver.screenshot( base.snr,"All_Leads");
            Thread.sleep(1000);
            region.myPriorities.click();
            Assert.assertTrue("All leads link is not present",region.allLeads.isDisplayed());
            base.driver.screenshot( base.snr,"MyPriorities");
            region.allLeads.click();

        }catch(AssertionError e){
            base.driver.screenshot(base.snr,"Erorr");
            throw new Exception(e.getMessage());
        }

    }

    @Then("I see these field values on the card: Lead type \\(Prospect or Customer), Company Name, Address, Next Meeting, Composite Score \\(Prospect only), Contract Size \\(Customer only), Distance from User \\(based on map integration & GPS location)")
    public void iSeeTheseFieldValuesOnTheCardLeadTypeProspectOrCustomerCompanyNameAddressNextMeetingCompositeScoreProspectOnlyContractSizeCustomerOnlyDistanceFromUserBasedOnMapIntegrationGPSLocation() {
            leads = new LeadCardPage( base.driver.getDriver());
            leads.VerifyCardsElements();
    }

    @Then("selected My Priorities lead cards")
    @Then("select My Priorities lead cards")
    @When("I select a star icon from a Leads card")
    public void iSelectAStarIconFromALeadsCard() throws Exception {
        try{
            Thread.sleep(4000);
            leads = new LeadCardPage(base.driver.getDriver());
            Assert.assertTrue("Customer card is not selected to my priorities",leads.SelectCardToPriority(1));
            Thread.sleep(1500);
            Assert.assertTrue("Prospect card is not selected to my priorities",leads.SelectCardToPriority(1));
            base.driver.screenshot(base.snr,"SelectedCards");
        }catch(AssertionError e){
            base.driver.screenshot(base.snr,"Erorr");
            throw new Exception(e.getMessage());
        }
    }


    @Then("I see that Leads Card under the My Region: My Priorities tab")
    public void iSeeThatLeadsCardUnderTheMyRegionMyPrioritiesTab() throws Exception {
        try{
            region = new MyRegion( base.driver.getDriver());
            region.myPriorities.click();
            base.driver.screenshot(base.snr,"PrioritiesCards");
            Assert.assertEquals ("There are no cards in the priorities list",2,leads.listOfCards.size() );
        }catch(AssertionError e){
            base.driver.screenshot(base.snr,"Erorr");
            throw new Exception(e.getMessage());
        }
    }


    @When("I deselect a star icon")
    public void iDeselectAStarIcon() throws Exception {
        try{
            region = new MyRegion( base.driver.getDriver());
            region.myPriorities.click();
            Thread.sleep(1500);
            base.driver.screenshot(base.snr,"PrioritiesCards");
            leads.DeSelectPriorities(1);
            Thread.sleep(1500);
            leads.DeSelectPriorities(1);
        }catch(AssertionError e){
            base.driver.screenshot(base.snr,"Erorr");
            throw new Exception(e.getMessage());
        }

    }
    @Then("all associated Leads cards are removed from the My Priorities page.")
    @Then("that Lead card is removed from the My Priorities page.")
    public void thatLeadCardIsRemovedFromTheMyPrioritiesPage() {
        base.driver.screenshot(base.snr,"PrioritiesCards");
        Assert.assertEquals ("There are no cards in the priorities list",0,leads.listOfCards.size() );
    }

    @When("I click on the Clear All button")
    public void iClickOnTheClearAllButton() {
        region = new MyRegion( base.driver.getDriver());
        region.myPriorities.click();
        region.ClearAllPriorities();

    }



}
