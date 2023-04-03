package ban.pocketbanking.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ban.pocketbanking.dao.AccountDao;
import ban.pocketbanking.dao.MpesaTransactionDao;
import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.MpesaDarajaResult;
import ban.pocketbanking.essential.C2B;
import ban.pocketbanking.essential.C2Bresponse;
import ban.pocketbanking.essential.RegURLDetails;
import ban.pocketbanking.utilities.Daraja;
import ban.pocketbanking.utilities.EmailSending;
import ban.pocketbanking.utilities.SessionUtil;

@Service
public class MpesaPayments {

    @Autowired
    AccountDao accDao;

    @Autowired
    MpesaTransactionDao mpesaDao;

    @Autowired
    EmailSending es;

    @Autowired
    SessionUtil su;

    public String depositConfirmationMpesa(MpesaDarajaResult mdr, Account acc){
       acc = accDao.getAccountUser(mdr.getBillRefNumber());
       acc.setBalance(acc.getBalance()+Integer.valueOf(mdr.getTransAmount()));
       accDao.save(acc);
       mpesaDao.save(mdr);
       String accountmessage = mdr.getTransID()+" Confirmed on " + mdr.getTransTime() + " you deposited ksh" +
              mdr.getTransAmount() + " from mpesa. Your new Balance is ksh" + acc.getBalance();

        es.sendSimpleMail(accountmessage, acc.getEmail());
       return "done";
    }

    public void validation(){
        System.out.println("Validated");
    }
    @Autowired
    Daraja daraja;
    public C2Bresponse mpesaDeposit(C2B c2b, HttpSession session){
        if (!su.checkSession(session)) {
            return null;
        }
        c2b.setBillRefNumber(su.getSessionArray(session)[2]);
        c2b.setShortCode(600984);
        c2b.setCommandID("CustomerPayBillOnline");
        return daraja.testApiC2B(c2b);
    }

    public ResponseEntity<String> regURLs(RegURLDetails reg){
        return daraja.registerUrl(reg);
    }
    
}
