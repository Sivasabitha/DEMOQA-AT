//Elements--> Links
package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class Elements_Links {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));  // Increased timeout
    }

    @Test
    public void TestLinksActions() {
        driver.get("https://demoqa.com/");
        
        WebElement elementsCard = driver.findElement(By.xpath("//h5[text()='Elements']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementsCard);
        elementsCard.click();

        WebElement linksItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Links']")));
        ((JavascriptExecutor) driver).executeScript("document.getElementById('fixedban').style.display='none'");  // Hide any potential overlapping ads
        linksItem.click();
        
        handleLinkClick("Created", "201");
        handleLinkClick("No Content", "204");
        handleLinkClick("Moved", "301");
        handleLinkClick("Bad Request", "400");
        handleLinkClick("Unauthorized", "401");
        handleLinkClick("Forbidden", "403");
        handleLinkClick("Not Found", "404");
    }

    private void handleLinkClick(String linkText, String expectedStatus) {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("linkResponse")));
        WebElement responseMessage = driver.findElement(By.id("linkResponse"));
        String actualMessage = responseMessage.getText();
        System.out.println(linkText + " Link Response: " + actualMessage);

        if (actualMessage.contains(expectedStatus)) {
            System.out.println(linkText + " link test passed.");
        } else {
            System.out.println(linkText + " link test failed.");
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}