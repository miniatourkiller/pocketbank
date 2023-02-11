package ban.pocketbanking.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
private int id;
private String name;
private String email;
private String password;
private String pin;
private String accno;
private int balance;
private String vkey;
private boolean verified;
private String passwordretriever;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getPin() {
	return pin;
}
public void setPin(String pin) {
	this.pin = pin;
}
public String getAccno() {
	return accno;
}
public void setAccno(String accno) {
	this.accno = accno;
}
public int getBalance() {
	return balance;
}
public void setBalance(int balance) {
	this.balance = balance;
}
public String getVkey() {
	return vkey;
}
public void setVkey(String vkey) {
	this.vkey = vkey;
}
public boolean isVerified() {
	return verified;
}
public void setVerified(boolean verified) {
	this.verified = verified;
}
public String getPasswordretriever() {
	return passwordretriever;
}
public void setPasswordretriever(String passwordretriever) {
	this.passwordretriever = passwordretriever;
}

}
