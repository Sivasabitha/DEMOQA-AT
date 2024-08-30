package Widgets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Menus {
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
    public void menuTest() throws InterruptedException {
        WebElement widgetsCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[text()='Widgets']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", widgetsCard);
        jsExecutor.executeScript("arguments[0].click();", widgetsCard);

        WebElement menuOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Menu']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", menuOption);
        jsExecutor.executeScript("arguments[0].click();", menuOption);
       
        WebElement mainItem1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Main Item 1']")));
        actions.moveToElement(mainItem1).perform();
        Thread.sleep(1000); 

        jsExecutor.executeScript("window.scrollBy(0, 500);"); 

        WebElement mainItem2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Main Item 2']")));
        actions.moveToElement(mainItem2).perform();
        Thread.sleep(1000);

        WebElement subSubList = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='nav']/li[2]/ul/li[3]")));

        wait.until(ExpectedConditions.elementToBeClickable(subSubList));

        try {
            actions.moveToElement(subSubList).click().perform();
            System.out.println("Click action passed using Actions");
        } catch (Exception e) {
            System.out.println("Click action failed, trying JavaScript click.");
            jsExecutor.executeScript("arguments[0].click();", subSubList);
        }

        Thread.sleep(3000); 

        WebElement mainItem3 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Main Item 3']")));
        actions.moveToElement(mainItem3).click().perform();
        Thread.sleep(1000); 
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
