package my.webstore.controller;

import lombok.RequiredArgsConstructor;
import my.webstore.model.Order;
import my.webstore.http.request.OrderRequest;
import my.webstore.service.OrdersService;
import my.webstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin // avoid CORS error
@RequestMapping("/api/orders")
@RequiredArgsConstructor // constructor injection
public class OrderController {

    private final OrdersService service;
    private final UserService userService;

    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getUsersOrders(@AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(service.getOrdersByUserId(userDetails.getUsername()), HttpStatus.OK);
    }

    @PostMapping("/")
    public void addOrder(@AuthenticationPrincipal UserDetails userDetails, @RequestBody OrderRequest request) {
        service.addOrder(userDetails.getUsername(), request);
    }

//    @PutMapping("/")
//    public void updateOrderStatus(@RequestBody )

}
