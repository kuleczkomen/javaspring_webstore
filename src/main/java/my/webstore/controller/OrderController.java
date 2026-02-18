package my.webstore.controller;

import lombok.RequiredArgsConstructor;
import my.webstore.model.Order;
import my.webstore.service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin // avoid CORS error
@RequestMapping("/api/orders")
@RequiredArgsConstructor // constructor injection
public class OrderController {

    private final OrdersService service;

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable int userId) {
        return new ResponseEntity<>(service.getOrdersByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Order>> getOrders() {
        return new ResponseEntity<>(service.getOrders(), HttpStatus.OK);
    }


}
