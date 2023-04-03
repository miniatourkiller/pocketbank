package ban.pocketbanking.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MpesaDarajaResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String TransactionType;
    private String TransID;
    private String TransTime;
    private String TransAmount;
    private String BusinessShortCode;
    private String BillRefNumber;
    private String InvoiceNumber;
    private String OrgAccountBalance;
    private String ThirdPartyTransID;
    private String MSISDN;
    private String FirstName;
    private String MiddleName;
    private String LastName;

    public String getTransactionType() {
        return TransactionType;
    }
    public void setTransactionType(String TransactionType) {
        this.TransactionType = TransactionType;
    }
    public String getTransID() {
        return TransID;
    }
    public void setTransID(String TransID) {
        this.TransID = TransID;
    }
    public String getTransTime() {
        return TransTime;
    }
    public void setTransTime(String TransTime) {
        this.TransTime = TransTime;
    }
    public String getTransAmount() {
        return TransAmount;
    }
    public void setTransAmount(String TransAmount) {
        this.TransAmount = TransAmount;
    }
    public String getBusinessShortCode() {
        return BusinessShortCode;
    }
    public void setBusinessShortCode(String BusinessShortCode) {
        this.BusinessShortCode = BusinessShortCode;
    }
    public String getBillRefNumber() {
        return BillRefNumber;
    }
    public void setBillRefNumber(String BillRefNumber) {
        this.BillRefNumber = BillRefNumber;
    }
    public String getInvoiceNumber() {
        return InvoiceNumber;
    }
    public void setInvoiceNumber(String InvoiceNumber) {
        this.InvoiceNumber = InvoiceNumber;
    }
    public String getOrgAccountBalance() {
        return OrgAccountBalance;
    }
    public void setOrgAccountBalance(String OrgAccountBalance) {
        this.OrgAccountBalance = OrgAccountBalance;
    }
    public String getThirdPartyTransID() {
        return ThirdPartyTransID;
    }
    public void setThirdPartyTransID(String ThirdPartyTransID) {
        this.ThirdPartyTransID = ThirdPartyTransID;
    }
    public String getMSISDN() {
        return MSISDN;
    }
    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }
    public String getFirstName() {
        return FirstName;
    }
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }
    public String getMiddleName() {
        return MiddleName;
    }
    public void setMiddleName(String MiddleName) {
        this.MiddleName = MiddleName;
    }
    public String getLastName() {
        return LastName;
    }
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    
}
