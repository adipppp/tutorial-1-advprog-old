package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.model.Product;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
        driver.get(baseUrl + "/product/create");
    }

    @Test
    void productList_isCorrect(ChromeDriver driver) {
        String productName = "Sendal Mas Faiz";
        int productQuantity = 2;

        WebElement nameInput = driver.findElement(
            By.id("nameInput"));
        WebElement quantityInput = driver.findElement(
            By.id("quantityInput"));
        WebElement submitButton = driver.findElement(
            By.cssSelector("button.btn:nth-child(2)"));

        nameInput.sendKeys(productName);
        quantityInput.clear();
        quantityInput.sendKeys(Integer.toString(productQuantity));
        submitButton.click();

        WebElement productNameElement = null;
        WebElement productQuantityElement = null;
        try {
            productNameElement = driver.findElement(
                By.cssSelector(".table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1)"));
            productQuantityElement = driver.findElement(
                By.cssSelector(".table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(2)"));
        } catch (NoSuchElementException exception) {}

        assertNotNull(productNameElement);
        assertNotNull(productQuantityElement);

        String productNameInPage = productNameElement.getText();
        int productQuantityInPage = Integer.parseInt(productQuantityElement.getText());

        assertEquals(productName, productNameInPage);
        assertEquals(productQuantity, productQuantityInPage);
    }

    @Test
    void nullProductName_createProduct_isHandled(ChromeDriver driver) {
        int productQuantity = 2;

        WebElement quantityInput = driver.findElement(
            By.id("quantityInput"));
        WebElement submitButton = driver.findElement(
            By.cssSelector("button.btn:nth-child(2)"));

        driver.executeScript("document.getElementById('nameInput').remove();");
        quantityInput.clear();
        quantityInput.sendKeys(Integer.toString(productQuantity));
        submitButton.click();

        exceptionMessageElement = null;
        try {
            exceptionMessageElement = driver.findElement(
                By.cssSelector(".container > h3:nth-child(4)"));
        } catch (NoSuchElementException exception) {}

        assertNotNull(exceptionMessageElement);
    }

    @Test
    void emptyProductName_createProduct_isHandled(ChromeDriver driver) {
        int productQuantity = 2;

        WebElement quantityInput = driver.findElement(
            By.id("quantityInput"));
        WebElement submitButton = driver.findElement(
            By.cssSelector("button.btn:nth-child(2)"));

        driver.executeScript(
            "document.getElementById('nameInput').removeAttribute('required');");
        quantityInput.clear();
        quantityInput.sendKeys(Integer.toString(productQuantity));
        submitButton.click();

        exceptionMessageElement = null;
        try {
            exceptionMessageElement = driver.findElement(
                By.cssSelector(".container > h3:nth-child(4)"));
        } catch (NoSuchElementException exception) {}

        assertNotNull(exceptionMessageElement);
    }

    @Test
    void negativeProductQuantity_createProduct_isHandled(ChromeDriver driver) {
        String productName = "Sendal Mas Faiz";
        int productQuantity = -1;

        WebElement nameInput = driver.findElement(
            By.id("nameInput"));
        WebElement quantityInput = driver.findElement(
            By.id("quantityInput"));
        WebElement submitButton = driver.findElement(
            By.cssSelector("button.btn:nth-child(2)"));

        nameInput.sendKeys(productName);
        quantityInput.clear();
        quantityInput.sendKeys(Integer.toString(productQuantity));
        submitButton.click();

        exceptionMessageElement = null;
        try {
            exceptionMessageElement = driver.findElement(
                By.cssSelector(".container > h3:nth-child(4)"));
        } catch (NoSuchElementException exception) {}

        assertNotNull(exceptionMessageElement);
    }

    @Test
    void nonNumberProductQuantity_createProduct_isHandled(ChromeDriver driver) {
        String productName = "Sendal Mas Faiz";
        String productQuantity = "abc";

        WebElement nameInput = driver.findElement(
            By.id("nameInput"));
        WebElement quantityInput = driver.findElement(
            By.id("quantityInput"));
        WebElement submitButton = driver.findElement(
            By.cssSelector("button.btn:nth-child(2)"));
        
        nameInput.sendKeys(productName);
        driver.executeScript(
            "document.getElementById('quantityInput').setAttribute('type', 'text')");
        quantityInput.clear();
        quantityInput.sendKeys(productQuantity);
        submitButton.click();

        exceptionMessageElement = null;
        try {
            exceptionMessageElement = driver.findElement(
                By.cssSelector(".container > h3:nth-child(4)"));
        } catch (NoSuchElementException exception) {}

        assertNotNull(exceptionMessageElement);
    }
}
