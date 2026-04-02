package my.webstore.service;

import lombok.RequiredArgsConstructor;
import my.webstore.config.SecurityConfig;
import my.webstore.model.User;
import my.webstore.repo.UserRepo;
import my.webstore.request.PasswordRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
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

    public List<User> getUsers() {
        return repo.findAll();
    }

    public void register(User user) {
        // not allowing users with the same email
        repo.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already in database"
            );
        });

        if (!validatePassword(user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password must be at least 3 characters"
            );
        };

        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
    }


    public String verify(User user) {
        Authentication auth =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(auth.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail(), user.getId());
        }
        return "Failed to log in!";
    }

    public User getUser(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public int getUserId(String email) {
        return getUser(email).getId();
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
