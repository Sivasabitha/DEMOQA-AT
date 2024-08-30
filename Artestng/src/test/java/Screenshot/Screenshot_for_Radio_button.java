package Screenshot;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

public class Screenshot_for_Radio_button {

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
        try {
            driver.get("https://demoqa.com/");
            WebElement elementsCard = driver.findElement(By.xpath("//h5[text()='Elements']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementsCard);
            elementsCard.click();

            WebElement radioButtonItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Radio Button']")));
            radioButtonItem.click();

            testRadioButton("//label[@for='yesRadio']", "You have selected Yes", "YesRadioButtonDisabledScreenshot");
            testRadioButton("//label[@for='impressiveRadio']", "You have selected Impressive", "ImpressiveRadioButtonDisabledScreenshot");
            testRadioButton("//label[@for='noRadio']", "You have selected No", "NoRadioButtonDisabledScreenshot");

        } catch (AssertionError e) {
            takeScreenshot("Test_Failure_Screenshot_Radiobtn.png");
            throw e; // Re-throw the caught exception
        }
    }

    private void testRadioButton(String xpath, String expectedText, String screenshotName) {
        WebElement radioButton = driver.findElement(By.xpath(xpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radioButton);

        if (radioButton.isEnabled()) {
            wait.until(ExpectedConditions.elementToBeClickable(radioButton)).click();
            WebElement radioButtonText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='mt-3']")));
            System.out.println("Selected option: " + radioButtonText.getText());
            Assert.assertEquals(radioButtonText.getText(), expectedText, "Radio button test failed.");
        } else {
            System.out.println("The radio button is disabled and cannot be clicked.");
            takeScreenshot(screenshotName + ".png");
            Assert.fail("Radio button is disabled.");
        }
    }

    private void takeScreenshot(String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destinationFile = new File("C:\\Users\\akash\\OneDrive\\Documents\\SIVASABITHA\\New folder\\" + fileName);

        File parentDir = destinationFile.getParentFile();
        if (!parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("Directory created: " + parentDir.getAbsolutePath());
            } else {
                System.err.println("Failed to create directory: " + parentDir.getAbsolutePath());
            }
        }

        try {
            FileUtils.copyFile(screenshot, destinationFile);
            System.out.println("Screenshot taken and saved to " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save screenshot.");
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
