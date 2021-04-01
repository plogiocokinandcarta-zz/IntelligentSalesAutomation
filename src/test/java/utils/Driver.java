package utils;

import com.google.common.collect.ImmutableMap;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.time.Duration;
import java.util.HashMap;
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

    public Driver() {
        loadProperties();
        options = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        try{
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();

        }
        catch(Exception ignored){
        }
    }

    public Driver(String deviceEmulation) {
        loadProperties();
        options = new ChromeOptions();
        setCapabilities(deviceEmulation);
        WebDriverManager.chromedriver().setup();
        try{
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        catch(Exception ignored){        }

    }
    //endregion


    private void setCapabilities(String emulation){
       options = new ChromeOptions();
       // Map<String, Object> deviceMetrics = new HashMap<>();
       // deviceMetrics.put("width", 375);
       // deviceMetrics.put("height", 812);
       // deviceMetrics.put("pixelRatio", 3.0);
       Map<String, String> mobileEmulation = new HashMap<>();
       mobileEmulation.put("deviceName",emulation );
       // Map<String, Object> mobileEmulation = new HashMap<>();
       // mobileEmulation.put("deviceMetrics", deviceMetrics);
       // mobileEmulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.34 (KHTML, like Gecko) Version/11.0 Mobile/15A5341f Safari/604.1");
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
    }



    //region Public methods

    public  WebDriver getDriver() {
        if (driver == null) {
            new Driver();}
        return driver;
    }

    public  void screenshot(Scenario snr)  {

        try{

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // now copy the screenshot to desired location using copyFile //method
            FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "target/" + properties.getProperty("Screenshots")  + "screenshot.png"));

        }
        catch(Exception e){ e.printStackTrace();}
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
