package my.webstore.service;

import lombok.RequiredArgsConstructor;
import my.webstore.model.User;
import my.webstore.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;

    public List<User> getUsers() {
        return repo.findAll();
    }

    public void addUser(User user) {
        IO.println(repo.findByEmail(user.getEmail()));
        if(repo.findByEmail(user.getEmail()) != null) {
            IO.println("Email already exists");
        } else {
            repo.save(user);
        }
    }


}
