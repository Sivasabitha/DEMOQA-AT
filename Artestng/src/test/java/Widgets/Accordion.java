package Widgets;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
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

public class Accordion {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        jsExecutor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increased timeout to handle slow loading
        driver.manage().window().maximize(); // Maximize window to avoid visibility issues
        driver.get("https://demoqa.com/");
    }

    @Test
    public void testAccordionOptions() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='home-banner']")));
            System.out.println("Home page loaded.");

            WebElement widgetsCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[text()='Widgets']")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", widgetsCard);
            jsExecutor.executeScript("arguments[0].click();", widgetsCard); // Click via JS
            System.out.println("Clicked on Widgets card.");

            WebElement accordionOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Accordian']")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", accordionOption);
            jsExecutor.executeScript("arguments[0].click();", accordionOption); // Click via JS
            System.out.println("Clicked on Accordion option.");

            List<String> optionsToClick = Arrays.asList(
                "What is Lorem Ipsum?",
                "Where does it come from?",
                "Why do we use it?"
            );

            Collections.shuffle(optionsToClick);

            for (String option : optionsToClick) {
                WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), '" + option + "')]/parent::div")));
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", optionElement);
                optionElement.click();
                System.out.println("Clicked on: " + option);
                wait.until(ExpectedConditions.stalenessOf(optionElement));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
