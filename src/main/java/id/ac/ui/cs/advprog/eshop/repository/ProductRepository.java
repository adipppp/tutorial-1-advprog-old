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

    public Product edit(Product product) throws RuntimeException {
        Product productFromRepo;
        try {
            productFromRepo = findOne(product.getProductId());
        } catch (RuntimeException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }

        productFromRepo.setProductName(product.getProductName());
        productFromRepo.setProductQuantity(product.getProductQuantity());

        return productFromRepo;
    }

    public Product findOne(String productId) throws RuntimeException {
        boolean productIsFound = false;

        Iterator<Product> productIterator = findAll();
        Product product = null;
        while (productIterator.hasNext()) {
            product = productIterator.next();
            if (product.getProductId().equals(productId)) {
                productIsFound = true;
                break;
            }
        }

        if (!productIsFound)
            throw new RuntimeException("No such product in repository");

        return product;
    }
}
