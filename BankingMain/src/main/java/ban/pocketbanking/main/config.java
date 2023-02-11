package ban.pocketbanking.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ban.pocketbanking.essential.BalanceCheck;
import ban.pocketbanking.essential.DepositDetails;
import ban.pocketbanking.essential.EmailContent;
import ban.pocketbanking.essential.LoanDetails;
import ban.pocketbanking.essential.LoginDetails;
import ban.pocketbanking.essential.NewPassword;
import ban.pocketbanking.essential.PinContent;
import ban.pocketbanking.essential.SavingDetails;
import ban.pocketbanking.essential.SendDetails;
import ban.pocketbanking.essential.WithdrawDetails;
import ban.pocketbanking.services.AccountMaintainance;
import ban.pocketbanking.services.AccountServices;
import ban.pocketbanking.services.AgentServices;
import ban.pocketbanking.services.Login;
import ban.pocketbanking.services.Register;
import ban.pocketbanking.utilities.EmailSending;

@Configuration
public class config {
	@Bean
	EmailContent setEmailContent() {
		return new EmailContent();
	}
@Bean
Register setRegisterAccount() {
	return new Register();
}
@Bean
PinContent setPinContent() {
	return new PinContent();
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
@Bean
SendDetails setSendDetails() {
	return new SendDetails();
}
@Bean
WithdrawDetails setWithdrawDetails() {
	return new WithdrawDetails();
}
@Bean
SavingDetails setSavingDetails() {
	return new SavingDetails();
}
@Bean
LoanDetails setLoanDetails() {
	return new LoanDetails();
}
@Bean
AccountMaintainance setAccountMaintainance() {
	return new AccountMaintainance();
}
@Bean
NewPassword setNewPassword() {
	return new NewPassword();
}
}
