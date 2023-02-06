package ban.pocketbanking.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import ban.pocketbanking.dao.AccountDao;
import ban.pocketbanking.entities.Account;
import ban.pocketbanking.utilities.EmailSending;
import ban.pocketbanking.utilities.SecurityEncryption;
import ban.pocketbanking.utilities.SessionUtil;
import ban.pocketbanking.utilities.Time;

public class AccountServices {
	@Autowired
	SessionUtil su;
	@Autowired
	SecurityEncryption se;
	@Autowired
	AccountDao accDao;
	@Autowired
	EmailSending es;
	@Autowired
	Time t;
	public int getBalance(HttpSession session, String pin, Account account) {
		if(session == null) {
			return -1;
		}
		account = accDao.getUserByAccno(su.getSessionArray(session)[2]);
		System.out.println(su.getSessionArray(session)[2]);
		if(se.checkPassPin(account.getPin(), pin)) {
			String message = "Your account balance is ksh"+account.getBalance()+" on "+t.dateTime();
			es.sendSimpleMail(message, account.getEmail());
			return account.getBalance();
		}
		return -1;
	}
}
