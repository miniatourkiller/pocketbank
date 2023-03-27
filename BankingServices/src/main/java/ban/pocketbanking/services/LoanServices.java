package ban.pocketbanking.services;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ban.pocketbanking.dao.AccountDao;
import ban.pocketbanking.dao.LoanDao;
import ban.pocketbanking.dao.LoanTransactionDao;
import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.Loan;
import ban.pocketbanking.entities.LoanTransaction;
import ban.pocketbanking.entities.Savings;
import ban.pocketbanking.essential.LoanDetails;
import ban.pocketbanking.utilities.EmailSending;
import ban.pocketbanking.utilities.Generator;
import ban.pocketbanking.utilities.SecurityEncryption;
import ban.pocketbanking.utilities.SessionUtil;
import ban.pocketbanking.utilities.Time;

@Service
public class LoanServices {
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
	@Autowired(required =false)
	LoanTransaction lt;
	@Autowired
	LoanTransactionDao ltDao;
	@Autowired
	LoanDao lDao;
	@Autowired
	SavingsServices ss;
	
	public int checkLimit(HttpSession session, ArrayList<Savings> savings){
		if (!su.checkSession(session)) {
			return -1;
		}
		return ss.totalSavings(session, savings)*3;
	}


	public String requestLoan(HttpSession session, Account acc, LoanDetails ld, Loan l, int days, ArrayList<Savings> savings) {
		if(!su.checkSession(session)) {
			return "expired";
		}
		acc = accDao.getAccountUser(su.getSessionArray(session)[2]);
		if(!se.checkPassPin(acc.getPin(), ld.getPin())) {
			return "wrong pin";
		}
		l = lDao.getLoanUser(su.getSessionArray(session)[2]);
		if(l.getBalance()>0) {
			return "repay first";
		}else if(l.isPenalized()) {
			return "cant access";
		}else {
			if(ld.getAmount()<ss.totalSavings(session, savings)*3) {
				acc.setBalance(acc.getBalance()+ld.getAmount());
				l.setBalance((int) (ld.getAmount()*1.1));
				l.setDuration(t.daysAfter(days));
				l.setPenalty(false);
				l.setPenaltyDuration(null);
				l.setCounter(0);
				lDao.save(l);
				accDao.save(acc);
				lt.setAccno(acc.getAccno());
				lt.setAmount(ld.getAmount());
				lt.setDateTime(t.dateTime());
				lt.setTransactionCode(gen.generateTransactionCode());
				lt.setType("request");
				ltDao.save(lt);
				String message = lt.getTransactionCode()+" Confirmed on"+t.dateTime()+" you have received a loan "+
				"of amount ksh"+ld.getAmount()+" due on "+l.getDuration()+". Your new account balance is ksh"+
						acc.getBalance();
				es.sendSimpleMail(message, acc.getEmail());
			}else {
				return "loan limit";
			}
		}
		return null;
	}
	
	public Loan loanDetails(Loan l, HttpSession session) {
		if(!su.checkSession(session)) {
			return null;
		}
		l = lDao.getLoanUser(su.getSessionArray(session)[2]);
		return l;
	}
	
	public String establishPenalty(HttpSession session, Loan l, int days) {
		if(!su.checkSession(session)) {
			return "expired";
		}
		l = loanDetails(l, session);
		if(l.getDuration() == null) {
			if(t.differenceInDays(l.getPenaltyDuration())<0){
				l.setPenaltyDuration(null);
				l.setPenalty(false);
			}
			return "no loan";
			///MY TARGET
		}
		if(t.differenceInDays(l.getDuration())<0) {
			l.setPenalty(true);
			l.setPenaltyDuration(t.daysAfter(days));
			int counter = l.getCounter();
			counter ++;
			l.setCounter(counter);
			
			lDao.save(l);
			if(l.getCounter() == 1 || l.getCounter() == 5) {
				//send email
				String message = "Your Loan has past due";
				es.sendSimpleMail(message, su.getSessionArray(session)[1]);
			}
			return "penalty issued";
		}else {
			return "no penalty";
		}
		
	}
	
	public String payLoan(HttpSession session, Loan l, Account acc,LoanDetails ld) {
		if(!su.checkSession(session)) {
			return "expired";
		}
		acc = accDao.getUserByAccno(su.getSessionArray(session)[2]);
		if(!se.checkPassPin(acc.getPin(), ld.getPin())) {
			return "wrong pin";
		}else {
			l = lDao.getLoanUser(acc.getAccno());
			int loanbal = l.getBalance()-ld.getAmount();
			if(loanbal<= 0) {
				acc.setBalance(acc.getBalance()-l.getBalance());
				l.setBalance(0);
				l.setDuration(null);
				l.setCounter(0);
				lDao.save(l);
				accDao.save(acc);
				lt.setAccno(acc.getAccno());
				lt.setAmount(ld.getAmount());
				lt.setDateTime(t.dateTime());
				lt.setTransactionCode(gen.generateTransactionCode());
				lt.setType("repay");
				ltDao.save(lt);
				String message = lt.getTransactionCode()+
						"Confirmed on"+t.dateTime()+" "
						+"you have successfully cleared your loan. Your new account balance is ksh" +acc.getBalance();
				es.sendSimpleMail(message, acc.getEmail());
				return "done";
			}else {
				acc.setBalance(acc.getBalance()-ld.getAmount());
				l.setBalance(l.getBalance() - ld.getAmount());
				lDao.save(l);
				accDao.save(acc);
				lt.setAccno(acc.getAccno());
				lt.setAmount(ld.getAmount());
				lt.setDateTime(t.dateTime());
				lt.setTransactionCode(gen.generateTransactionCode());
				lt.setType("repay");
				ltDao.save(lt);
				String message =  lt.getTransactionCode()+
						"Confirmed on"+t.dateTime()+" "
						+"you have partially paid your loan. Loan balance is ksh "+
						l.getBalance()+" while account balance is ksh"+acc.getBalance()+".";
				es.sendSimpleMail(message, acc.getEmail());
				return "done";
			}
			
		}
		
	}
}