package my.webstore.controller;

import lombok.RequiredArgsConstructor;
import my.webstore.model.User;
import my.webstore.request.PasswordRequest;
import my.webstore.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public User getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return service.getUser(userDetails.getUsername());
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return service.verify(user);
    }

    @PutMapping("/change-password")
    public void changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PasswordRequest request) {
        service.changePassword(service.getUser(userDetails.getUsername()), request);
    }
}
