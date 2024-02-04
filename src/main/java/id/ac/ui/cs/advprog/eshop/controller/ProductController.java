package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        try {
            service.create(product);
        } catch (RuntimeException exception) {
            exception.printStackTrace();

            String exceptionMessage = exception.getMessage();
            switch (exceptionMessage) {
                case "Field Product.productQuantity is less than 0":
                    model.addAttribute("error", "Product quantity cannot be negative");
                    break;
                case "Field Product.productName has 0 length":
                    model.addAttribute("error", "Product name should not be left empty");
                    break;
                case "Field Product.productName is null":
                    model.addAttribute("error", "Request body is invalid");
                    break;
            }
            return "createProduct";
        }
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    @GetMapping("/delete")
    public String deleteProductPage(@RequestParam String productId, Model model) {
        Product product;
        try {
            product = service.findOne(productId);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return "redirect:list";
        }
        model.addAttribute("product", product);
        return "deleteProduct";
    }

    @PostMapping("/delete")
    public String deleteProductPost(@ModelAttribute Product product, Model model) {
        try {
            service.delete(product);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
        }
        return "redirect:list";
    }
}
