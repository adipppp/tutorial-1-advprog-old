package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping({"/create", "/create/"})
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping({"/create", "/create/"})
    public String createProductPost(Model model, @ModelAttribute Product product, BindingResult result) {
        try {
            if (result.hasErrors())
                throw new RuntimeException("Product quantity is not an integer");
            service.create(product);
        } catch (RuntimeException exception) {
            exception.printStackTrace();

            String messageToDisplay;
            String exceptionMessage = exception.getMessage();
            switch (exceptionMessage) {
                case "Field Product.productQuantity is less than 0":
                    messageToDisplay = "Product quantity cannot be negative";
                    break;
                case "Product quantity is not an integer":
                    messageToDisplay = "Product quantity is not an integer";
                    break;
                case "Field Product.productName has 0 length":
                    messageToDisplay = "Product name should not be left empty";
                    break;
                case "Field Product.productName is null":
                    messageToDisplay = "Request body is invalid";
                    break;
                default:
                    messageToDisplay = "An unknown exception has occured";
            }

            model.addAttribute("error", messageToDisplay);

            return "createProduct";
        }

        return "redirect:/product/list";
    }

    @GetMapping({"/list", "/list/"})
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }
}
