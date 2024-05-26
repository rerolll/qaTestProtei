package qa.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthPageHelper extends BasePageHelper{

    @FindBy(xpath = "//*[@id='authPage']//*[@id='invalidEmailPassword']")
    public WebElement invalidEmailPassword;

    public boolean isButtonDataSendPresent() {
        return btnDataSend.isDisplayed();
    }

    public AuthPageHelper(WebDriver driver) {
        super(driver);
    }
}
