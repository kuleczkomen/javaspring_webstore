package my.webstore.controller;

import lombok.RequiredArgsConstructor;
import my.webstore.model.User;
import my.webstore.repo.UserRepo;
import my.webstore.request.LoginRequest;
import my.webstore.request.PasswordRequest;
import my.webstore.request.RegisterRequest;
import my.webstore.response.UserResponse;
import my.webstore.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/me")
    public UserResponse getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return service.getUser(userDetails.getUsername());
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) {
        service.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PutMapping("/change-password")
    public void changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PasswordRequest request) {
        service.changePassword(service.getAllUserData(userDetails.getUsername()), request);
    }
}
