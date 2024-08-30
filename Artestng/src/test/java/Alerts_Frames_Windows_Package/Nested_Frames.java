package Alerts_Frames_Windows_Package;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Nested_Frames {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor jsExecutor;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        jsExecutor = (JavascriptExecutor) driver;
    }

    @Test
    public void testFrames() {
        driver.get("https://demoqa.com");

        WebElement alertsFramesWindowsCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='card mt-4 top-card'][3]")));
        alertsFramesWindowsCard.click();

        WebElement nestedFramesOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Nested Frames']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", nestedFramesOption);
        nestedFramesOption.click();
        
        System.out.println("Clicked on 'Nested Frames'.");
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
