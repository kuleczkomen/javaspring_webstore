package my.webstore.service;

import lombok.RequiredArgsConstructor;
import my.webstore.model.Order;
import my.webstore.repo.OrdersRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepo repo;

    public List<Order> getOrdersByUserId(int userId) {
        return Collections.singletonList(repo.findById(userId)
                .orElse(new Order()));
    }

    public List<Order> getOrders() {
        return repo.findAll();
    }
}
