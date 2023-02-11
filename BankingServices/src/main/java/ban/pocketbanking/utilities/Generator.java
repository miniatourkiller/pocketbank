package ban.pocketbanking.utilities;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Generator {
	String arr = "";
	Random random = new Random();
public String generateTransactionCode() {
	String code ="";
	arr="1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	for(int i=0; i<10; i++) {
		int no = random.nextInt(arr.length());
		code+=arr.charAt(no);
	}
	return code;
}
public String generatevkey() {
	String code ="";
	arr="1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	for(int i=0; i<16; i++) {
		int no = random.nextInt(arr.length());
		code+=arr.charAt(no);
	}
	return code;
}
public String generateAccNo() {
	String code = "";
	arr = "1234567890";
	for(int i=0; i<15; i++) {
		int no = random.nextInt(arr.length());
		code+=arr.charAt(no);
	}

	return code;
}
public String generateAgentNo() {
	String code = "";
	arr = "1234567890";
	for(int i=0; i<6; i++) {
		int no = random.nextInt(arr.length());
		code+=arr.charAt(no);
	}

	return code;
}
public String generatepasswordretriever() {
	String code = "";
	arr="1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	for(int i=0; i<6; i++) {
		int no = random.nextInt(arr.length());
		code+=arr.charAt(no);
	}

	return code;
}
}
