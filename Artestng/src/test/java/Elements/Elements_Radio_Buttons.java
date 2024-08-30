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

public class Elements_Radio_Buttons {

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
    public void TestRadioButtonNavigation() {
        driver.get("https://demoqa.com/");
        WebElement elementsCard = driver.findElement(By.xpath("//h5[text()='Elements']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementsCard);
        elementsCard.click();

        WebElement radioButtonItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Radio Button']")));
        radioButtonItem.click();

        WebElement yesRadio = driver.findElement(By.xpath("//label[@for='yesRadio']"));
        if (yesRadio.isEnabled()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", yesRadio);
            wait.until(ExpectedConditions.elementToBeClickable(yesRadio)).click();
            WebElement yesRadioText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='mt-3']")));
            System.out.println("Selected option: " + yesRadioText.getText());
            Assert.assertEquals(yesRadioText.getText(), "You have selected Yes", "'Yes' radio button test failed.");
        } else {
            System.out.println("The 'Yes' radio button is disabled and cannot be clicked.");
            Assert.fail("'Yes' radio button is disabled.");
        }

        WebElement impressiveRadio = driver.findElement(By.xpath("//label[@for='impressiveRadio']"));
        if (impressiveRadio.isEnabled()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", impressiveRadio);
            wait.until(ExpectedConditions.elementToBeClickable(impressiveRadio)).click();
            WebElement impressiveRadioText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='mt-3']")));
            System.out.println("Selected option: " + impressiveRadioText.getText());
            Assert.assertEquals(impressiveRadioText.getText(), "You have selected Impressive", "'Impressive' radio button test failed.");
        } else {
            System.out.println("The 'Impressive' radio button is disabled and cannot be clicked.");
            Assert.fail("'Impressive' radio button is disabled.");
        }

        WebElement noRadio = driver.findElement(By.xpath("//label[@for='noRadio']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", noRadio);
        if (noRadio.isEnabled()) {
            wait.until(ExpectedConditions.elementToBeClickable(noRadio)).click();
            WebElement noRadioText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='mt-3']")));
            System.out.println("Selected option: " + noRadioText.getText());
            Assert.assertEquals(noRadioText.getText(), "You have selected No", "'No' radio button test failed.");
        } else {
            System.out.println("The 'No' radio button is disabled and cannot be clicked.");
        }

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
