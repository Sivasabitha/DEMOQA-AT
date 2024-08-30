package Widgets;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Select_Menu {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://demoqa.com/");
    }

    @Test
    public void selectMenuTest() {
        navigateTo("Widgets", "Select Menu");

        selectOptionByXPath("//*[@id='withOptGroup']/div/div[2]/div", "Group 1, option 2");

        selectOptionByXPath("//*[@id='selectOne']/div/div[2]/div", "Ms.");

        selectOptionByIdAndText("oldSelectMenu", "Blue");

        selectMultipleOptionsByXPath("//*[@id='selectMenuContainer']/div[7]/div/div/div/div[2]/div", "Red", "Green", "Blue");

        selectOptionByCssSelector("#cars", "Opel");
    }

    private void navigateTo(String cardTitle, String optionText) {
        try {
            WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='" + cardTitle + "']")));
            scrollIntoViewAndClick(card);
            System.out.println("Clicked on the " + cardTitle + " card.");

            WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + optionText + "']")));
            scrollIntoViewAndClick(option);
            System.out.println("Clicked on the " + optionText + " option.");
        } catch (Exception e) {
            System.out.println("Failed to navigate to " + cardTitle + " -> " + optionText);
            e.printStackTrace();
        }
    }

    private void selectOptionByXPath(String dropdownXPath, String optionText) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXPath)));
            scrollIntoViewAndClick(dropdown);

            WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + optionText + "')]")));
            scrollIntoViewAndClick(option);
            System.out.println("Selected " + optionText + " from the dropdown.");
        } catch (Exception e) {
            System.out.println("Failed to select " + optionText + " from the dropdown.");
            e.printStackTrace();
        }
    }

    private void selectOptionByIdAndText(String selectId, String optionText) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id(selectId)));
            scrollIntoViewAndClick(dropdown);

            WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[text()='" + optionText + "']")));
            scrollIntoViewAndClick(option);
            System.out.println("Selected " + optionText + " from dropdown");
        } catch (Exception e) {
            System.out.println("Failed to select " + optionText + " from dropdown");
            e.printStackTrace();
        }
    }

    private void selectMultipleOptionsByXPath(String dropdownXPath, String... options) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXPath)));
            scrollIntoViewAndClick(dropdown);

            for (String optionText : options) {
                WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='" + optionText + "']")));
                scrollIntoViewAndClick(option);
                System.out.println("Selected " + optionText + " from multiple select dropdown.");
            }
        } catch (Exception e) {
            System.out.println("Failed to select multiple options from the dropdown.");
            e.printStackTrace();
        }
    }

    private void selectOptionByCssSelector(String cssSelector, String expectedText) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
            scrollIntoViewAndClick(dropdown);

            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[text()='" + expectedText + "']")));
            scrollIntoViewAndClick(option);

            String selectedOptionText = option.getText();
            if (selectedOptionText.equals(expectedText)) {
                System.out.println("Successfully selected option: " + selectedOptionText);
            } else {
                System.out.println("Option selected does not match expected text. Selected: " + selectedOptionText);
            }
        } catch (Exception e) {
            System.out.println("Failed to select option: " + expectedText);
            e.printStackTrace();
        }
    }

    private void scrollIntoViewAndClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
