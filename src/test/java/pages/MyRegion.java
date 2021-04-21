package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class MyRegion extends BasePage{

    public MyRegion(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.XPATH, using = "//*[text()='Profile']")
    public WebElement profile;

    @FindBy(how = How.XPATH, using = "//*[text()='My Profile']")
    public WebElement myProfile;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Terms of Use')]")
    public WebElement termsOfUse;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Log out')]")
    public WebElement logOut;

    @FindBy(how = How.XPATH, using = "//html/body/div[2]/div[3]/div/p/div")
    public WebElement userLoged;

    @FindBy(how = How.XPATH, using = "//*[text()='View my priorities']")
    public WebElement myPriorities;

    @FindBy(how = How.XPATH, using = "//*[text()='View all leads']")
    public WebElement allLeads;

    @FindBy(how = How.XPATH, using = "//*[text()='Clear all priorities']")
    public WebElement clearAllPriorities;




    public String buttons = "//html/body/div/header/div/button/span[@class='MuiIconButton-label']" ;


    public boolean CheckButtons(){
        List<WebElement> list = wait.until(presenceOfAllElementsLocatedBy(By.xpath(buttons)));
        return list.size() == 3;
    }

    public boolean GoToProfile(){

            wait.until(elementToBeClickable(myProfile));
            profile.click();


        return isElementPresent(myProfile);
    }


    public void clickOnMenu() {
        List<WebElement> list = wait.until(presenceOfAllElementsLocatedBy(By.xpath(buttons)));
        list.get(0).click();
    }

    public void VerifyButtons(String userName) {
        Assert.assertEquals("User information does not show correctly",userLoged.getText().toUpperCase(),userName.toUpperCase());
        Assert.assertTrue("My profile button is not showing",isElementPresent(myProfile));
        Assert.assertTrue("Term of use is not showing",isElementPresent(termsOfUse));
        Assert.assertTrue("The profile section is not showing",isElementPresent(logOut));

    }

    public void ClearAllPriorities(){
        wait.until(elementToBeClickable(clearAllPriorities));
        clearAllPriorities.click();
    }

    public void logout() {
        clickOnMenu();
        logOut.click();

    }
}
