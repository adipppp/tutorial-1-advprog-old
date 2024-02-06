package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testServiceIfArgsAreNull() {
        Exception exception = assertThrows(RuntimeException.class,
            () -> {
                productService.create(null);
                productService.edit(null);
                productService.delete(null);
            });
        assertEquals("Product is null", exception.getMessage());
        exception = assertThrows(RuntimeException.class,
            () -> productService.findOne(null));
        assertEquals("productId is null", exception.getMessage());
    }

    @Test
    void testCreateIfProductNameIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class,
            () -> {
                Product product = new Product();
                productService.create(product);
            });
        assertEquals("Field Product.productName is null", exception.getMessage());
        exception = assertThrows(RuntimeException.class,
            () -> {
                Product product = new Product();
                product.setProductName("");
                productService.create(product);
            });
        assertEquals("Field Product.productName has 0 length", exception.getMessage());
    }

    @Test
    void testCreateIfProductQuantityIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class,
            () -> {
                Product product = new Product();
                product.setProductName("Sendal Mas Faiz");
                product.setProductQuantity(-1);
                productService.create(product);
            });
        assertEquals("Field Product.productQuantity is less than 0", exception.getMessage());
    }
}