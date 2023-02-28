package ban.pocketbanking.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import ban.pocketbanking.dao.AccountDao;
import ban.pocketbanking.dao.AtmAgentDao;
import ban.pocketbanking.dao.TransactionsDao;
import ban.pocketbanking.dao.WithdrawDao;
import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.AtmAgent;
import ban.pocketbanking.entities.Transactions;
import ban.pocketbanking.entities.Withdraw;
import ban.pocketbanking.essential.PinContent;
import ban.pocketbanking.essential.SendDetails;
import ban.pocketbanking.essential.WithdrawDetails;
import ban.pocketbanking.utilities.EmailSending;
import ban.pocketbanking.utilities.Generator;
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
	@Autowired
	TransactionsDao tDao;
	@Autowired
	Generator gen;
	@Autowired
	AtmAgentDao agtDao;
	@Autowired
	WithdrawDao wDao;
	
	
	public boolean checkPin(HttpSession session, Account acc, PinContent pc) {
		if(!su.checkSession(session)) {
			return false;
		}
		acc = accDao.getUserByAccno(su.getSessionArray(session)[2]);
		if(se.checkPassPin(acc.getPin(), pc.getPin())) {
			return true;
		}
		return false;
	}
	
	public Account account(HttpSession session) {
		return accDao.getAccountUser(su.getSessionArray(session)[2]);
	}
	
	public int getBalance(HttpSession session, String pin, Account account) {
		if(!su.checkSession(session)) {
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
	public String accountSuggestion(String accno, HttpSession session, Account acc) {
		if(!su.checkSession(session)) {
			return "expired";
		}else {
			acc = accDao.getAccountUser(accno);
			if(acc == null) {
				return "no user";
			}
			return acc.getName();
		}
	}
	public String send(SendDetails sd, Account acc,Account acc1, HttpSession session, Transactions tr) {
		if(!su.checkSession(session)) {
			acc = accDao.getAccountUser(su.getSessionArray(session)[1]);
			if(!se.checkPassPin(acc.getPin(), sd.getPin())) {
				return "wrong pin";
			}
			if(sd.getAmount()>acc.getBalance()) {
				return "insufficient";
			}else {
				acc1 = accDao.getAccountUser(sd.getAccno());
				acc.setBalance(acc.getBalance()-sd.getAmount());
				acc1.setBalance(acc1.getBalance()+sd.getAmount());
				if(accDao.save(acc)!=null && accDao.save(acc1) != null) {
					tr.setAmount(sd.getAmount());
					tr.setReceiverAccNo(acc1.getAccno());
					tr.setSenderAccno(acc.getAccno());
					tr.setTransactionCode(gen.generateTransactionCode());
					tr.setDateTime(t.dateTime());
					tDao.save(tr);
					String message  = "Confirmed on "+t.dateTime()+" you sent ksh"+ sd.getAmount()+" to account "+
				""+sd.getAccno()+" whose user is "+acc1.getName()+".Get more services on the pocketBanking app. "+
							"Thankyou and have a nice day!";
					String message1  = "Confirmed on "+t.dateTime()+" you received ksh"+ sd.getAmount()+" from account "+
							""+acc.getAccno()+" whose user is "+acc.getName()+".Get more services on the"+
							" pocketBanking app. Thankyou and have a nice day!";
					es.sendSimpleMail(message, acc.getEmail());
					es.sendSimpleMail(message1, acc1.getEmail());
					return "done";
				}
			}
		}
		return "error";
	}
	
	public String withdraw(WithdrawDetails wd, Account acc, AtmAgent agt , HttpSession session, Withdraw w) {
		if(!su.checkSession(session)) {
			return "expired";
		}
		acc = accDao.getAccountUser(su.getSessionArray(session)[2]);
		
		if(!se.checkPassPin(acc.getPin(), wd.getPin())){
			return "wrong pin";
		}else {
			agt = agtDao.getUserByAgentNo(wd.getAgentNo());
			agt.setFloatbalance(agt.getFloatbalance()+wd.getAmount());
			agtDao.save(agt);
			acc.setBalance(acc.getBalance()-wd.getAmount());
			accDao.save(acc);
			w.setAmount(wd.getAmount());
			w.setAtmAgentNo(wd.getAgentNo());
			w.setDateTime(t.dateTime());
			w.setTransactionCode(gen.generateTransactionCode());
			wDao.save(w);
			
			String agentmessage = w.getTransactionCode()+" Confirmed on "+t.dateTime()+" "+acc.getName()+" withdrew ksh"+wd.getAmount()+
					".Your new Balance is ksh"+agt.getFloatbalance();
			String accountmessage =  w.getTransactionCode()+" Confimed on "+t.dateTime()+" you withdrew from "+agt.getOwner()+". Your new balance"+
					" is ksh"+acc.getBalance()+".";
			
			es.sendSimpleMail(agentmessage, agt.getEmail());
			es.sendSimpleMail(accountmessage, acc.getEmail());
			return "done";
		}
	}
}
