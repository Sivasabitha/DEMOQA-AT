package Alerts_Frames_Windows_Package;

import java.time.Duration;

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

public class Frames {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor jsExecutor;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Initialize JavascriptExecutor
        jsExecutor = (JavascriptExecutor) driver;
    }

    @Test
    public void testFrames() {
        driver.get("https://demoqa.com");

        WebElement alertsFramesWindowsCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='card mt-4 top-card'][3]")));
        alertsFramesWindowsCard.click();

        WebElement framesOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Frames']")));
        framesOption.click();

        scrollPage();
        driver.switchTo().frame("frame1");

        WebElement frameContent1 = driver.findElement(By.tagName("body"));
        System.out.println("Frame 1 content: " + frameContent1.getText());
        driver.switchTo().defaultContent();
       
        driver.switchTo().frame("frame2");
        scrollFrame();
        
        WebElement frameContent2 = driver.findElement(By.tagName("body"));
        System.out.println("Frame 2 content: " + frameContent2.getText());
        driver.switchTo().defaultContent();
    }

    private void scrollPage() {
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);"); 
        jsExecutor.executeScript("window.scrollTo(document.body.scrollWidth, 0);"); 
        jsExecutor.executeScript("window.scrollTo(0, 0);");
    }

    private void scrollFrame() {
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        jsExecutor.executeScript("window.scrollTo(document.body.scrollWidth, 0);"); 
        jsExecutor.executeScript("window.scrollTo(0,0);");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
