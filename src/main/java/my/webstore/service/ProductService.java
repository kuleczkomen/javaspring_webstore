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
        return repo.findAll();
    }

    public Product getProductById() {
        return repo.findAllById().getFirst();
    }
}
