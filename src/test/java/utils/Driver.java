package utils;

import com.google.common.collect.ImmutableMap;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;


public class Driver {

    public  Properties properties;
    private  WebDriver driver;
    private  ChromeOptions options;


    private void loadProperties() {
        try {
            PropertyLoader propertyLoader = new PropertyLoader();
            properties = propertyLoader.getPropValues("config.properties");
        } catch (Exception e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    //region Constructor

    public Driver(String browser) {
        loadProperties();


        switch (browser.toUpperCase(Locale.ROOT)) {

            case "CHROME":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                driver = (new FirefoxDriver());
                break;

            case "IPAD":
                loadProperties();

                setCapabilities("iPad");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
                driver.manage().window().maximize();

                break;

            case "IPHONE":
                loadProperties();
                setCapabilities("iPhone 6/7/8 Plus");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
                driver.manage().window().maximize();
                break;

            case "SAFARI":
                WebDriverManager.getInstance(DriverManagerType.SAFARI).setup();
                driver = (new SafariDriver());
                break;
        }

        driver.manage().window().maximize();

    }


    //endregion


    private void setCapabilities( String emulation){
       options = new ChromeOptions();
       Map<String, String> mobileEmulation = new HashMap<>();
       mobileEmulation.put("deviceName", emulation);


        options.setExperimentalOption("mobileEmulation", mobileEmulation);
       // options.setExperimentalOption("mobileEmulation", emulation);

    }



    //region Public methods

    public  WebDriver getDriver(){
        if (driver == null) {
        }
        return driver;
    }

    public  void screenshot(Scenario snr,String name)  {

        try{

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            byte[] ts =  ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            snr.attach(ts, "image/png","screenshot");

            // now copy the screenshot to desired location using copyFile //method
            FileUtils.copyFile(src, new File( System.getProperty("user.dir") + "target/" + properties.getProperty("screenshots")  + "/" + name + ".png"));

        }
        catch(Exception e){
            snr.log(e.getMessage());}
    }

    public  Properties getProperties() {
        return properties;
    }

    public  void  changeLocation(double latitude, double longitude){
        Location location = new Location(latitude, longitude, 1000);
        ((LocationContext)driver).setLocation(location);
    }

    public  void setUpBandwidth(int downloadSpeed, int uploadSpeed,boolean offline) throws IOException {
        CommandExecutor executor = ((RemoteWebDriver) driver).getCommandExecutor();

        Map<Object, Object> map = new HashMap<>();
        map.put("offline", offline);
        map.put("latency", 5);
        map.put("download_throughput", downloadSpeed);
        map.put("upload_throughput", uploadSpeed);
        Response response = executor.execute(new Command(((RemoteWebDriver) driver).getSessionId(), "setNetworkConditions",
                ImmutableMap.of("network_conditions", ImmutableMap.copyOf(map))));

    }

    @SafeVarargs
    public final boolean waitForPageLoad(int waitTimeInSec, ExpectedCondition<Boolean>... conditions) {
        boolean isLoaded = false;
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(waitTimeInSec))
                .ignoring(StaleElementReferenceException.class)
                .pollingEvery(Duration.ofSeconds(2));
        for (ExpectedCondition<Boolean> condition : conditions) {
            isLoaded = wait.until(condition);
            if (!isLoaded) {
                //Stop checking on first condition returning false.
                break;
            }
        }
        return isLoaded;
    }
    //endregion





}
