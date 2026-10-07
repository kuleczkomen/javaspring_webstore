package my.webstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebstoreApplication {

	static void main(String[] args) {
		SpringApplication.run(WebstoreApplication.class, args);
	}

}
