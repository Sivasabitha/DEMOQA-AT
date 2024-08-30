package Widgets;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Progress_Bar {
    WebDriver driver;
    JavascriptExecutor jsExecutor;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        jsExecutor = (JavascriptExecutor) driver;
        driver.get("https://demoqa.com/");
    }

    @Test
    public void progressBarTest() throws InterruptedException {
        WebElement homeBanner = driver.findElement(By.xpath("//div[@class='home-banner']"));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", homeBanner);

        WebElement widgetsCard = driver.findElement(By.xpath("//h5[text()='Widgets']"));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", widgetsCard);
        jsExecutor.executeScript("arguments[0].click();", widgetsCard);

        WebElement progressBarOption = driver.findElement(By.xpath("//span[text()='Progress Bar']"));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", progressBarOption);
        jsExecutor.executeScript("arguments[0].click();", progressBarOption);

        WebElement startButton = driver.findElement(By.id("startStopButton"));
        jsExecutor.executeScript("arguments[0].click();", startButton);

        Thread.sleep(4000);

        jsExecutor.executeScript("arguments[0].click();", startButton);

        WebElement progressBar = driver.findElement(By.cssSelector(".progress-bar"));
        String progressValue = progressBar.getText().replace("%", "");
        int progressValueInt = Integer.parseInt(progressValue);
        Assert.assertTrue(progressValueInt < 100, "Progress bar should not reach 100%");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
