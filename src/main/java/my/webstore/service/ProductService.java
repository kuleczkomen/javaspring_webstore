package my.webstore.service;

import lombok.RequiredArgsConstructor;
import my.webstore.model.Product;
import my.webstore.repo.ProductRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Cacheable(value = "productList", key = "'all'")
    public List<Product> getProducts() {
        List<Product> products = repo.findAll();
        IO.println(products.size());
        return repo.findAll();
    }

    @Cacheable(value = "products", key = "#prodId")
    public Product getProductById(int prodId) {
        return repo.findById(prodId)
                .orElseThrow();
    }

    @CacheEvict(value = "productList", allEntries = true)
    public void addProduct(Product product) {
        repo.save(product);
    }

    @CacheEvict(value = "productList", allEntries = true)
    public void deleteProduct(int prodId) {
        repo.deleteById(prodId);
    }

    @CacheEvict(value = "productList", allEntries = true)
    public void updateProduct(Product product) {
        repo.save(product);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }

}
