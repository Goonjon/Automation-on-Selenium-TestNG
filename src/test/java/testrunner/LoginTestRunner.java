package testrunner;

import config.Setup;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;


public class LoginTestRunner extends Setup {
    LoginPage loginPage;
    @Test(priority = 1, description = "Admin login with wrong creds")
    public void adminLoginWithWrongCreds() throws InterruptedException {
        loginPage=new LoginPage(driver);
        loginPage.doLogin("admin@test.com","wrongpass");
        String errorMessageActual= driver.findElement(By.tagName("p")).getText();
        String errorMessageExpected="Invalid";
        Assert.assertTrue(errorMessageActual.contains(errorMessageExpected));
        clearCreds();
    }
    @Test(priority = 2, description = "Admin login with right creds")
    public void adminLogin(){
        loginPage=new LoginPage(driver);
        loginPage.doLogin("admin@test.com","admin123");
        String headerActual= driver.findElement(By.tagName("h2")).getText();
        String headerExpected="Admin Dashboard";
        Assert.assertTrue(headerActual.contains(headerExpected));
        loginPage.doLogout();
    }
    public void clearCreds(){
        loginPage =new LoginPage(driver);
        loginPage.txtEmail.sendKeys(Keys.CONTROL,"a");
        loginPage.txtEmail.sendKeys(Keys.BACK_SPACE);
        loginPage.txtPassword.sendKeys(Keys.CONTROL,"a");
        loginPage.txtPassword.sendKeys(Keys.BACK_SPACE);
    }
}
