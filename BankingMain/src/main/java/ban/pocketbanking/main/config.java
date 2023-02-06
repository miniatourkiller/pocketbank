package ban.pocketbanking.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ban.pocketbanking.essential.BalanceCheck;
import ban.pocketbanking.essential.DepositDetails;
import ban.pocketbanking.essential.LoginDetails;
import ban.pocketbanking.services.AccountServices;
import ban.pocketbanking.services.AgentServices;
import ban.pocketbanking.services.Login;
import ban.pocketbanking.services.Register;
import ban.pocketbanking.utilities.EmailSending;

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
@Bean 
DepositDetails setDeets() {
	return new DepositDetails();
}
@Bean 
BalanceCheck setBal() {
	return new BalanceCheck();
}
@Bean
AgentServices setThem() {
	return new  AgentServices();
}
@Bean
AccountServices setAccountServices() {
	return new  AccountServices();
}
@Bean
EmailSending setEmailSending() {
	return new EmailSending();
}
}
