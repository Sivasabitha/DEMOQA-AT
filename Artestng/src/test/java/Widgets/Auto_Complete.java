package Widgets;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Auto_Complete {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increased timeout to handle slow loading
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().window().maximize(); // Maximize window to avoid visibility issues
        driver.get("https://demoqa.com/");
    }

    @Test
    public void testAutoComplete() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='home-banner']")));
        System.out.println("Home page loaded.");

        WebElement widgetsCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[text()='Widgets']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", widgetsCard);
        jsExecutor.executeScript("arguments[0].click();", widgetsCard);
        System.out.println("Clicked on Widgets card.");

        WebElement autoCompleteOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Auto Complete']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", autoCompleteOption);
        jsExecutor.executeScript("arguments[0].click();", autoCompleteOption); // Click via JS
        System.out.println("Clicked on Auto Complete option.");

        WebElement multipleColorInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("autoCompleteMultipleInput")));
        
        String[] colors = {"Red", "Green", "Blue"};
        for (String color : colors) {
            multipleColorInput.clear();
            multipleColorInput.sendKeys(color);
            multipleColorInput.sendKeys(Keys.RETURN); 
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '" + color + "')]")));
            System.out.println(color + " color tag is displayed.");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        WebElement tagRed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Red')]")));
        WebElement tagGreen = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Green')]")));
        WebElement tagBlue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Blue')]")));
        
        Assert.assertTrue(tagRed.isDisplayed(), "Red color tag is not displayed.");
        Assert.assertTrue(tagGreen.isDisplayed(), "Green color tag is not displayed.");
        Assert.assertTrue(tagBlue.isDisplayed(), "Blue color tag is not displayed.");
        
        WebElement singleColorInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("autoCompleteSingleInput")));
        singleColorInput.sendKeys("Yellow");
        singleColorInput.sendKeys(Keys.RETURN);
        
        WebElement singleColorTag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Yellow')]")));
        
        Assert.assertTrue(singleColorTag.isDisplayed(), "Yellow color tag is not displayed.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
