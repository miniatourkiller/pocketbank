package ban.pocketbanking.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import ban.pocketbanking.dao.AccountDao;
import ban.pocketbanking.dao.AtmAgentDao;
import ban.pocketbanking.dao.DepositDao;
import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.AtmAgent;
import ban.pocketbanking.entities.Deposit;
import ban.pocketbanking.essential.DepositDetails;
import ban.pocketbanking.essential.PinContent;
import ban.pocketbanking.utilities.EmailSending;
import ban.pocketbanking.utilities.Generator;
import ban.pocketbanking.utilities.SecurityEncryption;
import ban.pocketbanking.utilities.SessionUtil;
import ban.pocketbanking.utilities.Time;

public class AgentServices {
	@Autowired
	AtmAgentDao agtDao;
	@Autowired
	SessionUtil su;
	@Autowired
	SecurityEncryption se;
	@Autowired
	EmailSending es;
	@Autowired
	Time t;
	@Autowired
	AccountDao accDao;
	@Autowired
	Generator gen;
	@Autowired
	DepositDao dDao;
	
	public boolean checkPin(HttpSession session, AtmAgent agt, PinContent pc) {
		if(session == null) {
			return false;
		}
		agt = agtDao.getUserByAgentNo(su.getSessionArray(session)[2]);
		if(se.checkPassPin(agt.getPin(), pc.getPin())) {
			return true;
		}
		return false;
	}
	
	public AtmAgent agent(HttpSession session) {
		return agtDao.getUserByAgentNo(su.getSessionArray(session)[2]);
	}
	
public String deposit(DepositDetails dd, HttpSession session, Account acc, AtmAgent agt, Deposit d) {
	if(session == null) {
		return "expired";
	}
	agt = agtDao.getUserByAgentNo(su.getSessionArray(session)[2]);
	if(!se.checkPassPin(agt.getPin(), dd.getPin())) {
		return "wrong pin";
	}
	if(agt.getFloatbalance()<dd.getAmount()) {
		return "no float";
	}else {
		agt.setFloatbalance(agt.getFloatbalance()-dd.getAmount());
		agtDao.save(agt);
		acc = accDao.getAccountUser(dd.getAccno());
		acc.setBalance(dd.getAmount()+acc.getBalance());
		accDao.save(acc);
		d.setAccno(dd.getAccno());
		d.setAmount(dd.getAmount());
		d.setAtmAgentNo(su.getSessionArray(session)[2]);
		d.setDateTime(t.dateTime());
		d.setTransactionCode(gen.generateTransactionCode());
		dDao.save(d);
		
		String agentmessage = "Confirmed on "+t.dateTime()+" you deposited ksh"+
		dd.getAmount()+" into "+acc.getName()+"'s account. Your new Balance is ksh"+agt.getFloatbalance();
		String accountmessage = "Confirmed on "+t.dateTime()+" you received ksh"+
				dd.getAmount()+" from "+agt.getOwner()+". Your new Balance is ksh"+acc.getBalance();
		
		es.sendSimpleMail(agentmessage, agt.getEmail());
		es.sendSimpleMail(accountmessage, acc.getEmail());
		return "done";
	}

}
public int getFloat(HttpSession session, String pin, AtmAgent agent) {
	if(session == null) {
		return -1;
	}
	agent = agtDao.getUserByAgentNo(su.getSessionArray(session)[2]);
	if(se.checkPassPin(agent.getPin(), pin)) {
		String message = "Your float balance is ksh"+agent.getFloatbalance()+" on "+t.dateTime();
		es.sendSimpleMail(message, agent.getEmail());
		return agent.getFloatbalance();
	}
	return -1;
}
public String suggestion(HttpSession session, String agentNo, AtmAgent agt) {
	if(session == null) {
		return "expired";
	}
	agt = agtDao.getUserByAgentNo(agentNo);
	if(agt == null) {
		return "no atm";
	}else {
		return agt.getOwner();
	}
}
}
