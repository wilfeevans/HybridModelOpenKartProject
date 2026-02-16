package Rough;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import BaseClass.BaseClass;

public class ExtentReportManagers implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;

    // ✅ ThreadLocal for parallel safety
    public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<>();

    String repName;
    String reportDir = "./src/test/resources/Reports/";

    public void onStart(ITestContext testContext) {

        File reportFolder = new File(reportDir);
        if (!reportFolder.exists()) {
            reportFolder.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(reportDir + repName);

        sparkReporter.config().setDocumentTitle("opencart Automation Report");
        sparkReporter.config().setReportName("opencart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    // ✅ Create test ONLY here
    public void onTestStart(ITestResult result) {

        ExtentTest test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());

        testReport.set(test);  // 🔥 Important

        testReport.get().info("Test Started: " + result.getName());
    }

    public void onTestSuccess(ITestResult result) {
        testReport.get().pass("Test Passed");
    }

    public void onTestFailure(ITestResult result) {

        testReport.get().fail("Test Failed");
        testReport.get().fail(result.getThrowable());

        try {
            String imgPath = BaseClass.captureScreen(result.getName());
            testReport.get().addScreenCaptureFromPath(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {
        testReport.get().skip("Test Skipped");
    }

    public void onFinish(ITestContext testContext) {

        extent.flush();

        String pathOfExtentReport = System.getProperty("user.dir") 
                + "/src/test/resources/Reports/" + repName;

        File extentReport = new File(pathOfExtentReport);

        if (extentReport.exists()) {
            try {
                Desktop.getDesktop().browse(extentReport.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Extent report file not found!");
        }
    }
}
