package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindOne() {
        Product product = new Product();
        product.setProductId("46e4ce01-d7f8-4c50-811f-871ab409a05a");
        product.setProductName("Sendal Mas Faiz");
        product.setProductQuantity(2);
        productRepository.create(product);

        assertEquals(product, productRepository.findOne("46e4ce01-d7f8-4c50-811f-871ab409a05a"));
    }

    @Test
    void testFindOneIfEmpty() {
        Exception exception = assertThrows(RuntimeException.class, () ->
            productRepository.findOne("46e4ce01-d7f8-4c50-811f-871ab409a05a"));
        assertEquals("No such product in repository", exception.getMessage());
    }

    @Test
    void testFindOneIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("46e4ce01-d7f8-4c50-811f-871ab409a05a");
        product1.setProductName("Sendal Mas Faiz");
        product1.setProductQuantity(2);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("8418c357-32d1-4c14-8195-086f17ba1399");
        product2.setProductName("Peci Mas Fuad");
        product2.setProductQuantity(1);
        productRepository.create(product2);

        assertEquals(product1, productRepository.findOne("46e4ce01-d7f8-4c50-811f-871ab409a05a"));
        assertEquals(product2, productRepository.findOne("8418c357-32d1-4c14-8195-086f17ba1399"));
    }
}
