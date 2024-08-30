package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class Elements_Buttons {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait for element visibility
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        actions = new Actions(driver);
    }

    @Test
    public void TestButtonActions() {
        try {
            driver.get("https://demoqa.com/");

            WebElement elementsCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Elements']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementsCard);
            elementsCard.click();

            WebElement buttonsItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Buttons']")));
            buttonsItem.click();

            WebElement doubleClickButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("doubleClickBtn")));
            actions.doubleClick(doubleClickButton).perform();
            WebElement doubleClickMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("doubleClickMessage")));
            String doubleClickExpected = "You have done a double click";
            String doubleClickActual = doubleClickMessage.getText();
            Assert.assertEquals(doubleClickActual, doubleClickExpected, "Double click message does not match!");

            WebElement rightClickButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("rightClickBtn")));
            actions.contextClick(rightClickButton).perform();
            WebElement rightClickMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rightClickMessage")));
            String rightClickExpected = "You have done a right click";
            String rightClickActual = rightClickMessage.getText();
            Assert.assertEquals(rightClickActual, rightClickExpected, "Right click message does not match!");

            WebElement dynamicClickButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Click Me']")));
            dynamicClickButton.click();
            WebElement dynamicClickMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dynamicClickMessage")));
            String dynamicClickExpected = "You have done a dynamic click";
            String dynamicClickActual = dynamicClickMessage.getText();
            Assert.assertEquals(dynamicClickActual, dynamicClickExpected, "Dynamic click message does not match!");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
