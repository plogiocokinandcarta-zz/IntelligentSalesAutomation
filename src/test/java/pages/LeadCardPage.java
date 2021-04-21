package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class LeadCardPage extends BasePage{
    public LeadCardPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.XPATH, using = "//*[@class='LeadCard_customerIcon__26vZe']")
    public List<WebElement> listCustomerIcon;

    @FindBy(how = How.XPATH, using = "//*[@class='MuiPaper-root MuiCard-root LeadCard_leadCardRoot__hmeUj MuiPaper-elevation1 MuiPaper-rounded']")
    public List<WebElement> listOfCards;

    @FindBy(how = How.XPATH, using = "//*[@class='LeadCard_prospectIcon__1kin1']")
    public List<WebElement> listProspectIcon;

    @FindBy(how = How.XPATH, using = "//*[@class='MuiTypography-root MuiTypography-h5']")
    public List<WebElement> listTitles ;

    @FindBy(how = How.XPATH, using  = "//*[@class='MuiTypography-root MuiTypography-subtitle1']")
    public List<WebElement> listDirection;

    @FindBy(how = How.XPATH, using = "//*[@class='MuiTypography-root LeadCard_distance__31TFb MuiTypography-body1']")
    public List<WebElement> listDistance ;

    @FindBy(how = How.XPATH, using = "//*[@class='MuiTypography-root MuiTypography-body1']")
    public List<WebElement> listNextVisitAndContract ;

    @FindBy(how = How.XPATH, using = "//*[@class='MuiSvgIcon-root LeadCard_starIcon__2qM9W']")
    public List<WebElement> listStarButton ;

    @FindBy(how = How.XPATH, using = "//*[@class='MuiSvgIcon-root LeadCard_starIcon__2qM9W LeadCard_prioritized__2i1hz']")
    public List<WebElement> listOfPriority ;


    public void VerifyCardsElements() {

        Assert.assertTrue("There are prospect that are not visualized",listProspectIcon.size() > 0 );
        Assert.assertTrue("There are customers that are not visualized",listCustomerIcon.size() > 0);
        Assert.assertTrue("Visit and contracts are not showing",listNextVisitAndContract.size() > 0 );
        Assert.assertTrue("Titles are not showing",listTitles.size() > 0);
        Assert.assertTrue("Star buttons are not showing",listStarButton.size() > 0);
        Assert.assertTrue("Distance is not showing",listDistance.size() > 0);
        Assert.assertTrue("Distance is not showing",listDirection.size() > 0);

    }

    public boolean SelectCardToPriority(int numberOfCard){
        int size = listStarButton.size();
        try {

            if (size != 0) {
                int n = numberOfCard - 1;
                Thread.sleep(1500);
                wait.until(elementToBeClickable(listStarButton.get(n)));
                listStarButton.get(n).click();
                size = size - 1;
            }
        }
        catch(Exception ignored){           }



        return size == listStarButton.size() ;
    }

    public boolean DeSelectPriorities(int numberOfCard){
        int size = listOfPriority.size();
        try {

            if (size != 0) {
                int n = numberOfCard - 1;
                Thread.sleep(1500);
                wait.until(elementToBeClickable(listOfPriority.get(n)));
                listOfPriority.get(n).click();
                size = size - 1;
            }
        }
           catch(Exception i){           }
        return size == listOfPriority.size() ;
    }
}
