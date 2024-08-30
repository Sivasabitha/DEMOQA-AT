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

public class Sortable {
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
    public void sortableTest() {
        WebElement interactionsCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[text()='Interactions']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", interactionsCard);
        interactionsCard.click();

        WebElement sortableOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Sortable']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", sortableOption);
        sortableOption.click();

        jsExecutor.executeScript("window.scrollBy(0, 500);");
        System.out.println("Scrolled 500 pixels down.");
    }

    @Test
 
    public void testSortableList() {
        driver.findElement(By.xpath("//a[text()='List']")).click();
        
        WebElement one = driver.findElement(By.xpath("//div[@id='demo-tabpane-list']//div[text()='One']"));
        WebElement four = driver.findElement(By.xpath("//div[@id='demo-tabpane-list']//div[text()='Four']"));
        
        actions.clickAndHold(one)
               .moveToElement(four)
               .release()
               .perform();
        
        WebElement newFourth = driver.findElement(By.xpath("//div[@id='demo-tabpane-list']//div[text()='One']"));
        Assert.assertEquals(newFourth.getText(), "One", "Element 'One' is not in the fourth position");
    }

    @Test
    public void testSortableGrid() {
        driver.findElement(By.xpath("//a[text()='Grid']")).click();
        
        WebElement one = driver.findElement(By.xpath("//div[@id='demo-tabpane-grid']//div[text()='One']"));
        WebElement four = driver.findElement(By.xpath("//div[@id='demo-tabpane-grid']//div[text()='Four']"));
        
        actions.clickAndHold(one)
               .moveToElement(four)
               .release()
               .perform();
        
        WebElement newFourth = driver.findElement(By.xpath("//div[@id='demo-tabpane-grid']//div[text()='One']"));
        Assert.assertEquals(newFourth.getText(), "One", "Element 'One' is not in the fourth position");
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
