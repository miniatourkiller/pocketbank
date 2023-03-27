package ban.pocketbanking.essential;

import com.fasterxml.jackson.annotation.JsonProperty;

public class C2Bresponse {
    private String OriginatorCoversationID;
    private String ResponseCode;
    private String ResponseDescription;

    @JsonProperty("OriginatorCoversationID")
    public String getOriginatorCoversationID() {
        return OriginatorCoversationID;
    }
    public void setOriginatorCoversationID(String originatorCoversationID) {
        OriginatorCoversationID = originatorCoversationID;
    }
    
    @JsonProperty("ResponseCode")
    public String getResponseCode() {
        return ResponseCode;
    }
    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }
    
    @JsonProperty("ResponseDescription")
    public String getResponseDescription() {
        return ResponseDescription;
    }
    public void setResponseDescription(String responseDescription) {
        ResponseDescription = responseDescription;
    }
    
}
