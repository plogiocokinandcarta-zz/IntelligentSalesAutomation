package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;


public class Login extends BasePage{


    public Login(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.ID, using = "i0116")
    public WebElement inputUser;

    @FindBy(how = How.ID, using = "idSIButton9")
    public WebElement nextButton;

    @FindBy(how = How.ID, using = "i0118")
    public WebElement inputPass;

    @FindBy(how = How.ID, using = "idSIButton9")
    public WebElement singIn;

    @FindBy(how = How.ID, using = "idSIButton9")
    public WebElement yesButton;

    @FindBy(how = How.XPATH, using = "//*[text()='My Region']")
    public WebElement myRegion;


    public boolean LoginIntelligntSales(String user, String pass)  {
        wait.until(elementToBeClickable(inputUser));

        inputUser.sendKeys(user);
        wait.until(elementToBeClickable(nextButton));
        nextButton.click();
        wait.until(elementToBeClickable(inputPass));
        inputPass.sendKeys(pass);
        singIn.click();
        try {
            Thread.sleep(5000);
            wait.until(elementToBeClickable(yesButton));
            yesButton.click();
        }
        catch(Exception ignored){}

        if (!isElementPresent(myRegion)){

            yesButton.click();
        }

        wait.until(ExpectedConditions.elementToBeClickable(myRegion));

        return isElementPresent(myRegion);
    }
}
