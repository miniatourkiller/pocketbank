package ban.pocketbanking.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ban.pocketbanking.entities.LoginDetails;
import ban.pocketbanking.services.Login;
import ban.pocketbanking.services.Register;

@Configuration
public class config {
@Bean
Register setRegisterAccount() {
	return new Register();
}
@Bean
Login setLogin() {
	return new Login();
}
@Bean
LoginDetails setLoginD() {
	return new LoginDetails();
}
}
