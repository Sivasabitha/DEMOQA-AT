package Widgets;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Slider {

    WebDriver driver;
    JavascriptExecutor jsExecutor;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        jsExecutor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://demoqa.com/");
    }

    @Test
    public void sliderTest() {
        try {
            
            WebElement widgetsCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Widgets']")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", widgetsCard);
            widgetsCard.click();
            System.out.println("Navigated to Widgets.");

            WebElement sliderOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Slider']")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", sliderOption);
            sliderOption.click();
            System.out.println("Navigated to Slider.");

            WebElement slider = driver.findElement(By.cssSelector(".range-slider"));
            Actions actions = new Actions(driver);

            int xOffset = 60; 
            actions.clickAndHold(slider)
                   .moveByOffset(xOffset, 0)
                   .release()
                   .perform();

            WebElement sliderValue = driver.findElement(By.id("sliderValue"));
            System.out.println("Slider value after moving: " + sliderValue.getAttribute("value"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit(); 
        }
    }
}
