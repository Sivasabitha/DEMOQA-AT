package Elements;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;

public class Elements_Upload_download {

    private WebDriver driver;
    private WebDriverWait wait;
    private String downloadPath = Paths.get(System.getProperty("user.home"), "Downloads").toAbsolutePath().toString();
    private String fileName = "sample test.txt";

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--enable-logging");
        options.addArguments("--v=1"); // Verbose logging

        options.setExperimentalOption("prefs", Map.of(
            "profile.default_content_settings.popups", 0,
            "download.default_directory", downloadPath,
            "download.prompt_for_download", false,
            "download.directory_upgrade", true
        ));

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.get("https://demoqa.com");
    }

    @Test
    public void testUploadAndDownload() {
        try {
            try {
                WebElement banner = driver.findElement(By.id("fixedban"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = 'none';", banner);
            } catch (Exception e) {
               
            }

            WebElement elementsSection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Elements']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementsSection);
            elementsSection.click();

            WebElement uploadAndDownloadSection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Upload and Download']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", uploadAndDownloadSection);
            uploadAndDownloadSection.click();

            WebElement uploadButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("uploadFile")));
            uploadButton.sendKeys(Paths.get("C:\\Users\\akash\\OneDrive\\Documents\\SIVASABITHA\\New folder\\sample test.txt").toAbsolutePath().toString());

            WebElement downloadButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("downloadButton")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", downloadButton);
            downloadButton.click();

            waitForFileToDownload(downloadPath, fileName, 60);

            File downloadedFile = new File(downloadPath, fileName);
            Assert.assertTrue(downloadedFile.exists(), "Downloaded file does not exist.");

            logDownloadDirectoryContents();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    private void waitForFileToDownload(String downloadPath, String fileName, int timeoutInSeconds) {
        File file = new File(downloadPath, fileName);
        int timeElapsed = 0;
        while (timeElapsed < timeoutInSeconds) {
            if (file.exists()) {
                System.out.println("File downloaded successfully: " + file.getAbsolutePath());
                return;
            }
            try {
                Thread.sleep(1000);
                timeElapsed++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting for file download", e);
            }
        }
        throw new AssertionError("File not downloaded within the timeout period");
    }

    private void logDownloadDirectoryContents() {
        try {
            Files.list(Paths.get(downloadPath))
                .map(path -> path.getFileName().toString())
                .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
