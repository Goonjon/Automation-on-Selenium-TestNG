package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UpdateProfilePage {
    WebDriver driver;

    @FindBy(tagName = "svg")
    public WebElement profileIcon;

    @FindBy(css = "li.MuiButtonBase-root.MuiMenuItem-root")
    public List<WebElement> profileMenuItem;

    @FindBy(xpath = "//button[text()='Edit']")
    public WebElement btnEdit;

    @FindBy(css = "input.upload-input")  // Adjust selector if needed
    public WebElement fileUpload;

    @FindBy(xpath = "//button[text()='Upload Image']")
    public WebElement uploadImageButton;

    @FindBy(name = "update")
    public WebElement btnUpdate;

    public UpdateProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateToUserView() {
        // Wait for the user table to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));

        // Find the "View" button for the last registered user in the table and click it
        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        WebElement lastRow = rows.get(1);  // Adjust the index based on how rows are structured

        WebElement viewButton = lastRow.findElement(By.xpath(".//button[text()='View']"));
        viewButton.click();
    }

    public void clickEditButton() {
        // Wait for the Edit button and click it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(btnEdit)).click();
    }

    public void uploadProfileImage(String imagePath) {
        // Upload the profile image
        fileUpload.sendKeys(imagePath);
    }

}
