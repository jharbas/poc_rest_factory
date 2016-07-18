package poc.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Jharbas Araujo
 */
@SpringBootApplication
@ComponentScan(basePackages = { "poc.backend" })
public class PocMain {

	public static void main(String[] args) {
		SpringApplication.run(PocMain.class, args);
	}

}