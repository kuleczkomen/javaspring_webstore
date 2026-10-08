package my.webstore.service;

import lombok.RequiredArgsConstructor;
import my.webstore.model.User;
import my.webstore.model.UserPrincipal;
import my.webstore.repo.UserRepo;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo repo;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return new UserPrincipal(user);

     }
}
