package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CostPage {

    WebDriver driver;
    @FindBy(className = "add-cost-button")
    public WebElement btnAddCost;

    @FindBy(id = "itemName")
    public WebElement txtItemName;

    @FindBy(id = "amount")
    public WebElement txtAmount;

    //@FindBy(id = "quantityMinus")
    //public WebElement btnQuantityMinus;

    //@FindBy(id = "quantityPlus")
    //public WebElement btnQuantityPlus;

    @FindBy(xpath = "//input[@id='purchaseDate']")
    public WebElement datePickerPurchaseDate;

    @FindBy(id = "remarks")
    public WebElement txtRemarks;

    @FindBy(css = "button.submit-button")
    public WebElement btnSubmit;

    public CostPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void CostPage(String itemName, String amount, String quantity, String purchaseDate, String month, String remarks) throws InterruptedException {
        // Fill Item Name
        txtItemName.sendKeys(itemName);

        // Fill Amount
        txtAmount.sendKeys(amount);

        // Adjust Quantity using Plus button (will do later)
//        int qty = Integer.parseInt(quantity);
//        for (int i = 0; i < qty - 1; i++) {
//            btnQuantityPlus.click();
//            Thread.sleep(500);  // Small pause for UI interaction
//        }
        datePickerPurchaseDate.clear();
        datePickerPurchaseDate.sendKeys(purchaseDate);
        Select select =new Select(driver.findElement(By.id("month")));
        select.selectByVisibleText(month);

        txtRemarks.sendKeys(remarks);
        // Submit the form
        btnSubmit.click();
    }


}
