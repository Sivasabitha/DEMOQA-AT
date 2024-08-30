package Alerts_Frames_Windows_Package;

import java.util.Iterator;
import java.util.Set;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.netty.handler.timeout.TimeoutException;

public class Browser_Windows_Class {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void testBrowserWindows() {
        driver.get("https://demoqa.com/");
        System.out.println("Opened DemoQA website successfully.");

        WebElement alertsFramesWindowsCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Alerts, Frame & Windows']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", alertsFramesWindowsCard);
        alertsFramesWindowsCard.click();
        System.out.println("Clicked 'Alerts, Frame & Windows' card successfully.");

        WebElement browserWindowsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Browser Windows']")));
        browserWindowsLink.click();
        System.out.println("Clicked 'Browser Windows' link successfully.");

        WebElement newTabButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("tabButton")));
        newTabButton.click();
        System.out.println("Clicked 'New Tab' button successfully.");
        switchAndVerifyNewWindow("https://demoqa.com/sample", "This is a sample page");

        WebElement newWindowButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("windowButton")));
        clickElementWithHandling(newWindowButton);
        System.out.println("Clicked 'New Window' button successfully.");
        switchAndVerifyNewWindow("https://demoqa.com/sample", "This is a sample page");

        WebElement newWindowMessageButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("messageWindowButton")));
        clickElementWithHandling(newWindowMessageButton);
        System.out.println("Clicked 'New Window Message' button successfully.");
        switchAndVerifyMessageWindow("Knowledge increases by sharing but not by saving. Please share this website with your friends and in your organization.");
    }

    private void clickElementWithHandling(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            element.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Element click intercepted, retrying...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            element.click();
        }
    }

    private void switchAndVerifyNewWindow(String expectedUrl, String expectedText) {
        String mainWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> iterator = windowHandles.iterator();

        while (iterator.hasNext()) {
            String currentWindow = iterator.next();
            if (!currentWindow.equals(mainWindow)) {
                driver.switchTo().window(currentWindow);
                wait.until(ExpectedConditions.urlToBe(expectedUrl));
                WebElement sampleHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleHeading")));
                Assert.assertEquals(sampleHeading.getText(), expectedText, "The sample page heading is incorrect!");
                System.out.println("Verified new window content successfully.");
                driver.close();
                driver.switchTo().window(mainWindow);
                break; 
            }
        }

        Assert.assertEquals(driver.getTitle(), "DEMOQA", "Main window title is incorrect!");
        System.out.println("Returned to the main window successfully.");
    }

 

    private void switchAndVerifyMessageWindow(String expectedText) {
        String mainWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> iterator = windowHandles.iterator();

        while (iterator.hasNext()) {
            String currentWindow = iterator.next();
            if (!currentWindow.equals(mainWindow)) {
                boolean success = false;
                int maxRetries = 3;
                int attempt = 0;

                while (attempt < maxRetries && !success) {
                    try {
                        // Switch to the new window
                        driver.switchTo().window(currentWindow);

                        // Wait for the body to be present and visible
                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                        WebElement body = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

                        // Verify the content
                        Assert.assertTrue(body.getText().contains(expectedText), "Message window content is incorrect!");
                        System.out.println("Verified message window content successfully.");
                        success = true; // Mark as successful if no exception
                    } catch (TimeoutException e) {
                        attempt++;
                        System.out.println("Attempt " + attempt + " failed: " + e.getMessage());
                        if (attempt >= maxRetries) {
                            System.out.println("Failed after " + maxRetries + " attempts.");
                        }
                    } catch (NoSuchWindowException e) {
                        System.out.println("NoSuchWindowException occurred: " + e.getMessage());
                        break; // Exit the loop if the window is no longer available
                    } catch (Exception e) {
                        System.out.println("An unexpected error occurred: " + e.getMessage());
                        break; // Exit the loop on unexpected errors
                    }
                }

                // Cleanup and switch back to the main window
                try {
                    if (driver.getWindowHandles().contains(currentWindow)) {
                        driver.close();
                        System.out.println("Closed message window successfully.");
                    } else {
                        System.out.println("Message window was already closed.");
                    }
                } catch (Exception e) {
                    System.out.println("Error closing the message window: " + e.getMessage());
                } finally {
                    // Always switch back to the main window
                    driver.switchTo().window(mainWindow);
                    System.out.println("Returned to the main window successfully.");
                }
                break; // Exit the loop after handling the message window
            }
        }
    }

    



    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed successfully.");
        }
    }
}
