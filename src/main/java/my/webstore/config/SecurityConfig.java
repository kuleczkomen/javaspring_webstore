package my.webstore.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import my.webstore.filter.JwtFilter;
import my.webstore.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // this is a config file
@EnableWebSecurity // this config overrides default spring config
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;
    @Getter
    private static final int STRENGTH = 10;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                // csrf is not saved -> logging in another browser requires new authentication
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(r -> r
                    .requestMatchers("/error").permitAll()
                    .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                        //to change
                    .requestMatchers("/api/admin/login", "/api/admin/register").permitAll()
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")
                    .anyRequest().authenticated())
                    // login form
                    // .httpBasic(Customizer.withDefaults())
                // http is stateless
                .sessionManagement(s -> s
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // using jwt filter before user and password filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(STRENGTH));
        return provider;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }
}
