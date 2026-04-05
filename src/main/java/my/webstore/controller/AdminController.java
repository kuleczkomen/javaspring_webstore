package my.webstore.controller;

import lombok.AllArgsConstructor;
import my.webstore.model.Order;
import my.webstore.model.Product;
import my.webstore.http.request.AdminLoginRequest;
import my.webstore.http.request.AdminRegisterRequest;
import my.webstore.http.response.UserResponseForAdmin;
import my.webstore.service.AdminService;
import my.webstore.service.OrdersService;
import my.webstore.service.ProductService;
import my.webstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService service;
    private final UserService userService;
    private final OrdersService ordersService;
    private final ProductService productService;


    /* login and register */
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminLoginRequest request) {
        return service.loginAdmin(request);
    }

    @PostMapping("/register")
    public void registerAdmin(@RequestBody AdminRegisterRequest request) {
        service.registerAdmin(request);
    }

    /* users */
    @GetMapping("/users")
    public List<UserResponseForAdmin> getUsers() {
        return service.getUsers();
    }

    /* orders */
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return new ResponseEntity<>(ordersService.getOrders(), HttpStatus.OK);
    }

    /* products - all non-GET methods */

    @PostMapping("/products")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @DeleteMapping("/products/{prodId}")
    public void deleteProduct(@PathVariable int prodId) {
        productService.deleteProduct(prodId);
    }

    @PutMapping("/products")
    public void updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
    }


}
