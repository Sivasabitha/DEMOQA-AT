package Interactions;

import java.time.Duration;

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

public class Selectable {
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
    public void selectableTest() throws Exception {
        WebElement interactionsCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[text()='Interactions']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", interactionsCard);
        interactionsCard.click();

        WebElement selectableOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Selectable']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", selectableOption);
        selectableOption.click();

        WebElement listItem1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='verticalListContainer']/li[1]")));
        WebElement listItem2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='verticalListContainer']/li[2]")));
        WebElement listItem3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='verticalListContainer']/li[3]")));
        WebElement listItem4 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='verticalListContainer']/li[4]")));

        clickElementWithLogging(listItem1);
        clickElementWithLogging(listItem2);
        clickElementWithLogging(listItem3);
        clickElementWithLogging(listItem4);

        WebElement gridTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("demo-tab-grid")));
        gridTab.click();

        WebElement oneItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='One']")));
        WebElement twoItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='Two']")));
        WebElement threeItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='Three']")));

        clickElementWithLogging(oneItem);
        clickElementWithLogging(twoItem);
        clickElementWithLogging(threeItem);

        System.out.println("Selectable Test Completed Successfully");
    }

    private void clickElementWithLogging(WebElement element) throws InterruptedException {
        try {
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            System.out.println("Clicked on element: " + element.getText());
            Thread.sleep(1000);
        } 
        catch (Exception e) {
     
            System.out.println("Failed to click on element: " + element.getText());
            jsExecutor.executeScript("arguments[0].click();", element);
            System.out.println("Clicked on element using JavaScript: " + element.getText());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
