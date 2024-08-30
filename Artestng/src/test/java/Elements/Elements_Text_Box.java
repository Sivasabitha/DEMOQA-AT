package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class Elements_Text_Box {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void TestDemoQA() {
        driver.get("https://demoqa.com/");

        try {
            WebElement fixedBan = driver.findElement(By.id("fixedban"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].remove();", fixedBan);
        } catch (Exception e) {
            System.out.println("No fixed banner found or couldn't remove it.");
        }

        WebElement elementsCard = driver.findElement(By.xpath("//h5[text()='Elements']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementsCard);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("fixedban")));

        WebElement clickableElementsCard = wait.until(ExpectedConditions.elementToBeClickable(elementsCard));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableElementsCard);

        WebElement textBoxItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Text Box']")));
        textBoxItem.click();

        String fullName = "Sivasabitha";
        String email = "sivasabitha@gmail.com";
        String currentAddress = "Periyar nagar 6th street, madurai";
        String permanentAddress = "Periyar nagar 6th street, madurai";

        WebElement fullNameInput = driver.findElement(By.id("userName"));
        fullNameInput.sendKeys(fullName);

        WebElement emailInput = driver.findElement(By.id("userEmail"));
        emailInput.sendKeys(email);

        WebElement currentAddressInput = driver.findElement(By.id("currentAddress"));
        currentAddressInput.sendKeys(currentAddress);

        WebElement permanentAddressInput = driver.findElement(By.id("permanentAddress"));
        permanentAddressInput.sendKeys(permanentAddress);

        WebElement submitButton = driver.findElement(By.id("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        WebElement clickableSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableSubmitButton);

        WebElement nameOutput = driver.findElement(By.id("name"));
        WebElement emailOutput = driver.findElement(By.id("email"));
        WebElement currentAddressOutput = driver.findElement(By.xpath("//p[@id='currentAddress']"));
        WebElement permanentAddressOutput = driver.findElement(By.xpath("//p[@id='permanentAddress']"));

        Assert.assertTrue(nameOutput.getText().contains(fullName), "Full Name does not match!");
        Assert.assertTrue(emailOutput.getText().contains(email), "Email does not match!");
        Assert.assertTrue(currentAddressOutput.getText().contains(currentAddress), "Current Address does not match!");
        Assert.assertTrue(permanentAddressOutput.getText().contains(permanentAddress), "Permanent Address does not match!");

        System.out.println(driver.getTitle());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
