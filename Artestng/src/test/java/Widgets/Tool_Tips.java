package Widgets;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class Tool_Tips {
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
    public void toolTipsTest() throws InterruptedException {
        
        WebElement widgetsCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[text()='Widgets']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", widgetsCard);
        jsExecutor.executeScript("arguments[0].click();", widgetsCard);
       
        WebElement toolTipsOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Tool Tips']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", toolTipsOption);
        jsExecutor.executeScript("arguments[0].click();", toolTipsOption);
     
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='toolTipButton']")));
        actions.moveToElement(button).perform();
        Thread.sleep(2000); 
      
        WebElement textField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='toolTipTextField']")));
        actions.moveToElement(textField).perform();
        Thread.sleep(2000); 
       
        WebElement contraryWord = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Contrary']")));
        actions.moveToElement(contraryWord).perform();
        Thread.sleep(2000);
        
        WebElement versionText = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='1.10.32']")));
        actions.moveToElement(versionText).perform();
        Thread.sleep(2000); 
    }

   @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
 


