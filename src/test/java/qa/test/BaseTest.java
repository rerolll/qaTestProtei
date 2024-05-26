package qa.test;

import gurock.testrail.APIClient;
import gurock.testrail.APIException;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.json.simple.JSONObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import qa.pages.HomePageHelper;
import qa.pages.resources.TakeJsonData;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;


public class BaseTest {
    public static WebDriver driver;
    public static ChromeOptions chromeOptions;

    public static String VALID_EMAIL = TakeJsonData.take_json_data("valid_email");
    public static String VALID_PASSWORD = TakeJsonData.take_json_data("valid_password");

    protected static String localFilePath  = new File("qa-test.html").getAbsolutePath();
    protected static String baseUrl = localFilePath;

    public static String TestRailUsername = TakeJsonData.take_json_data("testRail_email");
    public static String TestRailPassword = TakeJsonData.take_json_data("testRail_password");

    public static final int TEST_CASE_PASSED_STATUS = 1;
    public static final int TEST_CASE_FAILED_STATUS = 5;

    public static final String TESTRAIL_RUN_ID = "1";
    public static String testCaseId;


    public static APIClient client = new APIClient(TakeJsonData.take_json_data("testRail_baseUrl"));

     HomePageHelper homePageHelper;

    public static void AddResultForTestCaseInTestRail(String TestRailRunId, String TestCaseId, int status)
            throws IOException, APIException {
        client.setUser(TestRailUsername);
        client.setPassword(TestRailPassword);

        Map data = new HashMap();
        data.put("status_id", status);
        data.put("comment", "This test worked fine!");
        JSONObject r = (JSONObject) client.sendPost("add_result_for_case/" +TestRailRunId+ "/"+TestCaseId+"", data);
    }


    @BeforeSuite
    public void initWebDriver(){
        WebDriverManager.chromedriver().setup();
        chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--headless=new");
        chromeOptions.addArguments("window-size=1920, 1080");
        driver = new ChromeDriver(chromeOptions);
        homePageHelper = PageFactory.initElements(driver, HomePageHelper.class);
        driver.get(baseUrl);
    }

    @AfterMethod
    public void addTestResult(ITestResult result) throws APIException, IOException {
        int resultStatus;
        if(result.isSuccess()) {
            resultStatus = TEST_CASE_PASSED_STATUS;
        } else {
            resultStatus = TEST_CASE_FAILED_STATUS;
        }
        BaseTest.AddResultForTestCaseInTestRail(TESTRAIL_RUN_ID, testCaseId, resultStatus);
    }

    @AfterSuite
    public void teardown(){
        driver.quit();
    }

}