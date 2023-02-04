package ban.pocketbanking.utilities;

import java.util.ArrayList;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.AtmAgent;

@Component
public class EmailVerification {
	@Autowired
	JavaMailSender jms;
	ArrayList<Object> list = new ArrayList<>();
public String getEntity(String url, String apikey, String email) {
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	HttpEntity<String> requestEntity = new HttpEntity<>(headers);
	
	RestTemplate rest = new RestTemplate();
	
	ResponseEntity<String> responseEntity = rest.exchange(""+url+""+apikey+"&email="+email+"",HttpMethod.GET,requestEntity, String.class);
return String.valueOf(responseEntity.getBody());
}


public boolean realEmail(String url, String apiKey, String email){
String arr = getEntity(url,apiKey,email);
	String[] obj = arr.split(",", 11);
	String[] del = obj[2].split(":",2);
	
	String text = (String)del[1];
if(text.equals("\"DELIVERABLE\"") ) {
	return true;
     }
  return false;
 }

public void verification(Account acc) {
	try {
		MimeMessage message = jms.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(message, "utf-8");
		String msg = "<p>Click here to <a href='http://localhost:8089/verify/"+acc.getVkey()+"'>verify</a> it is you</p>";
		mmh.setTo(acc.getEmail());
		mmh.setFrom("jgathiru97@gmail.com");
		mmh.setSubject("Email Verification");
		mmh.setText(msg,true);
		jms.send(message);
	}catch(Exception e) {
		System.out.println(e);
	}
}
public void verification(AtmAgent agent) {
	try {
		MimeMessage message = jms.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(message, "utf-8");
		String msg = "<p>Click here to <a href='http://localhost:8089/verifyagent/"+agent.getVkey()+"'>verify</a> it is you</p>";
		mmh.setTo(agent.getEmail());
		mmh.setFrom("jgathiru97@gmail.com");
		mmh.setSubject("Email Verification");
		mmh.setText(msg,true);
		jms.send(message);
	}catch(Exception e) {
		System.out.println(e);
	}
}
}
