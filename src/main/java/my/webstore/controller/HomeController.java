package my.webstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String hello(HttpServletRequest request) {
        return "Welcome to the website %s".formatted(request.getSession().getId());
    }
}
