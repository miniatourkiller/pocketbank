package ban.pocketbanking.services;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ban.pocketbanking.dao.AccountDao;
import ban.pocketbanking.dao.SavingsDao;
import ban.pocketbanking.dao.SavingsTransactionDao;
import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.Savings;
import ban.pocketbanking.entities.SavingsTransaction;
import ban.pocketbanking.essential.SavingDetails;
import ban.pocketbanking.utilities.EmailSending;
import ban.pocketbanking.utilities.Generator;
import ban.pocketbanking.utilities.SecurityEncryption;
import ban.pocketbanking.utilities.SessionUtil;
import ban.pocketbanking.utilities.Time;

@Service
public class SavingsServices {
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
	@Autowired
	Generator gen;
	@Autowired
	SavingsDao sDao;
	@Autowired
	SavingsTransactionDao stDao;
	public String createSavings(HttpSession session, Savings s, Savings s2) {
		if(!su.checkSession(session)) {
			return "expired";
		}
		s2 = sDao.getBysavingAccName(s.getSavingAccName());
		if(s2!=null) {
			return "exists";
		}else {
			s.setBalance(0);
		s.setSavingsAccNo(s.getSavingAccName()+su.getSessionArray(session)[2]);
		s.setSavingsAccNo(su.getSessionArray(session)[2]);
		sDao.save(s);
		String message = "You have successfully created a savings account by the name "+s.getSavingAccName()+".";
		es.sendSimpleMail(message, su.getSessionArray(session)[1]);
		return "done";
		}
		
	}
	
	public String destroySavingAccount(	HttpSession session, String savingsAccNo, Savings s) {
		if(!su.checkSession(session)) {
			return "expired";
		}
		s = sDao.getBysavingsAccNo(savingsAccNo);
		if(s.getBalance()>0) {
			return "invalid";
		}else {
			String message = "You have successfully removed the "+s.getSavingAccName()+" account";
			es.sendSimpleMail(message, su.getSessionArray(session)[1]);
			sDao.delete(s);
			
			return "done";
		}
	}
	
	public ArrayList<Savings> getAll(HttpSession session){
		if(!su.checkSession(session)) {
			return null;
		}
	ArrayList<Savings> s= sDao.getAllSavingsAccounts(su.getSessionArray(session)[2]);
	if(!s.isEmpty()){
		return s;
	}
	else{
		return null;
	}
	}
	
	public int totalSavings(HttpSession session, ArrayList<Savings> savings) {
		int total = 0;
		savings = getAll(session);
		if(savings == null) {
			return total;
		}
		for( Savings s: savings) {
			total =total+ s.getBalance();
		}
		return total;
	}
	
	public String deposit(SavingDetails sd, HttpSession session, Account acc, Savings s, SavingsTransaction st) {
		if(!su.checkSession(session)) {
			return "expired";
		}
		acc = accDao.getAccountUser(su.getSessionArray(session)[2]);
		if(!se.checkPassPin(acc.getPin(), sd.getPin())) {
			return "wrong pin";
		}
		if(acc.getBalance()<sd.getAmount()) {
			return "insufficient";
		}else {
			s = sDao.getBysavingsAccNo(sd.getSavingsAccNo());
			s.setBalance(s.getBalance()+sd.getAmount());
			acc.setBalance(acc.getBalance()-sd.getAmount());
			accDao.save(acc);
			sDao.save(s);
			st.setAmount(sd.getAmount());
			st.setDateTime(t.dateTime());
			st.setSavingsAccNo(sd.getSavingsAccNo());
			st.setTransactionCode(gen.generateTransactionCode());
			st.setType("deposit");
			stDao.save(st);
			
			String message = ""+st.getTransactionCode()+" Confirmed on " +t.dateTime()+" you have deposited ksh"+sd.getAmount()+" in your "+s.getSavingAccName()+
					" savings account";
			es.sendSimpleMail(message, acc.getEmail());
			return "done";
		}
	}
	
	public String withdraw(HttpSession session, SavingDetails sd, Account acc, Savings s, SavingsTransaction st) {
		if(!su.checkSession(session)) {
			return "expired";
		}
		acc = accDao.getAccountUser(su.getSessionArray(session)[2]);
		s = sDao.getBysavingsAccNo(sd.getSavingsAccNo());
		if(!se.checkPassPin(acc.getPin(), sd.getPin())) {
			return "wrong pin";
		}else {
			if(s.getChoice() == 0) {
				if(s.getTarget() >= s.getBalance()) {
					//can withdraw
					acc.setBalance(acc.getBalance()+sd.getAmount());
					s.setBalance(s.getBalance()-sd.getAmount());
					accDao.save(acc);
					sDao.save(s);
					st.setAmount(sd.getAmount());
					st.setDateTime(t.dateTime());
					st.setSavingsAccNo(sd.getSavingsAccNo());
					st.setTransactionCode(gen.generateTransactionCode());
					st.setType("deposit");
					stDao.save(st);
					
					String message = ""+st.getTransactionCode()+" Confirmed on "+t.dateTime()+
							"you withdrew ksh"+sd.getAmount()+ " from your "+s.getSavingAccName()+
							" savinga account. Your new savings account balance is ksh"+s.getBalance()+
							" and new account balance is ksh"+acc.getBalance();
					es.sendSimpleMail(message, acc.getEmail());
					return "done";
				}else {
					return "target error";
				}
			}else if(s.getChoice() == 1) {
				if(t.differenceInDays(s.getDuration()) <= 0) {
					//can withdraw
					acc.setBalance(acc.getBalance()+sd.getAmount());
					s.setBalance(s.getBalance()-sd.getAmount());
					accDao.save(acc);
					sDao.save(s);
					st.setAmount(sd.getAmount());
					st.setDateTime(t.dateTime());
					st.setSavingsAccNo(sd.getSavingsAccNo());
					st.setTransactionCode(gen.generateTransactionCode());
					st.setType("deposit");
					stDao.save(st);
					
					String message = ""+st.getTransactionCode()+" Confirmed on "+t.dateTime()+
							"you withdrew ksh"+sd.getAmount()+ " from your "+s.getSavingAccName()+
							" savinga account. Your new savings account balance is ksh"+s.getBalance()+
							" and new account balance is ksh"+acc.getBalance();
					es.sendSimpleMail(message, acc.getEmail());
					return "done";
				}else {
					return "duration error";
				}
			}
		}
		return null;
	}
}
