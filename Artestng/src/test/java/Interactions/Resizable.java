package Interactions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Resizable {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    WebDriverWait wait;
    Actions actions;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        jsExecutor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        driver.get("https://demoqa.com/");
    }

    @Test
    public void resizableTest() {
        WebElement interactionsCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[text()='Interactions']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", interactionsCard);
        interactionsCard.click();

        WebElement resizableOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Resizable']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", resizableOption);
        resizableOption.click();

        jsExecutor.executeScript("window.scrollBy(0, 500);");
        System.out.println("Scrolled 500 pixels down.");

        WebElement resizableHandle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='resizableBoxWithRestriction']/span")));
        
        actions.clickAndHold(resizableHandle)
                .moveByOffset(100, 50) 
                .release()
                .perform();

        WebElement resizableBox = driver.findElement(By.id("resizableBoxWithRestriction"));
        String boxSizeAfterResize = resizableBox.getAttribute("style");
        System.out.println("Box size after resize: " + boxSizeAfterResize);

        Assert.assertTrue(boxSizeAfterResize.contains("width") && boxSizeAfterResize.contains("height"), "The box did not resize correctly");

        System.out.println("Box resized successfully.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
