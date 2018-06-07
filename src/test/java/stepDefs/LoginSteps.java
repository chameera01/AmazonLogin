package stepDefs;

import java.util.HashMap;
import java.util.Map;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utils.SeleniumConfigs;

public class LoginSteps extends SeleniumConfigs {

    SeleniumConfigs sc = new SeleniumConfigs();

    private Map<String, String> properties = new HashMap<String, String>();
    private Map<String, String> productDetails = new HashMap<String, String>();
    private Map<String, String> cartProductDetails = new HashMap<String, String>();
    String errorMessage;
    String message;
    Boolean isAvailable = false;

    @Before
    public void before() {
        properties = sc.readPropertyFile();
        sc.initiateTheWebDriver();
        System.out.println("==Before===");
    }

    @Given("^I navigate to the URL$")
    public void navigateURL() throws Throwable {
        sc.urlLaunched(properties.get("url"));
    }

    @When("^I type valid user name and valid password$")
    public void successFulLogin() throws Throwable {
        sc.signin(properties.get("username"), properties.get("password"));
    }


    @Then("^I validate the home page header$")
    public void successLogin() throws Throwable {
        sc.validateSuccessLogin();
    }

    @When("^I type incorrect user name \"(.*)\" and password \"(.*)\"$")
    public void failedLogin(String username, String password) throws Throwable {
        sc.incorrectSignin(username);
    }

    @Then("^I read the error$")
    public void readingError() throws Throwable {
        errorMessage = sc.readError();
    }

    @Then("error should be \"(.*)\"$")
    public void validateError(String correctMessage) throws Throwable {
        correctMessage.contains(errorMessage);

    }

    @When("I type valid user name and password \"(.*)\"$")
    public void loginWithinValidPassword(String password) throws Throwable {
        sc.signin(properties.get("username"), password);
    }

    @When("I check for the page header")
    public void checkForPageHeader() throws Throwable {
        isAvailable = sc.validatePageHeader();

    }

    @Then("I validate the page header")
    public void visibilityOfPageHeader() {
        isAvailable.equals(true);

    }

    @When("I search for \"(.*)\"$")
    public void searchItemName(String itemName) throws Throwable {
        sc.searchItem(itemName);
    }

    @When("I click on the first product visible")
    public void clickOnFirstItem() throws Throwable {
        sc.clickFirstItem();
    }

    @Then("product page should be visible")
    public void validateTheProductPage() {
        String value = sc.viewProductPage();
        value.contains("Back to search results for");

    }

    @When("product name is captured as \"(.*)\"$")
    public void captureProdName(String itemName) throws Throwable {
        productDetails = sc.captureProductName(itemName);
    }

    @When("product price is captured as \"(.*)\"$")
    public void captureProdPrice(String itemPrice) throws Throwable {
        productDetails = sc.captureProductPrice(itemPrice);
    }

    @When("I add \"(.*)\" qualitity to the basket")
    public void addProductToTheCart(String noOfItems) throws Throwable {
        sc.addItemToTheCart(noOfItems);
    }

    @When("I read the message in the product page")
    public void readTheProductMessage() throws Throwable {
        message = sc.readTheMessage();
    }

    @Then("Message should be visible as \"(.*)\"$")
    public void validateMessage(String correctMessage) throws Throwable {
        correctMessage.equals(message);

    }

    @When("I click on the cart")
    public void clickTheCart() throws Throwable {
        sc.clickOnTheCart();

    }

    @When("I read the \"(.*)\" in the cart$")
    public void readTheProductNameInTheCart(String key) throws Throwable {

        if (key.equals("productNameValue")) {
            cartProductDetails.put(key, sc.readTheCartProductName());
        } else if (key.equals("productPriceValue")) {
            cartProductDetails.put(key, sc.readTheCartProductValue());
        }
    }

    @When("I logout from the system$")
    public void logOut() throws Throwable {
        sc.logout();

    }

    @When("I relogin with valid user name and valid password$")
    public void reLogin() throws Throwable {
        sc.LoginAfterlogout(properties.get("username"), properties.get("password"));
    }

    @Then("the product value should be \"(.*)\"")
    public void validateProductNameInCart(String value) {
        cartProductDetails.get(value).equals(productDetails.get(value));

    }

    @After
    public void after() {
        sc.closeBrowser();

    }

}
