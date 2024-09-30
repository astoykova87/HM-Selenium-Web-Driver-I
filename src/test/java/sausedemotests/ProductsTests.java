package sausedemotests;

import core.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductsTests extends BaseTest {

    @Test
    public void productAddedToShoppingCart_when_addToCart() {
        authenticateWithUser("standard_user", "secret_sauce");

        addToCart("Sauce Labs Backpack");
        addToCart("Sauce Labs Bike Light");

        driver.findElement(By.className("shopping_cart_link")).click();

        List<WebElement> cartItems = driver.findElements(By.className("inventory_item_name"));
        boolean isBackpackAdded = false;
        boolean isBikeLightAdded = false;

        for (WebElement item : cartItems) {
            if (item.getText().equals("Sauce Labs Backpack")) {
                isBackpackAdded = true;
            }
            if (item.getText().equals("Sauce Labs Bike Light")) {
                isBikeLightAdded = true;
            }
        }

        assertTrue(isBackpackAdded, "Backpack not added to the cart");
        assertTrue(isBikeLightAdded, "Bike Light not added to the cart");
    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation() {
        authenticateWithUser("standard_user", "secret_sauce");

        addToCart("Sauce Labs Backpack");
        addToCart("Sauce Labs Bike Light");

        driver.findElement(By.className("shopping_cart_link")).click();
        proceedToCheckout();

        fillShippingDetails("John", "Doe", "12345");
        driver.findElement(By.id("continue")).click();

        String summaryText = driver.findElement(By.className("title")).getText();
        assertTrue(summaryText.contains("Checkout: Overview"), "Failed to navigate to Checkout Overview page");
    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm() {
        authenticateWithUser("standard_user", "secret_sauce");

        addToCart("Sauce Labs Backpack");
        addToCart("Sauce Labs Bike Light");

        driver.findElement(By.className("shopping_cart_link")).click();
        proceedToCheckout();

        fillShippingDetails("John", "Doe", "12345");
        driver.findElement(By.id("continue")).click();

        driver.findElement(By.id("finish")).click();

        driver.findElement(By.className("shopping_cart_link")).click();
        List<WebElement> cartItems = driver.findElements(By.className("inventory_item_name"));
        assertEquals(0, cartItems.size(), "Cart is not empty after completing the order");
    }
}