package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodo2 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @SuppressWarnings("deprecation")
	@BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = "pavanhiware11" ;
        String authkey = "LT_jNCTayEqS0ANefOn6f97OAeSqKTNOMtYedb8MVBmGH47iQv";

        String hub = "@hub.lambdatest.com/wd/hub";

        // ✅ LambdaTest-specific options (must go under "LT:Options")
        MutableCapabilities ltOptions = new MutableCapabilities();
        ltOptions.setCapability("build", "TestNG With Java");
        ltOptions.setCapability("name", m.getName() + this.getClass().getName());
        ltOptions.setCapability("platformName", "Windows 10");
        ltOptions.setCapability("plugin", "git-testng");
        ltOptions.setCapability("tags", new String[] { "Feature", "Magicleap", "Severe" });

        /*
        Enable Smart UI Project
        ltOptions.setCapability("smartUI.project", "<Project Name>");
        */

        // ✅ Standard browser options
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setCapability("browserVersion", "latest");
        browserOptions.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), browserOptions);
    }

    @Test
    public void basicTest() throws InterruptedException {
        System.out.println("Loading Url");
//open website
        driver.get("https://www.testmuai.com/selenium-playground/");
        System.out.println("clicking on simple link text ");
//click on drag & drop sliders    
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Drag & Drop Sliders")));
        driver.findElement(By.linkText("Drag & Drop Sliders")).click();
        System.out.println("clicked on link");
//validate the slider    
        WebElement slider = driver.findElement(By.xpath("//*[@id='slider3']"));
        WebElement inputSlider = slider.findElement(By.xpath("//*[@id='slider3']/div/input"));
        WebElement outputValue = slider.findElement(By.xpath("//*[@id='slider3']/div/output"));
        
        while (!outputValue.getText().equals("95")) {
			inputSlider.sendKeys(Keys.ARROW_RIGHT);
		}
        System.out.println("Slider is draged to required value");
//validate slider value 
        Assert.assertEquals(outputValue.getText(), "95");
        System.out.println("Slider validaton is complted.");

        Status = "passed";
        Thread.sleep(150);
        System.out.println("Test Finished");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.executeScript("lambda-status=" + Status);
            driver.quit();
        }
    }
}
