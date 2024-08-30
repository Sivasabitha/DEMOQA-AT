package Elements;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Elements_Dynamic_properties {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--enable-logging");
        options.addArguments("--v=1"); // Verbose logging

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.get("https://demoqa.com");
    }

    @Test
    public void testDynamicProperties() {
        try {
            try {
                WebElement banner = driver.findElement(By.id("fixedban"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = 'none';", banner);
            } catch (Exception e) {
            }

            WebElement elementsSection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Elements']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementsSection);
            elementsSection.click();

            WebElement dynamicPropertiesSection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Dynamic Properties']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dynamicPropertiesSection);
            dynamicPropertiesSection.click();

            WebElement colorChangeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("colorChange")));

            String initialColor = colorChangeButton.getCssValue("color");
            System.out.println("Initial Color: " + initialColor);

            Thread.sleep(6000); 

            String newColor = colorChangeButton.getCssValue("color");
            System.out.println("New Color: " + newColor);
            Assert.assertNotEquals(initialColor, newColor, "Button color should change after 5 seconds.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
