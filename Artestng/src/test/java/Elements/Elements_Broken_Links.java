package Elements;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class Elements_Broken_Links {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void testBrokenLinksAndImages() {
        driver.get("https://demoqa.com/broken");

        WebElement validLink = driver.findElement(By.linkText("Click Here for Valid Link"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", validLink);
        wait.until(ExpectedConditions.elementToBeClickable(validLink)).click();

        String validLinkTitle = driver.getTitle();
        Assert.assertTrue(validLinkTitle.contains("DEMOQA"));

        System.out.println("The Valid link is passed");
        
        driver.navigate().back();

        WebElement brokenLink = driver.findElement(By.linkText("Click Here for Broken Link"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", brokenLink);
        wait.until(ExpectedConditions.elementToBeClickable(brokenLink)).click();

        String brokenLinkTitle = driver.getTitle();
        Assert.assertTrue(brokenLinkTitle.contains("500") || brokenLinkTitle.contains("Error"));
        
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
