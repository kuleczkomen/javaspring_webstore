package my.webstore.controller;

import lombok.RequiredArgsConstructor;
import my.webstore.model.User;
import my.webstore.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/users")
    public List<User> getUsers() {
        return service.getUsers();
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        service.addUser(user);
    }
}
