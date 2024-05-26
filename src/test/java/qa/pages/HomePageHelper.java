package qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

public class HomePageHelper extends BasePageHelper {

    @FindBy(xpath = "//*[@id='inputsPage']//*[@id='dataEmail']")
    public WebElement dataEmail;

    @FindBy(xpath = "//*[@id='inputsPage']//*[@id='dataName']")
    public WebElement dataName;

    @FindBy(xpath = "//*[@id='inputsPage']//*[@id='dataGender']")
    public WebElement dataGender;

    @FindBy(xpath = "//*[@class='uk-modal-dialog']//*[@class='uk-margin uk-modal-content']")
    public WebElement succesAlert;

    @FindBy(xpath = "//*[@class='uk-modal-dialog']//*[@class='uk-button uk-button-primary uk-modal-close']")
    public WebElement succesAlertBtn;

    @FindBy(xpath = "//*[@id='inputsPage']//*[@id='emailFormatError']")
    public WebElement alertEmailFormatError;

    @FindBy(xpath = "//*[@id='inputsPage']//*[@id='blankNameError']")
    public WebElement alertBlankNameError;

    @FindAll({ @FindBy(xpath = "//*[@id='inputsPage']//*[@type='checkbox']")})
    public List<WebElement> checkBoxes;

    @FindAll({@FindBy(xpath = "//*[@id='inputsPage']//*[@type='radio']")})
    public List<WebElement> radioBoxes;

    @FindAll({@FindBy(xpath = "//*[@id='inputsPage']//*[@id='dataTable']/tbody/tr[last()]")})
    public List<WebElement> dataTable;



    public static String setValid_email(){
        String str;
        Random gen = new Random();
        int num = gen.nextInt(1000);
        str = "rand0m" + num + "@gmail.com";
        return str;
    }

    public void fillForm(String email, String name, String gender, List<Integer> checkBoxIndices, Integer radioBoxIndex) {
        dataEmail.sendKeys(email);
        dataName.sendKeys(name);

        if (checkBoxIndices != null) {
            for (Integer checkBoxIndex : checkBoxIndices) {
                checkBoxes.get(checkBoxIndex).click();
            }
        }
        if (radioBoxIndex != null) {
            radioBoxes.get(radioBoxIndex).click();
        }

        if (gender != null){
            Select selectGender = new Select(dataGender);
            selectGender.selectByVisibleText(gender);
        }

        btnDataSend.click();
    }

    public boolean isEmailFormatErrorAlertDisplayed() {
        return alertEmailFormatError.getAttribute("innerHTML").contains("Неверный формат E-Mail");
    }

    public boolean isBlankNameErrorAlertDisplayed() {
        return alertBlankNameError.getAttribute("innerHTML").contains("Поле имя не может быть пустым");
    }

    public boolean isSuccessAlertDisplayed() {
        return succesAlert.getAttribute("innerHTML").contains("Данные добавлены.");
    }

    public void closeSuccessAlert() {
        succesAlertBtn.click();
    }

    public String[] getDataFromTable() {
        return dataTable.get(0).getText().split(" ");
    }

    public String getCheckBoxValue(int index) {
        return checkBoxes.get(index).findElement(By.xpath("..")).getText().trim().replace("Вариант ", "");
    }

    public String getRadioBoxValue(int index) {
        return radioBoxes.get(index).findElement(By.xpath("..")).getText().trim().replace("Вариант ", "");
    }

    public void clearFormFields() {
        dataEmail.clear();
        dataName.clear();
        for (WebElement checkbox : checkBoxes) {
            if (checkbox.isSelected()) {
                checkbox.click();
            }
        }
        for (WebElement radioButton : radioBoxes) {
            if (radioButton.isSelected()) {
                radioButton.click();
            }
        }
    }

    public HomePageHelper(WebDriver driver) {
        super(driver);
    }
}
