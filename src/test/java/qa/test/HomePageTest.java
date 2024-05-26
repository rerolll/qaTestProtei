package qa.test;

import org.openqa.selenium.support.PageFactory;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import qa.pages.HomePageHelper;

import java.lang.reflect.Method;
import java.util.List;


public class HomePageTest extends BaseTest{

    HomePageHelper homePageHelper;

    @BeforeClass
    public void login_in(){
        homePageHelper = PageFactory.initElements(driver, HomePageHelper.class);
        homePageHelper.login(VALID_EMAIL, VALID_PASSWORD);
    }

    @BeforeMethod
    public void initTests(Method method){
        Test testAnnotation = method.getAnnotation(Test.class);
        testCaseId = testAnnotation.description();
    }

    @AfterMethod
    public void clearForm(){
        homePageHelper.clearFormFields();
    }

    @Test(description = "4")
    public void checkAdd() {
        String email = "temp@gmail.com";
        String name = "Oleg";

        homePageHelper.fillForm(email, name, null, null, null);

        Assert.assertTrue(homePageHelper.isSuccessAlertDisplayed());
        homePageHelper.closeSuccessAlert();

        String[] data = homePageHelper.getDataFromTable();
        validateFormData(data, email, name, null, null, null);

    }

    @Test(description = "5")
    public void selectAllCheckBoxesAndVerify() {
        String email = HomePageHelper.setValid_email();
        String name = "Ar";
        String gender = "Женский";
        List<Integer> checkBoxIndices = List.of(0, 1);

        homePageHelper.fillForm(email, name, gender, checkBoxIndices , 0);

        Assert.assertTrue(homePageHelper.isSuccessAlertDisplayed());
        homePageHelper.closeSuccessAlert();

        String[] data = homePageHelper.getDataFromTable();

        validateFormData(data, email, name, gender, checkBoxIndices, 0);

    }

    @Test(description = "6")
    public void selectAllRadioBoxesAndVerify() {
        String email = HomePageHelper.setValid_email();
        String name = "Name";
        String gender = "Мужской";
        int numberOfRadioBoxes = 3;

        for(int radioBoxIndex = 0; radioBoxIndex < numberOfRadioBoxes; radioBoxIndex++){

            homePageHelper.fillForm(email, name, gender, null, radioBoxIndex);

            Assert.assertTrue(homePageHelper.isSuccessAlertDisplayed());
            homePageHelper.closeSuccessAlert();

            String[] data = homePageHelper.getDataFromTable();


            validateFormData(data, email, name, gender, null, radioBoxIndex);
            homePageHelper.clearFormFields();
        }
    }

    @Test(description = "7", priority = 3)
    public void checkAlertInvalidEmail() {
        String email = "asda";
        String name = "Oleg";

        homePageHelper.fillForm(email, name, null, null, null);

        Assert.assertTrue(homePageHelper.isEmailFormatErrorAlertDisplayed());

        homePageHelper.clearFormFields();

        email = "asda@mail";
        homePageHelper.fillForm(email, name, null, null, null);

        Assert.assertTrue(homePageHelper.isEmailFormatErrorAlertDisplayed());
    }


    @Test(description = "8")
    public void checkAlertInvalidName() {
        String email = HomePageHelper.setValid_email();
        String name = "";

        homePageHelper.fillForm(email, name, null, null, null);

        Assert.assertTrue(homePageHelper.isBlankNameErrorAlertDisplayed());
    }

    public void validateFormData(String[] data, String email, String name, String gender, List<Integer> checkBoxIndices, Integer radioBoxIndex) {
        Assert.assertEquals(email, data[0], "Email validation failed.");
        Assert.assertEquals(name, data[1], "Name validation failed.");
        if (gender == null){
            gender = "Мужской";
        }
        Assert.assertEquals(gender, data[2], "Gender validation failed.");

        int currentIndex = 3;

        if (checkBoxIndices != null && !checkBoxIndices.isEmpty()) {
            for (Integer checkBoxIndex : checkBoxIndices) {
                String checkBoxValue = homePageHelper.getCheckBoxValue(checkBoxIndex);
                Assert.assertTrue(data[currentIndex].contains(checkBoxValue), "Checkbox validation failed for index: " + checkBoxIndex);
                currentIndex++;
            }
        } else { currentIndex++; }

        if (radioBoxIndex != null) {
            String radioBoxValue = homePageHelper.getRadioBoxValue(radioBoxIndex);
            Assert.assertEquals(radioBoxValue, data[currentIndex], "Radio button validation failed.");
        }
    }
}

