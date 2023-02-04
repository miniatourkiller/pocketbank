package ban.pocketbanking.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LoanTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String accno;
private int amount;
private String type;
private String transactionCode;
private String dateTime;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getAccno() {
	return accno;
}
public void setAccno(String accno) {
	this.accno = accno;
}
public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getTransactionCode() {
	return transactionCode;
}
public void setTransactionCode(String transactionCode) {
	this.transactionCode = transactionCode;
}
public String getDateTime() {
	return dateTime;
}
public void setDateTime(String dateTime) {
	this.dateTime = dateTime;
}

}
