package my.webstore.service;

import lombok.RequiredArgsConstructor;
import my.webstore.model.Product;
import my.webstore.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    /*
        this is constructor injection
        because @RequiredArgsConstructor makes a setter
        for ProductRepo repo
     */

    private final ProductRepo repo;

    public List<Product> getProducts() {
        List<Product> products = repo.findAll();
        IO.println(products.size());
        return repo.findAll();
    }

    public Product getProductById(int prodId) {
        return repo.findById(prodId)
                .orElse(new Product()); // give status code?
    }

    public void addProduct(Product product) {
        repo.save(product);
    }

    public void deleteProduct(int prodId) {
        repo.deleteById(prodId);
    }

    public void updateProduct(Product product) {
        repo.save(product);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }

}
