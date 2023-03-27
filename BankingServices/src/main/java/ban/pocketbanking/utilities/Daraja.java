package ban.pocketbanking.utilities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ban.pocketbanking.essential.C2B;
import ban.pocketbanking.essential.C2Bresponse;
import ban.pocketbanking.essential.Token;


public class Daraja {
    String json;
    @Autowired
    C2Bresponse res;
@Autowired
    Token t;

    String token = "";
    Boolean timeSet = false;
    LocalDateTime now;
    LocalDateTime then;

   

    public Token token(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.set("Authorization", "Basic cFJZcjZ6anEwaThMMXp6d1FETUxwWkIzeVBDa2hNc2M6UmYyMkJmWm9nMHFRR2xWOQ");
        HttpEntity<String> req = new HttpEntity<>(header);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials", 
                HttpMethod.GET,req,  String.class);

                ObjectMapper obj = new ObjectMapper();
        
                try{
                    t = obj.readValue(response.getBody(), Token.class);
                }catch(Exception e){
                    System.out.println(e);
                }
return t;
    }

 public void setToken(){

    if(then == null){
        t = token();
        token = t.getAccess_token();
        
        then =now.plusMinutes((Integer.valueOf(t.getExpires_in())/60)-5);
    }else if(then != null){
        int mins = (int) ChronoUnit.MINUTES.between(now, then);
        if(mins <=0){
            then = null;
            setToken();
        }
    }

 }


    public C2Bresponse testApiC2B(C2B c2b){
        now = LocalDateTime.now();
            setToken();
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+token);
ObjectMapper objMapper = new ObjectMapper();
try{
   json = objMapper.writeValueAsString(c2b);
}catch(Exception e){
    System.out.println(e);
}

        HttpEntity<String> reqEntity = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate", HttpMethod.POST, reqEntity,String.class);

        if(response.getStatusCode() == HttpStatus.OK){
            ObjectMapper obj = new ObjectMapper();

            try {
                res = obj.readValue(String.valueOf(response.getBody()), C2Bresponse.class);
            } catch (Exception e) {

            }
            return res;
        }else{
            return null;
        }
        
    }
}
