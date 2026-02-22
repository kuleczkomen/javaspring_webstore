package my.webstore.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import my.webstore.config.SecurityConfig;
import my.webstore.model.User;
import my.webstore.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(SecurityConfig.getStrength());

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

        if(user.getPassword().length() < 6) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_CONTENT,
                    "Password must be at least 6 characters"
            );
        };

        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
    }


}
