package sausedemotests;

import core.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginTests extends BaseTest {

    @ParameterizedTest
    @CsvSource({
            "standard_user, secret_sauce",
            "locked_out_user, secret_sauce",
            "problem_user, secret_sauce",
            "performance_glitch_user, secret_sauce"
    })
    public void userAuthenticated_when_validCredentialsProvided(String username, String password) {
        loginPage.navigate();
        loginPage.fillLoginForm(username, password);
        if (username.equals("locked_out_user")) {
            WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
            Assertions.assertTrue(errorMessage.isDisplayed(), "Expected error message for locked out user is not displayed");
        } else {
            inventoryPage.waitForPageTitle();
            inventoryPage.assertNavigated();
        }
    }
}