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

public class Draggable {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    WebDriverWait wait;
    Actions actions;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        jsExecutor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        actions = new Actions(driver);
        driver.get("https://demoqa.com");
    }

    @Test
    public void testDragElement() throws Exception {
        // Navigate to the "Interactions" card
        WebElement interactionsCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[text()='Interactions']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", interactionsCard);
        interactionsCard.click();

        // Navigate to the "Draggable" option using full XPath
        WebElement draggableOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div/div/div/div[1]/div/div/div[5]/div/ul/li[5]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", draggableOption);
        draggableOption.click();

        // Scroll down to make sure elements are visible
        jsExecutor.executeScript("window.scrollBy(0, 300);");
        System.out.println("Scrolled 300 pixels down.");

        testDragAndDropInSimpleTab();
    }
    private void testDragAndDropInSimpleTab() {
        try {
            // Wait for and click on the "Simple" tab
            WebElement simpleTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("simpleTab")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", simpleTab);
            simpleTab.click();

            // Switch to iframe if the elements are inside an iframe
            WebElement iframe = driver.findElement(By.xpath("//*[@id=\"dragBox\"]"));
            driver.switchTo().frame(iframe);

            // Locate the "Drag me" element and the target "Drop here" element
            WebElement dragMeBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='dragBox']")));
            WebElement dropTarget = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dropTarget")));

            // Perform double-click on the "Drag me" box
            actions.doubleClick(dragMeBox).perform();
            System.out.println("Double-clicked on the drag box.");

            // Perform drag and drop using Actions class
            actions.clickAndHold(dragMeBox)
                   .moveToElement(dropTarget)
                   .release()
                   .perform();
            
            // Optionally, you can verify if the drop action was successful
            System.out.println("Drag and drop action performed successfully.");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Switch back to the default content
            driver.switchTo().defaultContent();
        }
    }










    @AfterClass
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
