package my.webstore.service;

import lombok.AllArgsConstructor;
import my.webstore.config.SecurityConfig;
import my.webstore.model.Role;
import my.webstore.model.User;
import my.webstore.repo.UserRepo;
import my.webstore.request.AdminLoginRequest;
import my.webstore.request.AdminRegisterRequest;
import my.webstore.response.UserResponseForAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final String adminKey;
    private final JWTService jwtService;
    private final AuthenticationManager authManager;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(SecurityConfig.getSTRENGTH());

    // requires manual constructor because of @Value from .env
    public AdminService(@Value("${ADMIN_KEY}") String adminKey, JWTService jwtService, AuthenticationManager authManager, UserRepo userRepo) {
        this.adminKey = adminKey;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userRepo = userRepo;
    }

    public ResponseEntity<String> loginAdmin(@RequestBody AdminLoginRequest request) {
        User user = userRepo.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        try {
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            if(request.adminKey().equals(adminKey) && auth.isAuthenticated()) {
                return new ResponseEntity<>(jwtService.generateToken(user.getEmail(), user.getId()), HttpStatus.OK);
            }
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Bad credentials", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("Bad email or password", HttpStatus.FORBIDDEN);

    }

    public void registerAdmin(@RequestBody AdminRegisterRequest request) {
        userRepo.findByEmail(request.email()).ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already is database");});

        if(!validateAdminPassword(request.password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must contain at least 16 characters, and at least 1 non-alphanumeric");
        }

        if(!request.adminKey().equals(adminKey)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bad credentials");
        }

        User admin = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .role(Role.ROLE_ADMIN)
                .build();
        userRepo.save(admin);
    }

    private boolean validateAdminPassword(String password) {
        if(password.length() < 16) {
            return false;
        } if(password.matches("[a-zA-Z0-9]+")) {
            return false;
        }
        return true;
    }

    public List<UserResponseForAdmin> getUsers() {
        return userRepo.findAll().stream()
                .map(user -> new UserResponseForAdmin(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .collect(Collectors.toList());
    }
}
