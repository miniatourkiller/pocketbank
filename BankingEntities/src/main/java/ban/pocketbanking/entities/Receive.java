package ban.pocketbanking.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Receive {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private int amount;
private String transactionCode;
private String senderAccNo;
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
public String getTransactionCode() {
	return transactionCode;
}
public void setTransactionCode(String transactionCode) {
	this.transactionCode = transactionCode;
}
public String getSenderAccNo() {
	return senderAccNo;
}
public void setSenderAccNo(String senderAccNo) {
	this.senderAccNo = senderAccNo;
}
public String getDateTime() {
	return dateTime;
}
public void setDateTime(String dateTime) {
	this.dateTime = dateTime;
}

}
