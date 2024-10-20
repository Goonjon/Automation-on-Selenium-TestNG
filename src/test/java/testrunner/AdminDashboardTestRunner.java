package testrunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.Setup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdminDashboardTestRunner extends Setup {
    LoginPage loginPage;

    @BeforeTest
    public void doLogin() {
        loginPage=new LoginPage(driver);
        loginPage.doLogin("admin@test.com","admin123");

        //Code for pass credentials from terminal
        //loginPage.doLogin(System.getProperty("username"),System.getProperty("password"));
    }

    @Test(priority = 3, description = "Verify the last registered user in the admin dashboard")
    public void verifyLastRegisteredUser() throws IOException, InterruptedException {

        // Get the first row (newest registered user) from the admin dashboard table
        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        WebElement firstRow = rows.get(1); // First row contains the most recent user

        // Extract user information from the first row (cells for first name, email, and phone)
        List<WebElement> cells = firstRow.findElements(By.tagName("td"));
        String dashboardFirstName = cells.get(0).getText();
        String dashboardEmail = cells.get(2).getText();
        String dashboardPhone = cells.get(3).getText();

        // Read the last registered user data from the users.json file
        File jsonFile = new File("src/test/resources/users.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode users = objectMapper.readTree(jsonFile);

        // Get the last user object in the JSON array (the most recent user added)
        JsonNode lastRegisteredUser = users.get(users.size() - 1);
        String jsonFirstName = lastRegisteredUser.get("firstName").asText();
        String jsonEmail = lastRegisteredUser.get("email").asText();
        String jsonPhone = lastRegisteredUser.get("phoneNumber").asText();

        // Log the data for debugging
        System.out.println("Dashboard Data: First Name - " + dashboardFirstName + ", Email - " + dashboardEmail + ", Phone - " + dashboardPhone);
        System.out.println("JSON Data: First Name - " + jsonFirstName + ", Email - " + jsonEmail + ", Phone - " + jsonPhone);

        // Assert the dashboard data matches the JSON data
        Assert.assertEquals(dashboardFirstName, jsonFirstName, "First name does not match!");
        Assert.assertEquals(dashboardEmail, jsonEmail, "Email does not match!");
        Assert.assertEquals(dashboardPhone, jsonPhone, "Phone number does not match!");
    }

    @AfterTest
    public void finishTest() {
        driver.quit();
    }

}
