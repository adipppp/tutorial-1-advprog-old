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
    }

    @Test
    void productList_isCorrect(ChromeDriver driver) {
        String productName = "Sendal Mas Faiz";
        int productQuantity = 2;

        driver.get(baseUrl + "/product/create");

        driver.findElement(
            By.cssSelector("#nameInput")).sendKeys(productName);
        driver.findElement(
            By.cssSelector("#quantityInput")).sendKeys(Integer.toString(productQuantity));
        driver.findElement(
            By.cssSelector(".btn")).click();

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
}
