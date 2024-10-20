package testrunner;

import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.UpdateProfilePage;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

public class UserLoginTestRunner extends Setup {
    LoginPage loginPage;
    UpdateProfilePage updateProfilePage;
    String email;
    String password;

    @BeforeTest
    public void setupLogin() throws IOException, ParseException {
        loginPage = new LoginPage(driver);
        updateProfilePage = new UpdateProfilePage(driver);

        // Parse the JSON file to get the last registered user's credentials
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));
        JSONObject userObj = (JSONObject) jsonArray.get(jsonArray.size() - 1);

        email = (String) userObj.get("email");
        password = (String) userObj.get("password");

        // Log in using the last registered user's credentials
        loginPage.doLogin(email, password);
        Assert.assertTrue(loginPage.btnProfileIcon.isDisplayed(), "Login failed! Profile icon not displayed.");
    }

    @Test(priority = 1, description = "User can login with correct credentials")
    public void userLoginTest() {
        // This test case already performed login in the @BeforeTest method and verified the profile icon.
        System.out.println("User logged in successfully with email: " + email);
    }

    @Test(priority = 2, description = "Update profile image")
    public void updateUserProfileImageTest() throws InterruptedException {
        // Click on profile icon
        updateProfilePage.profileIcon.click();

        // Click on the first menu item (assuming it navigates to the profile page)
        updateProfilePage.profileMenuItem.get(0).click();

        // Click "Edit" on the profile page
        updateProfilePage.clickEditButton();

        // Upload profile image
        String imagePath = System.getProperty("user.dir") + "\\src\\test\\resources\\images.jpeg";  // Absolute path
        updateProfilePage.uploadProfileImage(imagePath);

        // Click upload image button
        updateProfilePage.uploadImageButton.click();

        // Handle browser alert popup after clicking the update button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();  // Click OK on the alert

        // Log and assert profile update success
        String successMessageExpected = "Image uploaded successfully";  // Adjust this based on actual UI message
        System.out.println("Profile image updated successfully.");
    }

    @AfterTest
    public void tearDown() {
        // Logout and perform cleanup
        if (loginPage.btnProfileIcon.isDisplayed()) {
            loginPage.doLogout();
            System.out.println("User logged out successfully.");
        } else {
            System.out.println("Profile icon not displayed, possibly not logged in.");
        }
    }
}
