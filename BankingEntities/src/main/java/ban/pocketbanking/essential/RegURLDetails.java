package ban.pocketbanking.essential;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegURLDetails {
    private int ShortCode;
    private String ResponseType;
    private String ConfirmationURL;
    private String ValidationURL;

    @JsonProperty("ShortCode")
    public int getShortCode() {
        return ShortCode;
    }
    public void setShortCode(int ShortCode) {
        this.ShortCode = ShortCode;
    }
    
    @JsonProperty("ResponseType")
    public String getResponseType() {
        return ResponseType;
    }
    public void setResponseType(String ResponseType) {
        this.ResponseType = ResponseType;
    }
    
    @JsonProperty("ConfirmationURL")
    public String getConfirmationURL() {
        return ConfirmationURL;
    }
    public void setConfirmationURL(String ConfirmationURL) {
        this.ConfirmationURL = ConfirmationURL;
    }
    
    @JsonProperty("ValidationURL")
    public String getValidationURL() {
        return ValidationURL;
    }
    public void setValidationURL(String ValidationURL) {
        this.ValidationURL = ValidationURL;
    }
    
}
