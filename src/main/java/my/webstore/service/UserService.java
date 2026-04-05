package my.webstore.service;

import lombok.RequiredArgsConstructor;
import my.webstore.config.SecurityConfig;
import my.webstore.model.Role;
import my.webstore.model.User;
import my.webstore.repo.UserRepo;
import my.webstore.request.LoginRequest;
import my.webstore.request.PasswordRequest;
import my.webstore.request.RegisterRequest;
import my.webstore.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;
    private final JWTService jwtService;
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(SecurityConfig.getSTRENGTH());


    public void register(RegisterRequest request) {
        // not allowing users with the same email
        repo.findByEmail(request.email()).ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in database");
        });

        if (!validatePassword(request.password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password must be at least 3 characters");
        };

        User user = User.builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(request.email())
                    .password(encoder.encode(request.password()))
                    .role(Role.ROLE_USER)
                    .build();
        repo.save(user);
    }


    public ResponseEntity<String> login(LoginRequest request) {
        User user = repo.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        try {
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            if(auth.isAuthenticated()) {
                return new ResponseEntity<>(jwtService.generateToken(user.getEmail(), user.getId()), HttpStatus.OK);
            }
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);

    }

    public User getAllUserData(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }


    public UserResponse getUser(String email) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return UserResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public int getUserId(String email) {
        return getAllUserData(email).getId();
    }

    public void changePassword(User user, PasswordRequest request) {
        // we compare hashes as oldPassword is stored in DB as a hash
        if(!encoder.matches(request.oldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Old password is incorrect");
        }

        if(encoder.matches(request.newPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is the same");
        };

        if(!validatePassword(request.newPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        };

        // here we only need to compare string values
        if(!request.newPassword().equals(request.repeatPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords don't match");
        }

        user.setPassword(encoder.encode(request.newPassword()));
        repo.save(user);
    }

    private boolean validatePassword(String password) {
        return password.length() >= 3;
    }
}
