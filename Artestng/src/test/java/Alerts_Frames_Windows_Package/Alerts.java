package Alerts_Frames_Windows_Package;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Alerts {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("maximized"); 
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increased timeout
    }

    @Test
    public void testAlerts() {
        driver.get("https://demoqa.com");

        WebElement alertsFramesWindowsCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='card mt-4 top-card'][3]")));
        scrollToElement(alertsFramesWindowsCard);
        alertsFramesWindowsCard.click();

        WebElement alertsOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Alerts']")));
        scrollToElement(alertsOption);
        alertsOption.click();

        WebElement alertButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("alertButton")));
        scrollToElement(alertButton);
        alertButton.click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("Alert text: " + alert.getText());
        alert.accept(); 
        System.out.println("Alert handled successfully.");

        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("confirmButton")));
        scrollToElement(confirmButton);
        confirmButton.click();
        Alert confirmAlert = wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("Confirm box text: " + confirmAlert.getText());
        confirmAlert.dismiss(); 
        System.out.println("Confirm box handled successfully.");

        WebElement promptButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("promtButton")));
        scrollToElement(promptButton);
        promptButton.click();
        Alert promptAlert = wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("Prompt box text: " + promptAlert.getText());
        String inputText = "Sivasabitha";
        promptAlert.sendKeys(inputText); 
        promptAlert.accept(); 

        try {
            WebElement resultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//some-correct-locator"))); // Adjust this locator
            String resultText = resultElement.getText();
            if (resultText.contains(inputText)) {
                System.out.println("Prompt box handled successfully. Entered text is present in the result.");
            } else {
                System.out.println("Prompt box handled, but entered text is not present in the result.");
            }
        } catch (Exception e) {
            System.out.println("Exception encountered while verifying the result: " + e.getMessage());
        }
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
    private void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
