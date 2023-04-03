package ban.pocketbanking.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class},scanBasePackages = {"ban.pocketbanking.*"})
@EntityScan(basePackages = {"ban.pocketbanking.*"})
@ComponentScan({"ban.pocketbanking.*"})    
@EnableJpaRepositories({"ban.pocketbanking.*"}) 
public class BankingApplication {
public static void main(String[] args) {
	SpringApplication.run(BankingApplication.class,args);
}
}