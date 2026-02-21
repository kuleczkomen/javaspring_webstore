package my.webstore.service;

import lombok.RequiredArgsConstructor;
import my.webstore.model.User;
import my.webstore.model.UserPrincipal;
import my.webstore.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repo.findByEmail(email);

        if(user.isEmpty()) {
            // IO.println("User with email '%s' not found".formatted(email));
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user.get());

     }
}
