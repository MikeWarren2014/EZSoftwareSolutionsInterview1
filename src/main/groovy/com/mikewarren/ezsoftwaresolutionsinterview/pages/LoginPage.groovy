package com.mikewarren.ezsoftwaresolutionsinterview.pages


import com.mikewarren.ezsoftwaresolutionsinterview.utils.WebDriverUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class LoginPage {
    final String url;

    @FindBy(id = 'sign-in-email')
    WebElement emailField;

    @FindBy(id = 'sign-in-password')
    WebElement passwordField;

    @FindBy(id = 'login-button')
    WebElement loginButton;

    public LoginPage() {
        this.url = "https://dev.studio.ezsoftpos.com/login";
        PageFactory.initElements(WebDriverUtils.GetWebDriver(), this)
    }

    public void go() {
        WebDriver driver = WebDriverUtils.GetWebDriver();

        driver.get(this.url);
    }

    public void login(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();
    }

}
