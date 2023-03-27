package ban.pocketbanking.essential;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class C2B {
    private int ShortCode;
    private String CommandID;
    private String amount;
    private String MSISDN;
    private String BillRefNumber;
   
    @JsonProperty("ShortCode")
    public int getShortCode() {
		return ShortCode;
	}
	public void setShortCode(int ShortCode) {
		this.ShortCode = ShortCode;
	}
    
    @JsonProperty("CommandID")
	public String getCommandID() {
        return CommandID;
    }
    public void setCommandID(String CommandID) {
        this.CommandID = CommandID;
    }
    
    @JsonProperty("Amount")
    public String getAmount() {
        return  amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    @JsonProperty("Msisdn")
    public String getMSISDN() {
        return MSISDN;
    }
    public void setMsisdn(String MSISDN) {
        this.MSISDN = MSISDN;
    }
    @JsonProperty("BillRefNumber")
    public String getBillRefNumber() {
        return BillRefNumber;
    }
    public void setBillRefNumber(String BillRefNumber) {
        this.BillRefNumber = BillRefNumber;
    }
    @Override
    public String toString() {
        return "C2B [ShortCode=" + ShortCode + ", CommandID=" + CommandID + ", amount=" + amount + ", MSISDN=" + MSISDN
                + ", BillRefNumber=" + BillRefNumber + "]";
    }

}
