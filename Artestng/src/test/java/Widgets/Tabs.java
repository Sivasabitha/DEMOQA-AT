package Widgets;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class Tabs {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        jsExecutor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://demoqa.com/");
    }

    @Test
    public void tabsTest() {
        try {
           
            WebElement widgetsCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Widgets']")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", widgetsCard);
            widgetsCard.click();

          
            WebElement tabsOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Tabs']")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", tabsOption);
            tabsOption.click();

            clickTab("//a[@id='demo-tab-origin']", "Origin");

            clickTab("//a[@id='demo-tab-use']", "Use");

            clickTab("//a[@id='demo-tab-more']", "More");

            clickTab("//a[@id='demo-tab-what']", "What");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickTab(String xpath, String tabName) {
        try {
            WebElement tabButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", tabButton);
            jsExecutor.executeScript("arguments[0].click();", tabButton);
            System.out.println("Clicked on '" + tabName + "' tab using JavaScript");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("demo-tab-" + tabName.toLowerCase())));
        } catch (Exception e) {
            System.out.println("Failed to click on '" + tabName + "' tab");
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
