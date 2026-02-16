package ExtentListeners;

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
import com.aventstack.extentreports.reporter.configuration.ViewName;

import BaseClass.BaseClass;

public class ExtentReportManager implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;

    // ✅ ThreadLocal for parallel execution safety
    public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<>();

    private String repName;
    private String reportDir = "./src/test/resources/Reports/";

    @Override
    public void onStart(ITestContext testContext) {

        File reportFolder = new File(reportDir);
        if (!reportFolder.exists()) {
            reportFolder.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(reportDir + repName);

        sparkReporter.config().setDocumentTitle("Opencart Automation Report");
        sparkReporter.config().setReportName("Opencart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        // ✅ Ensure Dashboard (Pie Chart) loads first
        sparkReporter.viewConfigurer()
                .viewOrder()
                .as(new ViewName[] {
                        ViewName.TEST,
                        ViewName.CATEGORY,
                        ViewName.EXCEPTION,
                        ViewName.DASHBOARD,
                        ViewName.AUTHOR,
                        ViewName.DEVICE
                })
                .apply();

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "Opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");
        if (os != null)
            extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        if (browser != null)
            extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    // ✅ Create test only once
    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest test = extent.createTest(
                result.getTestClass().getRealClass().getSimpleName() + " :: " + result.getName()
        );

        test.assignCategory(result.getMethod().getGroups());
        testReport.set(test);

        testReport.get().info("Test Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testReport.get().pass("Test Passed Successfully");
    }

    @Override
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

    @Override
    public void onTestSkipped(ITestResult result) {
        testReport.get().skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext testContext) {

        extent.flush();

        // Small delay to ensure full rendering
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        
        
        /*
        try {
            URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);

            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com", "password"));
            email.setSSLOnConnect(true);
            email.setFrom("pavanoltraining@gmail.com");
            email.setSubject("Test Results");
            email.setMsg("Please find Attached Report....");
            email.addTo("pavankumar.busyqa@gmail.com");
            email.attach(url, "extent report", "please check report...");
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        
               
    }
}
