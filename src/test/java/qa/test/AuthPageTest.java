package qa.test;

import org.openqa.selenium.support.PageFactory;

import org.testng.Assert;
import org.testng.annotations.*;

import qa.pages.AuthPageHelper;

import java.lang.reflect.Method;

public class AuthPageTest extends BaseTest{

    AuthPageHelper authPageHelper;

    @BeforeMethod
    public void initTests(Method method){
        authPageHelper = PageFactory.initElements(driver, AuthPageHelper.class);
        driver.navigate().refresh();
        Test testAnnotation = method.getAnnotation(Test.class);
        testCaseId = testAnnotation.description();
    }

    @Test(description = "1")
    public void login_in() {
        homePageHelper.login(VALID_EMAIL, VALID_PASSWORD);
        Assert.assertTrue(authPageHelper.isButtonDataSendPresent());
    }

    @Test(description = "2")
    public void login_in_invalid_password() {
        String invalid_password = "Password123!";
        homePageHelper.login(VALID_EMAIL, invalid_password);

        String alertMessage = authPageHelper.invalidEmailPassword.getText().trim();
        Assert.assertEquals(alertMessage, "Неверный E-Mail или пароль");
    }

    @Test(description = "3")
    public void login_in_invalid_email() {
        String invalid_email = "smth@protei.ru";
        homePageHelper.login(invalid_email, VALID_PASSWORD);

        String alertMessage = authPageHelper.invalidEmailPassword.getText().trim();
        Assert.assertEquals(alertMessage, "Неверный E-Mail или пароль");
    }
}
