package com.a11y;

import com.deque.html.axecore.axeargs.AxeRunOnlyOptions;
import com.deque.html.axecore.axeargs.AxeRunOptions;
import com.deque.html.axecore.extensions.WebDriverExtensions;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.deque.html.axecore.selenium.ResultType;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

public class A11yTest extends TestBase {


    @Test
    public void analyzeEntirePage(Method method) throws IOException, OperationNotSupportedException {
        driver.get("https://www.google.com/");
        result = WebDriverExtensions.analyze(driver);
        try {
            Assert.assertTrue(AxeReporter.getReadableAxeResults(ResultType.Violations.getKey(), driver, result.getViolations()), "No violations found");
            Assert.assertNotEquals(result.getViolations().size(), 0, "No violations found");
        } finally {
            generateReports(method.getName());
        }
    }

    @Test
    public void analyzeSpecificElement(Method method) throws IOException, OperationNotSupportedException {
        driver.get("http://the-internet.herokuapp.com/broken_images");
        result = WebDriverExtensions.analyze(driver, driver.findElement(By.id("content")));
        try {
            Assert.assertTrue(AxeReporter.getReadableAxeResults(ResultType.Violations.getKey(), driver, result.getViolations()), "No violations found");
            Assert.assertEquals(result.getViolations().size(), 1, String.format("%s violations found", result.getViolations().size()));
        } finally {
            generateReports(method.getName());
        }
    }

    @Test
    public void analyzePageIncludingExcludingOptions(Method method) {
        driver.get("https://www.workable.com/");
        result = new AxeBuilder()
                .include(Arrays.asList(".demo-badges__no-divider___30pYA"))
                .exclude(Collections.singletonList("#stars"))
                .analyze(driver);
        try {
            Assert.assertFalse(AxeReporter.getReadableAxeResults(ResultType.Violations.getKey(), driver, result.getViolations()), "Violations found");
            Assert.assertEquals(result.getViolations().size(), 0, String.format("%s violations found", result.getViolations().size()));
        } finally {
            generateReports(method.getName());
        }
    }

    @Test
    public void analyzePageWithTags(Method method) {
        driver.get("https://www.google.com/");
        result = new AxeBuilder()
                .withTags(Collections.singletonList("best-practice"))
                .withOutputFile(JSON_OUTPUT_PATH + method.getName() + ".json")
                .analyze(driver);
        try {
            Assert.assertTrue(AxeReporter.getReadableAxeResults(ResultType.Violations.getKey(), driver, result.getViolations()), "No violations found");
            Assert.assertEquals(result.getViolations().size(), 3, String.format("%s violations found", result.getViolations().size()));
        } finally {
            generateReports(method.getName());
        }
    }

    @Test
    public void analyzePageWithOptions(Method method) {
        driver.get("https://www.google.com/");
        AxeRunOnlyOptions runOnlyOptions = new AxeRunOnlyOptions();
        runOnlyOptions.setType("tag");
        runOnlyOptions.setValues(Arrays.asList("wcag2a", "best-practice"));
        AxeRunOptions runOptions = new AxeRunOptions();
        runOptions.setRunOnly(runOnlyOptions);
        result = new AxeBuilder()
                .withOptions(runOptions)
                .analyze(driver);
        try {
            Assert.assertTrue(AxeReporter.getReadableAxeResults(ResultType.Violations.getKey(), driver, result.getViolations()), "No violations found");
            Assert.assertEquals(result.getViolations().size(), 5, String.format("%s violations found", result.getViolations().size()));
        } finally {
            generateReports(method.getName());
        }
    }
}
