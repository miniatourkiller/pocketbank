package ban.pocketbanking.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private int balance; 
private String accNo;
private String duration;
private String discipline;
private String penalty;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getBalance() {
	return balance;
}
public void setBalance(int balance) {
	this.balance = balance;
}
public String getAccNo() {
	return accNo;
}
public void setAccNo(String accNo) {
	this.accNo = accNo;
}
public String getDuration() {
	return duration;
}
public void setDuration(String duration) {
	this.duration = duration;
}
public String getDiscipline() {
	return discipline;
}
public void setDiscipline(String discipline) {
	this.discipline = discipline;
}
public String getPenalty() {
	return penalty;
}
public void setPenalty(String penalty) {
	this.penalty = penalty;
}


}
