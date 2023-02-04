package ban.pocketbanking.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Savings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String savingAccName;
private int balance;
private String choice;
private int target;
private String duration;
private String accNo;
private String savingsAccNo;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getSavingAccName() {
	return savingAccName;
}
public void setSavingAccName(String savingAccName) {
	this.savingAccName = savingAccName;
}
public int getBalance() {
	return balance;
}
public void setBalance(int balance) {
	this.balance = balance;
}
public String getDuration() {
	return duration;
}
public void setDuration(String duration) {
	this.duration = duration;
}
public String getAccNo() {
	return accNo;
}
public void setAccNo(String accNo) {
	this.accNo = accNo;
}
public String getSavingsAccNo() {
	return savingsAccNo;
}
public void setSavingsAccNo(String savingsAccNo) {
	this.savingsAccNo = savingsAccNo;
}
public String getChoice() {
	return choice;
}
public void setChoice(String choice) {
	this.choice = choice;
}
public int getTarget() {
	return target;
}
public void setTarget(int target) {
	this.target = target;
}

}
