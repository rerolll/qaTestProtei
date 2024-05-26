package qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public abstract class BasePageHelper {
    WebDriver driver;
    public BasePageHelper(WebDriver driver){ this.driver = driver; }

    @FindBy(xpath = "//*[@id='authPage']//*[@id='authButton']")
    public WebElement authButton;

    @FindBy(xpath = "//*[@id='authPage']//*[@id='loginEmail']")
    public WebElement loginEmail;

    @FindBy(xpath = "//*[@id='authPage']//*[@id='loginPassword']")
    public WebElement loginPassword;

    @FindBy(xpath = "//*[@id='inputsPage']//*[@id='dataSend']")
    public WebElement btnDataSend;

    public void login(String email, String password) {
        loginEmail.sendKeys(email);
        loginPassword.sendKeys(password);
        authButton.click();
    }

}