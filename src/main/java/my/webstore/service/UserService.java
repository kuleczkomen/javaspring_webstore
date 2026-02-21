package my.webstore.service;

import lombok.RequiredArgsConstructor;
import my.webstore.model.User;
import my.webstore.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;

    public List<User> getUsers() {
        return repo.findAll();
    }

    public void addUser(User user) {
        // not allowing users with the same email
        repo.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already in database"
            );
        });

        repo.save(user);
    }


}
