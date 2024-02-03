package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }
    
    public Product findOne(String productId) throws RuntimeException {
        boolean productIsFound = false;

        Iterator<Product> productIterator = findAll();
        Product product = null;
        while (productIterator.hasNext()) {
            product = productIterator.next();
            if (productId.equals(product.getProductId())) {
                productIsFound = true;
                break;
            }
        }

        if (!productIsFound)
            throw new RuntimeException("No such product in repository");

        return product;
    }

    public Product delete(Product product) throws RuntimeException {
        boolean productIsFound = false;

        Iterator<Product> productIterator = findAll();
        Product productFromRepo = null;
        while (productIterator.hasNext()) {
            productFromRepo = productIterator.next();
            if (productFromRepo.equals(product)) {
                productIterator.remove();
                productIsFound = true;
                break;
            }
        }

        if (!productIsFound)
            throw new RuntimeException("No such product in repository");

        return productFromRepo;
    }
}
