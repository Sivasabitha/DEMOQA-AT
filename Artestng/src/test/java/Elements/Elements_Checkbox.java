//Elements --> check box
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

public class Elements_Checkbox {

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
    public void TestCheckboxNavigation() {
        driver.get("https://demoqa.com/");
        WebElement elementsCard = driver.findElement(By.xpath("//h5[text()='Elements']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementsCard);
        elementsCard.click();

        WebElement checkboxItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Check Box']")));
        checkboxItem.click();

        WebElement expandAllButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Expand all']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", expandAllButton);

        WebElement desktopCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='tree-node-desktop']//span[@class='rct-checkbox']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", desktopCheckbox);

        WebElement documentsCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='tree-node-documents']//span[@class='rct-checkbox']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", documentsCheckbox);

        WebElement downloadsCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='tree-node-downloads']//span[@class='rct-checkbox']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", downloadsCheckbox);

        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
        String resultText = result.getText();

        Assert.assertTrue(resultText.contains("desktop"), "Desktop checkbox not selected");
        Assert.assertTrue(resultText.contains("documents"), "Documents checkbox not selected");
        Assert.assertTrue(resultText.contains("downloads"), "Downloads checkbox not selected");
        System.out.println(result.getText());

        WebElement collapseAllButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Collapse all']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", collapseAllButton);

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
