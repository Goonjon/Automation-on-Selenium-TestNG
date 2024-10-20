package testrunner;

import config.AddCostDataset;
import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.CostPage;
import pages.LoginPage;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class AddCostTestRunner extends Setup {
    LoginPage loginPage;

    // Global variable to accumulate the total cost from the CSV
    private double expectedTotalCost = 0;

    @BeforeTest
    public void doLogin() throws IOException, ParseException {
        loginPage=new LoginPage(driver);
        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));

        JSONObject userObj= (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email= (String) userObj.get("email");
        String password= (String) userObj.get("password");
        loginPage.doLogin(email,password);
    }

    @Test(priority = 1, description = "Add a new cost with CSV data", dataProvider = "AddCostCSVData", dataProviderClass = AddCostDataset.class)
    public void CostPage(String itemName, String amount, String quantity, String purchaseDate, String month, String remarks) throws InterruptedException {

        CostPage costPage = new CostPage(driver);
        costPage.btnAddCost.click();
        costPage.CostPage(itemName,amount,quantity,purchaseDate,month,remarks);

        // Instead of multiplying amount by quantity, directly accumulate the amount
        double itemCost = Double.parseDouble(amount);
        expectedTotalCost += itemCost;  // Accumulate the cost directly from the CSV

        // Print the accumulated total after each addition
        System.out.println("Accumulated Expected Total Cost: " + expectedTotalCost);

        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }


    @Test(priority = 2, description = "Print and assert total cost")
    public void totalCost() {
        // Refresh the page to ensure total cost is updated
        driver.navigate().refresh();

        // Locate the total cost element on the dashboard
        WebElement totalCostElement = driver.findElement(By.xpath("//span[contains(text(),'Total Cost:')]"));

        // Extract the displayed total cost as text and convert it to a number
        String displayedTotalCostText = totalCostElement.getText(); // Example: "Total Cost: 500 TK"
        double displayedTotalCost = Double.parseDouble(displayedTotalCostText.replaceAll("[^0-9]", "")); // Remove non-numeric characters

        // Print the displayed total cost and the expected total cost
        System.out.println("Displayed Total Cost: " + displayedTotalCost + " TK");
        System.out.println("Expected Total Cost: " + expectedTotalCost + " TK");

        // Assert the displayed total cost matches the expected total cost
        assertEquals(displayedTotalCost, expectedTotalCost, "Mismatch in total cost!");
    }

    @Test(priority = 3, description = "Search an item and assert the price")
    public void searchAndVerifyItemCost() {

        // Refresh the page to ensure all data is loaded
        driver.navigate().refresh();

        // Wait for the search input field to be visible after refresh
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.search-input")));

        String itemNameToSearch = "Furniture"; // Item to search
        double expectedFurnitureCost = 250.0; // Expected cost of the furniture

        // Enter the item name in the search input field
        searchInput.clear();
        searchInput.sendKeys(itemNameToSearch);

        // Optionally, wait for the search results to load if the page updates dynamically
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//td[text()='" + itemNameToSearch + "']")));

        // Verify the item details in the table
        WebElement tableRow = driver.findElement(By.xpath("//table//td[text()='" + itemNameToSearch + "']"));
        WebElement furnitureCostElement = tableRow.findElement(By.xpath("//td[contains(text(),'250')]")); // Locate the Amount column for the Furniture item

        // Extract the displayed cost of the furniture
        double displayedFurnitureCost = Double.parseDouble(furnitureCostElement.getText());
        System.out.println("Displayed Furniture Cost: " + displayedFurnitureCost);

        // Assert that the displayed cost matches the expected cost
        assertEquals(displayedFurnitureCost, expectedFurnitureCost, "Furniture cost mismatch!");

        // Optionally, check if the total cost on the dashboard reflects the same cost
        WebElement totalCostElement = driver.findElement(By.xpath("//span[contains(text(),'Total Cost:')]"));
        double displayedTotalCost = Double.parseDouble(totalCostElement.getText().replaceAll("[^0-9]", "")); // Remove non-numeric characters
        System.out.println("Displayed Total Cost: " + displayedTotalCost);

        // Assert that the total cost is the same as the furniture cost
        assertEquals(displayedTotalCost, displayedFurnitureCost, "Total cost mismatch for Furniture!");
    }

    @AfterTest
    public void finishTest() {
        driver.quit();
    }

}
