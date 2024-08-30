package Interactions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.netty.handler.timeout.TimeoutException;

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

public class Droppable {
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
        jsExecutor  = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        actions = new Actions(driver);
        driver.get("https://demoqa.com/");
    }

    @Test
    public void droppableTest() {
        WebElement interactionsCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[text()='Interactions']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", interactionsCard);
        interactionsCard.click();

        WebElement droppableOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Droppable']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", droppableOption);
        droppableOption.click();
        
        jsExecutor.executeScript("window.scrollBy(0, 300);");
        System.out.println("Scrolled 300 pixels down.");

        // Test Simple Tab
        testSimpleTab();

        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        testAcceptTab();
    }

    private void testSimpleTab() {
        WebElement simpleTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Simple']")));
        simpleTab.click();

        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement draggable = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("draggable")));
        WebElement droppable = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("droppable")));

        System.out.println("Draggable Location: " + draggable.getLocation());
        System.out.println("Droppable Location: " + droppable.getLocation());

        actions.dragAndDrop(draggable, droppable).perform();
        
        try {
            Thread.sleep(2000); // Adjust delay as needed for drag and drop action
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String droppableText = droppable.getText();
        System.out.println("Droppable Text: " + droppableText);
        Assert.assertTrue(droppableText.contains("Dropped!"));
        System.out.println("Drag and drop successful in Simple tab.");
    }

    public void testAcceptTab() {
        // Click on the 'Accept' tab
        try {
            WebElement acceptTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Accept']")));
            acceptTab.click();
            System.out.println("Clicked on the 'Accept' tab.");
        } catch (Exception e) {
            System.out.println("Failed to click on the 'Accept' tab: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Unable to click on the 'Accept' tab.");
        }

        // Wait for draggable and droppable elements to be present
        WebElement acceptableDraggable = null;
        WebElement notAcceptableDraggable = null;
        WebElement droppable = null;

        try {
            acceptableDraggable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("acceptable")));
            notAcceptableDraggable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notAcceptable")));
            droppable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("droppable")));
            
            // Debugging element states
            System.out.println("Droppable Element Location: " + droppable.getLocation());
            System.out.println("Droppable Text: " + droppable.getText());
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for elements to be visible: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Unable to locate one or more elements.");
        }

        // Perform drag and drop action for the acceptable element
        try {
            actions.dragAndDrop(acceptableDraggable, droppable).perform();
            wait.until(ExpectedConditions.textToBePresentInElement(droppable, "Dropped!"));
            String droppableText = droppable.getText();
            Assert.assertTrue(droppableText.contains("Dropped!"), "Failed to drop acceptable element in Accept tab");
            System.out.println("Drag and drop successful for acceptable element in Accept tab.");
        } catch (Exception e) {
            System.out.println("Drag and drop action failed for acceptable element: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Drag and drop action failed for acceptable element.");
        }

        // Perform drag and drop action for the not acceptable element
        try {
            ((JavascriptExecutor) driver).executeScript(
                "function createEvent(eventType) { " +
                "var event = document.createEvent('CustomEvent'); " +
                "event.initCustomEvent(eventType, true, true, null); " +
                "return event; " +
                "} " +
                "function dispatchEvent(element, event) { " +
                "element.dispatchEvent(event); " +
                "} " +
                "function simulateHTML5DragAndDrop(element, destination) { " +
                "var dragStartEvent = createEvent('dragstart'); " +
                "dispatchEvent(element, dragStartEvent); " +
                "var dropEvent = createEvent('drop'); " +
                "dispatchEvent(destination, dropEvent); " +
                "var dragEndEvent = createEvent('dragend'); " +
                "dispatchEvent(element, dragEndEvent); " +
                "} " +
                "simulateHTML5DragAndDrop(arguments[0], arguments[1]);",
                notAcceptableDraggable, droppable
            );

            // Verify that the droppable area does not contain "Dropped!"
            String droppableText = droppable.getText();
            Assert.assertFalse(droppableText.contains("Dropped!"), "Failed to prevent dropping non-acceptable element in Accept tab");
            System.out.println("Drag and drop correctly handled for non-acceptable element in Accept tab.");
        } catch (Exception e) {
            System.out.println("Drag and drop action failed for non-acceptable element: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Drag and drop action failed for non-acceptable element.");
        }
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
