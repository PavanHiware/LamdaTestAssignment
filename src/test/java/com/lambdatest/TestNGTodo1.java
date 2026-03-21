package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariOptions;
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
    }

    @Test
    public void basicTest() throws InterruptedException {
        System.out.println("Loading Url");
        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Opening WebApp\", \"level\": \"info\"}}");

        driver.get("https://lambdatest.github.io/sample-todo-app/");

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Checking List Items\", \"level\": \"info\"}}");

        System.out.println("Checking Boxes...");
        driver.findElement(By.name("li1")).click();
        driver.findElement(By.name("li2")).click();
        driver.findElement(By.name("li3")).click();
        driver.findElement(By.name("li4")).click();

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Adding Items\", \"level\": \"info\"}}");

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 6");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 7");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 8");
        driver.findElement(By.id("addbutton")).click();

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Checking More Items\", \"level\": \"info\"}}");

        driver.findElement(By.name("li1")).click();
        driver.findElement(By.name("li3")).click();
        driver.findElement(By.name("li7")).click();
        driver.findElement(By.name("li8")).click();
        Thread.sleep(300);

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Adding and Verify List Items\", \"level\": \"info\"}}");
        driver.findElement(By.id("sampletodotext")).sendKeys("Get Taste of Lambda and Stick to It");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.name("li9")).click();

        String spanText = driver.findElement(By.xpath("//li[9]/span")).getText();
        Assert.assertEquals(spanText.trim(), "Get Taste of Lambda and Stick to It");

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
