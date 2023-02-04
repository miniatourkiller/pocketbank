package ban.pocketbanking.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Deposit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private int amount;
private String atmAgentNo;
private String transactionCode;
private String accno;
private String dateTime;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
public String getAtmAgentNo() {
	return atmAgentNo;
}
public void setAtmAgentNo(String atmAgentNo) {
	this.atmAgentNo = atmAgentNo;
}
public String getTransactionCode() {
	return transactionCode;
}
public void setTransactionCode(String transactionCode) {
	this.transactionCode = transactionCode;
}
public String getAccno() {
	return accno;
}
public void setAccno(String accno) {
	this.accno = accno;
}
public String getDateTime() {
	return dateTime;
}
public void setDateTime(String dateTime) {
	this.dateTime = dateTime;
}


}
