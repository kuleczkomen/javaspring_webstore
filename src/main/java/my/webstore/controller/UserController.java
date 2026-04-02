package my.webstore.controller;

import lombok.RequiredArgsConstructor;
import my.webstore.model.User;
import my.webstore.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/")
    public List<User> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/me")
    public User getCurrentUser(Integer id) {
        return service.getCurrentUser(id);
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return service.verify(user);
    }
}
