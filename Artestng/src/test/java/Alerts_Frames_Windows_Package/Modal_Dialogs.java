package Alerts_Frames_Windows_Package;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Modal_Dialogs {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor jsExecutor;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        jsExecutor = (JavascriptExecutor) driver;
    }

    @Test
    public void testSmallModal() {
        driver.get("https://demoqa.com");

        WebElement alertsFramesWindowsCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='card mt-4 top-card'][3]")));
        alertsFramesWindowsCard.click();

        WebElement modalDialogsOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Modal Dialogs']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", modalDialogsOption);
        modalDialogsOption.click();

        WebElement smallModalButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("showSmallModal")));
        smallModalButton.click();
        
        WebElement smallModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-dialog")));
        Assert.assertTrue(smallModal.isDisplayed(), "Small Modal is not displayed");
        System.out.println("Small Modal is displayed.");

        WebElement closeSmallModalButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("closeSmallModal")));
        closeSmallModalButton.click();
        
        wait.until(ExpectedConditions.invisibilityOf(smallModal));
        System.out.println("Small Modal is closed and no longer visible.");

        WebElement largeModalButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("showLargeModal")));
        largeModalButton.click();
        
        WebElement largeModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-dialog")));
        Assert.assertTrue(largeModal.isDisplayed(), "Large Modal is not displayed");
        System.out.println("Large Modal is displayed.");

        WebElement closeLargeModalButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("closeLargeModal")));
        closeLargeModalButton.click();
        
        wait.until(ExpectedConditions.invisibilityOf(largeModal));
        System.out.println("Large Modal is closed and no longer visible.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
