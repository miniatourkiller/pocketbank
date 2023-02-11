package ban.pocketbanking.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AtmAgent {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
private int id;
private String agentNo;
private String owner;
private String email;
private String location;
private String password;
private String pin;
private String vkey;
private int floatbalance;
private boolean verified;
private String passwordretriever;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public String getOwner() {
	return owner;
}
public void setOwner(String owner) {
	this.owner = owner;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
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
public String getAgentNo() {
	return agentNo;
}
public void setAgentNo(String agentNo) {
	this.agentNo = agentNo;
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
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public int getFloatbalance() {
	return floatbalance;
}
public void setFloatbalance(int floatbalance) {
	this.floatbalance = floatbalance;
}
public String getPasswordretriever() {
	return passwordretriever;
}
public void setPasswordretriever(String passwordretriever) {
	this.passwordretriever = passwordretriever;
}

}
