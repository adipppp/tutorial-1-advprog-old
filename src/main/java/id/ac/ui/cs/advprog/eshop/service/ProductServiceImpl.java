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
        if (product == null)
            throw new RuntimeException("Product is null");

        String productName = product.getProductName();
        int productQuantity = product.getProductQuantity();

        if (productName == null)
            throw new RuntimeException("Field Product.productName is null");
        if (productName.length() == 0)
            throw new RuntimeException("Field Product.productName has 0 length");
        if (productQuantity < 0)
            throw new RuntimeException("Field Product.productQuantity is less than 0");

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
}
