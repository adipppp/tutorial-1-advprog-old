package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductServiceImpl implements ProductService {
    private static AtomicLong idCounter = new AtomicLong(1);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        String productId = Long.toString(idCounter.getAndIncrement());
        product.setProductId(productId);
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findOne(String productId) {
        Product product;
        try {
            product = productRepository.findOne(productId);
        } catch (RuntimeException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
        return product;
    }

    @Override
    public Product delete(Product product) {
        if (product.getProductId() == null)
            throw new RuntimeException("Field Product.productId is null");

        Product productFromRepo;
        try {
            productFromRepo = productRepository.delete(product);
        } catch (RuntimeException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
        return productFromRepo;
    }
}
