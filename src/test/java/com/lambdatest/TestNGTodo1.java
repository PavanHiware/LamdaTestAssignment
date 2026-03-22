package com.lambdatest;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodo1 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @SuppressWarnings("deprecation")
	@BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = "pavanhiware11";
        String authkey = "LT_jNCTayEqS0ANefOn6f97OAeSqKTNOMtYedb8MVBmGH47iQv";

        String hub = "@hub.lambdatest.com/wd/hub";

        // ✅ Use LambdaTest W3C-compliant structure (LT:Options)
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 11");
        browserOptions.setBrowserVersion("latest");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", username);
        ltOptions.put("accessKey", authkey);
        ltOptions.put("project", "Untitled");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        browserOptions.setCapability("LT:Options", ltOptions);
        /*
        Enable Smart UI Project (optional)
        ltOptions.setCapability("smartUI.project", "<Project Name>");
        */

//        // ✅ Safari browser setup (works with Selenium 4.x)
//        ChromeOptions browserOptions = new ChromeOptions();
//        browserOptions.setCapability("browserVersion", "latest");
//        browserOptions.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), browserOptions);
        String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
        System.out.println("LambdaTest Session ID: " + sessionId);
    }

    @Test
    public void basicTest() throws InterruptedException {
        System.out.println("Loading Url");
        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Opening WebApp\", \"level\": \"info\"}}");

        driver.get("https://www.testmuai.com/selenium-playground/");

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Checking List Items\", \"level\": \"info\"}}");

        System.out.println("clicking on simple link text ");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Simple Form Demo")));
        driver.findElement(By.linkText("Simple Form Demo")).click();
        System.out.println("clicked on link");
        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Adding Items\", \"level\": \"info\"}}");
        
        String url = driver.getCurrentUrl();
        if(url.contains("simple-form-demo")) {
        	Assert.assertEquals(url, "https://www.testmuai.com/selenium-playground/simple-form-demo/");
        	System.out.println("URL validation is completed....");
        }

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Checking More Items\", \"level\": \"info\"}}");
        String inputString = "Welcome to TestMu AI";
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-message")));
        driver.findElement(By.id("user-message")).sendKeys(inputString);
        driver.findElement(By.xpath("//button[@id='showInput']")).click();

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Adding and Verify List Items\", \"level\": \"info\"}}");

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p[@id='message']"))));
        String displayedMessage = driver.findElement(By.xpath("//p[@id='message']")).getText();
        Assert.assertEquals(displayedMessage, "Welcome to TestMu AI");
        System.out.println("Message reflecting correct..");
        Status = "passed";
        Thread.sleep(150);

        System.out.println("Test Finished");
    }
    

    @AfterMethod
    public void tearDown() {
        try {
            driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Adding Test Result and Closing Browser\", \"level\": \"info\"}}");
            driver.executeScript("lambda-status=" + Status);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
