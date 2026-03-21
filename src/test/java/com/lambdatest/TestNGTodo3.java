package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodo3 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @SuppressWarnings("deprecation")
	@BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
    	 String username = "pavanhiware11";
         String authkey = "LT_jNCTayEqS0ANefOn6f97OAeSqKTNOMtYedb8MVBmGH47iQv";

        String hub = "@hub.lambdatest.com/wd/hub";

        // ✅ W3C-compliant capabilities using ChromeOptions + LT:Options
        MutableCapabilities ltOptions = new MutableCapabilities();
        ltOptions.setCapability("build", "TestNG With Java");
        ltOptions.setCapability("name", m.getName() + " - " + this.getClass().getName());
        ltOptions.setCapability("platformName", "macOS Sonoma"); // updated OS name
        ltOptions.setCapability("plugin", "git-testng");
        ltOptions.setCapability("tags", new String[] { "Feature", "Tag", "Moderate" });

        // ✅ Chrome-specific options
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setBrowserVersion("latest");
        browserOptions.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), browserOptions);
    }

    @Test
    public void basicTest() throws InterruptedException {
    	  System.out.println("loading URL");
    	    // 1. Open the Playground and click "Input Form Submit"
    	    driver.get("https://www.lambdatest.com/selenium-playground/");
    	    
    	    // Using an explicit wait to ensure the link is clickable
    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Input Form Submit"))).click();
    	    
    	    // 2. Click "Submit" without filling in any information in the form
    	    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[text()='Submit']"))));
    	    WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
    	    submitButton.click();
    	    System.out.println("Submit button is clicked without adding info");
//    	    // 3. Assert "Please fill in this field." error message on the Name field
//    	    // Note: This is a browser-native validation message
    	    WebElement nameField = driver.findElement(By.id("name"));
//    	    wait.until(ExpectedConditions.visibilityOf(nameField));
//    	    String actualErrorMessage = nameField.getAttribute("validationMessage");
//    	    Assert.assertEquals(actualErrorMessage, "Please fill in this field.");
//    	    System.out.println("✅ Validation message verified: " + actualErrorMessage);

    	    // 4. Fill in Name, Email, and other fields
    	    nameField.sendKeys("Pavan Hiware");
    	    driver.findElement(By.id("inputEmail4")).sendKeys("pavan@example.com");
    	    driver.findElement(By.name("password")).sendKeys("Test@123");
    	    driver.findElement(By.id("company")).sendKeys("LambdaTest");
    	    driver.findElement(By.id("websitename")).sendKeys("https://www.lambdatest.com");

    	    // 5. From the Country drop-down, select "United States" using the text property
    	    WebElement countryDropdown = driver.findElement(By.name("country"));
    	    Select selectCountry = new Select(countryDropdown);
    	    selectCountry.selectByVisibleText("United States");
    	    System.out.println("Country selected as per requirement");
    	    // 6. Fill in remaining fields
    	    driver.findElement(By.id("inputCity")).sendKeys("San Jose");
    	    driver.findElement(By.id("inputAddress1")).sendKeys("123 Test Street");
    	    driver.findElement(By.id("inputAddress2")).sendKeys("Suite 100");
    	    driver.findElement(By.id("inputState")).sendKeys("California");
    	    driver.findElement(By.id("inputZip")).sendKeys("95131");

    	    // 7. Click "Submit"
    	    submitButton.click();
    	    System.out.println("form is submitted");

    	    // 8. Validate the success message
    	    // The message appears after a short delay, so we wait for its visibility
    	    WebElement successMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("success-msg")));
    	    
    	    String successMessage = successMsgElement.getText();
    	    Assert.assertEquals(successMessage, "Thanks for contacting us, we will get back to you shortly.");
    	    
    	    System.out.println("✅ Form submitted successfully: " + successMessage);
    	    
    	    // Mark the test as passed for LambdaTest Dashboard
    	    Status = "passed";
        Thread.sleep(300);
        System.out.println("✅ Test Finished Successfully");
    }

    @AfterMethod
    public void tearDown() {
        try {
            driver.executeScript("lambda-status=" + Status);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
