package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    private static AtomicLong idCounter = new AtomicLong(1);
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        long id = idCounter.getAndIncrement();
        product.setProductId(Long.toString(id));
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }
}
