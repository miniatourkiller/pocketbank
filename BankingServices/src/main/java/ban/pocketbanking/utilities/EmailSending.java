package ban.pocketbanking.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailSending {
	@Autowired
	JavaMailSender jvm ;
	
	public void sendSimpleMail(String message, String email) {
		SimpleMailMessage smm = new SimpleMailMessage();
		
		smm.setFrom("jgathiru97@gmail.com");
		smm.setTo(email);
		smm.setSubject("Pocket Banking");
		smm.setText(message);
		jvm.send(smm);
	}
}
