package my.webstore.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import my.webstore.service.MyUserDetailsService;
import my.webstore.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // this is a config file
@EnableWebSecurity // this config overrides default spring config
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;
    @Getter
    private static final int strength = 10;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        return http
                // csrf is not saved -> logging in another browser requires new authentication
                .csrf(csrf -> csrf.disable())
                // every http request must be authenticated
                .authorizeHttpRequests(r -> r.anyRequest().authenticated())
                // login form
                .httpBasic(Customizer.withDefaults())
                // http is stateless
                .sessionManagement(s -> s
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();


    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(strength));
        return provider;
    }
}
