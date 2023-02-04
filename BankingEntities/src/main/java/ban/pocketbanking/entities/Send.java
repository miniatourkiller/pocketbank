package ban.pocketbanking.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Send {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private int amount;
private String transactionCode;
private String receiverAccNo;
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
public String getReceiverAccNo() {
	return receiverAccNo;
}
public void setReceiverAccNo(String receiverAccNo) {
	this.receiverAccNo = receiverAccNo;
}

}
