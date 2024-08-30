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

public class Elements_Web_tables {

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
    public void TestWebTableActions() {
        driver.get("https://demoqa.com/");
        
        WebElement elementsCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='card mt-4 top-card'][.//h5[text()='Elements']]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementsCard);
        elementsCard.click();

        WebElement webTablesItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Web Tables']")));
        webTablesItem.click();

        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewRecordButton")));
        addButton.click();

        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
        firstNameInput.sendKeys("Sivasabitha");

        WebElement lastNameInput = driver.findElement(By.id("lastName"));
        lastNameInput.sendKeys("K");

        WebElement emailInput = driver.findElement(By.id("userEmail"));
        emailInput.sendKeys("sivasabitha@gmail.com");

        WebElement ageInput = driver.findElement(By.id("age"));
        ageInput.sendKeys("20");

        WebElement salaryInput = driver.findElement(By.id("salary"));
        salaryInput.sendKeys("20000");

        WebElement departmentInput = driver.findElement(By.id("department"));
        departmentInput.sendKeys("QA INTERN");

        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBox")));
        searchBox.sendKeys("Sivasabitha");

        WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rt-tr -odd']")));
        String searchResultText = searchResult.getText();

        String[] searchResultData = searchResultText.split("\\s+");

        Assert.assertEquals(searchResultData[0], "Sivasabitha", "First name doesn't match.");
        Assert.assertEquals(searchResultData[1], "K", "Last name doesn't match.");
        Assert.assertEquals(searchResultData[2], "20", "Age doesn't match.");
        Assert.assertEquals(searchResultData[3], "sivasabitha@gmail.com", "Email doesn't match.");
        Assert.assertEquals(searchResultData[4], "20000", "Salary doesn't match.");
        Assert.assertTrue(searchResultText.contains("QA INTERN"), "Department doesn't match.");
    
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
