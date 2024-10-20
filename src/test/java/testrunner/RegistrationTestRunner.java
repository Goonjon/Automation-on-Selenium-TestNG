package testrunner;

import config.Setup;
import com.github.javafaker.Faker;
import config.UserModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegistrationPage;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;

public class RegistrationTestRunner extends Setup {

    @Test(priority = 1, description = "Registration without mandatory field")
    public void userRegByMissingMandatoryFields(){
        RegistrationPage userReg=new RegistrationPage(driver);
        userReg.btnRegister.click();
        userReg.btnSubmitReg.click();
        String validationError= userReg.txtFirstname.getAttribute("validationMessage");
        Assert.assertTrue(validationError.contains("Please fill out this field"));
        userReg.loginLink.click();

    }

    @Test(priority = 2, description = "Register a user with all fields", groups="smoke")
    public void userRegByAllFields() throws InterruptedException, IOException, ParseException {
        RegistrationPage userReg = new RegistrationPage(driver);
        // Initialize Faker for generating random data
        Faker faker = new Faker();
        userReg.btnRegister.click();

        // Generate random test data
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String email = "nadia" + (int)(Math.random() * 10000) + "@gmail.com";
        String password = faker.internet().password();
        String phonenumber = faker.phoneNumber().cellPhone();
        String address = faker.address().fullAddress();

        UserModel userModel= new UserModel();
        userModel.setFirstname(firstname);
        userModel.setLastname(lastname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhonenumber(phonenumber);
        userModel.setAddress(address);
        userReg.doRegistration(userModel);

        //assertion
        doRegAssertion();

        JSONObject userObj = new JSONObject();
        userObj.put("firstName", firstname);
        userObj.put("lastName", lastname);
        userObj.put("email", email);
        userObj.put("password", password);
        userObj.put("phoneNumber", phonenumber);
        userObj.put("address", address);
        Utils.saveUserInfo("./src/test/resources/users.json", userObj);
    }

    @Test(priority = 3, description = "Register a user with only mandatory fields")
    public void userRegByMandatoryFields() throws IOException, ParseException, InterruptedException {
        RegistrationPage userReg = new RegistrationPage(driver);
        // Initialize Faker for generating random data
        Faker faker = new Faker();
        userReg.btnRegister.click();

        // Generate random test data
        String firstname = faker.name().firstName();
        String email = "nadia" + (int)(Math.random() * 10000) + "@gmail.com";
        String password = faker.internet().password();
        String phonenumber = faker.phoneNumber().cellPhone();

        UserModel userModel= new UserModel();
        userModel.setFirstname(firstname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhonenumber(phonenumber);
        userReg.doRegistration(userModel);

        doRegAssertion();
        JSONObject userObj = new JSONObject();
        userObj.put("firstName", firstname);
        userObj.put("email", email);
        userObj.put("password", password);
        userObj.put("phoneNumber", phonenumber);
        Utils.saveUserInfo("./src/test/resources/users.json", userObj);
    }

    public void doRegAssertion() throws InterruptedException {
        Thread.sleep(2000);

        // Wait for the success message
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Toastify__toast")));

        // Get and verify the success message
        String successMessageActual = driver.findElement(By.className("Toastify__toast")).getText();
        String successMessageExpected = "successfully";
        System.out.println(successMessageActual);
        Assert.assertTrue(successMessageActual.contains(successMessageExpected));
    }
}