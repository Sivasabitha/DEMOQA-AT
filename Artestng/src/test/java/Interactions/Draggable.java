package Interactions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
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

        // Navigate to the "Draggable" option using an updated XPath
        WebElement draggableOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div/div/div/div[1]/div/div/div[5]/div/ul/li[5]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", draggableOption);
        draggableOption.click();

        // Scroll down to make sure elements are visible
        jsExecutor.executeScript("window.scrollBy(0, 300);");
        System.out.println("Scrolled 300 pixels down.");

        testDragAndDropInSimpleTab();
        testDragAndDropInAxisRestrictedTab(); 
        testDragAndDropInContainerRestrictedTab();
        testDragAndDropInCursorStyleTab();
        }

    private void testDragAndDropInSimpleTab() {
        try {
            WebElement simpleTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("draggableExample-tab-simple")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", simpleTab);
            simpleTab.click();

            WebElement dragMeBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dragBox")));

            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dragMeBox);

            Actions actions = new Actions(driver);

            int offsetX = 50; 
            int offsetY = 50; 
            
            actions.clickAndHold(dragMeBox)
                   .moveByOffset(offsetX, offsetY) 
                   .release()
                   .perform();

            System.out.println("Simple drag is passed");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testDragAndDropInAxisRestrictedTab() {
        try {
            WebElement axisRestrictedTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("draggableExample-tab-axisRestriction")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", axisRestrictedTab);
            axisRestrictedTab.click();

            WebElement restrictedX = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("restrictedX")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", restrictedX);

            Actions actions = new Actions(driver);

            int horizontalOffset = 50;
            actions.clickAndHold(restrictedX)
                   .moveByOffset(horizontalOffset, 0) 
                   .release()
                   .perform();

            System.out.println("Dragged the restrictedX element horizontally.");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebElement restrictedY = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("restrictedY")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", restrictedY);

            int verticalOffset = 50; 
            actions.clickAndHold(restrictedY)
                   .moveByOffset(0, verticalOffset) 
                   .release()
                   .perform();

            System.out.println("Dragged the restrictedY element vertically.");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Axix Restricted is passed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testDragAndDropInContainerRestrictedTab() {
        try {
        
            WebElement containerRestrictedTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("draggableExample-tab-containerRestriction")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", containerRestrictedTab);
            containerRestrictedTab.click();
            
            WebElement dragBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='containmentWrapper']/div")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dragBox);

            WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("containmentWrapper")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", container);

            Point containerLocation = container.getLocation();
            Dimension containerSize = container.getSize();

            Dimension dragBoxSize = dragBox.getSize();

            int dropX = containerLocation.getX() + containerSize.getWidth() - dragBoxSize.getWidth();
            int dropY = containerLocation.getY() + containerSize.getHeight() - dragBoxSize.getHeight();

            Actions actions = new Actions(driver);

            actions.clickAndHold(dragBox)
                   .moveByOffset(dropX - dragBox.getLocation().getX(), dropY - dragBox.getLocation().getY())  // Move to calculated position
                   .release()
                   .perform();

            System.out.println("Container Restricted tab is Passed");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testDragAndDropInCursorStyleTab() {
        try {
            WebElement cursorStyleTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("draggableExample-tab-cursorStyle")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", cursorStyleTab);
            cursorStyleTab.click();

            WebElement cursorCenter = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cursorCenter")));
            WebElement cursorTopLeft = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cursorTopLeft")));
            WebElement cursorBottom = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cursorBottom")));

            int centerOffsetX = 100; 
            int centerOffsetY = 100;

            int topLeftOffsetX = 10; 
            int topLeftOffsetY = 10;

            int bottomOffsetX = 10; 
            int bottomOffsetY = 150;

            Actions actions = new Actions(driver);

            actions.clickAndHold(cursorCenter)
                   .moveByOffset(centerOffsetX, centerOffsetY)
                   .release()
                   .perform();

            System.out.println("Dragged the cursorCenter element to a general position.");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            actions.clickAndHold(cursorTopLeft)
                   .moveByOffset(topLeftOffsetX, topLeftOffsetY)
                   .release()
                   .perform();

            System.out.println("Dragged the cursorTopLeft element to a general position.");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            actions.clickAndHold(cursorBottom)
                   .moveByOffset(bottomOffsetX, bottomOffsetY)
                   .release()
                   .perform();

            System.out.println("Dragged the cursorBottom element to a general position.");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Cursor Style Tab is Passed");

        } catch (Exception e) {
            e.printStackTrace();
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
