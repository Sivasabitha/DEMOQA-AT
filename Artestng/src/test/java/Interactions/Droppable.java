package Interactions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
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
        jsExecutor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        actions = new Actions(driver);
        driver.get("https://demoqa.com/");
    }

    @Test
    public void droppableTest() {
        // Navigate to Interactions -> Droppable
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

        //  Accept Tab
        testAcceptTab();
        
        //PreventPropagation Tab
        testPreventPropagationTab();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
        //Revert Draggable tab
        testRevertDraggableTab();
        }

    private void testSimpleTab() {
        WebElement simpleTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Simple']")));
        simpleTab.click();

        WebElement draggable = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("draggable")));
        WebElement droppable = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("droppable")));

        System.out.println("Draggable Location: " + draggable.getLocation());
        System.out.println("Droppable Location: " + droppable.getLocation());

        actions.dragAndDrop(draggable, droppable).perform();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String droppableText = droppable.getText();
        System.out.println("Droppable Text: " + droppableText);
        Assert.assertTrue(droppableText.contains("Dropped!"));
        System.out.println("Drag and drop successful in Simple tab.");
    }

    private void testAcceptTab() {
        try {
            WebElement acceptTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("droppableExample-tab-accept")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", acceptTab);
            jsExecutor.executeScript("arguments[0].click();", acceptTab);
            System.out.println("Clicked on 'Accept' tab.");

            WebElement target = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Drop here']")));

            WebElement acceptable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("acceptable")));

            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", acceptable);
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", target);

            actions.clickAndHold(acceptable)
                   .moveToElement(target)
                   .release()
                   .build()
                   .perform();
            System.out.println("Drag and drop action performed with 'Acceptable' box.");

            String targetText = target.getText();
            Assert.assertTrue(targetText.contains("Dropped!"), "'Acceptable' box should trigger the drop.");
            System.out.println("Drop action validated successfully with 'Acceptable' box.");

            WebElement notAcceptable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notAcceptable")));

            actions.clickAndHold(notAcceptable)
                   .moveToElement(target)
                   .release()
                   .build()
                   .perform();
            System.out.println("Drag and drop action performed with 'Not Acceptable' box.");
            String finalTargetText = target.getText();
            Assert.assertTrue(finalTargetText.contains("Dropped!"));
            System.out.println("Validation passed: 'Not Acceptable' box ");

        } catch (Exception e) {
            System.err.println("Error occurred during drag and drop in 'Accept' tab: " + e.getMessage());
        }
    }
    
    private void testPreventPropagationTab() {
        try {
            WebElement preventPropagationTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("droppableExample-tab-preventPropogation")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", preventPropagationTab);
            jsExecutor.executeScript("arguments[0].click();", preventPropagationTab);
            System.out.println("Clicked on 'Prevent Propagation' tab.");

            WebElement dragBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dragBox")));

            WebElement outerDropZone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notGreedyDropBox")));

            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dragBox);
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", outerDropZone);

            actions.clickAndHold(dragBox)
                   .moveToElement(outerDropZone)
                   .release()
                   .build()
                   .perform();
            System.out.println("Dragged and dropped the box into the outer drop zone.");

            String outerDropText = outerDropZone.getText();
            Assert.assertTrue(outerDropText.contains("Dropped!"), "Outer drop zone should contain 'Dropped!'.");
            System.out.println("Outer drop zone validation passed.");

            WebElement innerDropZone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notGreedyInnerDropBox")));

            actions.clickAndHold(dragBox)
                   .moveToElement(innerDropZone)
                   .release()
                   .build()
                   .perform();
            System.out.println("Dragged and dropped the box into the inner drop zone.");

            String innerDropText = innerDropZone.getText();
            Assert.assertTrue(innerDropText.contains("Dropped!"), "Inner drop zone should contain 'Dropped!'.");
            System.out.println("Inner drop zone validation passed.");

        } catch (Exception e) {
            System.err.println("Error occurred during drag and drop in 'Prevent Propagation' tab: " + e.getMessage());
        }
    }
    
    private void testRevertDraggableTab(){
                WebElement revertTab1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("droppableExample-tab-revertable")));
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", revertTab1);
                jsExecutor.executeScript("arguments[0].click();", revertTab1);
                System.out.println("Clicked on 'Revert Draggable' tab.");

                WebElement target1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Drop here']")));

                WebElement revertable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("revertable")));

                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", revertable);
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", target1);

                actions.clickAndHold(revertable)
                       .moveToElement(target1)
                       .release()
                       .build()
                       .perform();
                System.out.println("Drag and drop action performed with 'Revertable' box.");

                String targetText = target1.getText();
                Assert.assertTrue(targetText.contains("Dropped!"), "'Revertable' box should trigger the drop.");
                System.out.println("Drop action validated successfully with 'Revertable' box.");

                try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                WebElement revertableAfterRevert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("revertable")));
                Point revertablePositionAfterRevert = revertableAfterRevert.getLocation();
                System.out.println("Revertable box position after revert: " + revertablePositionAfterRevert);

                // Perform drag and drop for 'notRevertable' box
                WebElement notRevertable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notRevertable")));
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", notRevertable);

                actions.clickAndHold(notRevertable)
                       .moveToElement(target1)
                       .release()
                       .build()
                       .perform();
                System.out.println("Drag and drop action performed with 'Not Revertable' box.");

                String finalTargetText = target1.getText();
                Assert.assertTrue(finalTargetText.contains("Dropped!"), "'Not Revertable' box should trigger the drop.");
                System.out.println("Drop action validated successfully with 'Not Revertable' box.");

                WebElement notRevertableAfterDrop = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notRevertable")));
                Point notRevertablePositionAfterDrop = notRevertableAfterDrop.getLocation();
                System.out.println("Not Revertable box position after drop: " + notRevertablePositionAfterDrop);

            } 

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
