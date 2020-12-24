package com.a11y;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestBase {

    public static final String JSON_OUTPUT_PATH = "src/test/java/results/json/";
    public static final String TXT_OUTPUT_PATH = "src/test/java/results/txt/";
    protected WebDriver driver;
    protected Results result;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors");
        driver = new ChromeDriver(options);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    protected void generateReports(String testName) {
        AxeReporter.writeResultsToJsonFile(JSON_OUTPUT_PATH + testName, result);
        AxeReporter.writeResultsToTextFile(TXT_OUTPUT_PATH + testName, AxeReporter.getAxeResultString());
    }

}
