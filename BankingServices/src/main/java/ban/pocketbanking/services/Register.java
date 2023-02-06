package ban.pocketbanking.services;

import org.springframework.beans.factory.annotation.Autowired;

import ban.pocketbanking.dao.AccountDao;
import ban.pocketbanking.dao.AtmAgentDao;
import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.AtmAgent;
import ban.pocketbanking.utilities.EmailVerification;
import ban.pocketbanking.utilities.Generator;
import ban.pocketbanking.utilities.SecurityEncryption;




public class Register {
	@Autowired
	AccountDao accDao;
	
	@Autowired
	Generator gen;
	@Autowired
	AtmAgentDao agentDao;
	@Autowired
	EmailVerification ev;
	@Autowired
	SecurityEncryption se;
	public String regAcc(Account acc, String url, String apikey) {
		if(accDao.getUserByEmail(acc.getEmail()) != null) {
			return "email exists";
		}
		if(ev.realEmail(url, apikey, acc.getEmail())) {
			setAccNo(acc);
			acc.setPassword(se.hashPassPin(acc.getPassword()));
			acc.setPin(se.hashPassPin(acc.getPin()));
			acc.setVkey(gen.generatevkey());
			acc.setBalance(0);
			ev.verification(acc);
			acc.setVerified(false);
			if(accDao.save(acc) != null) {
				return "done";
			}
		}else {
			return "false email";
		}
		
		return "error";
	}
	
	public String regAgent(AtmAgent agent, String url, String apikey) {
		if(agentDao.getUserByEmail(agent.getEmail()) != null) {
			return "email exists";
		}
		if(ev.realEmail(url, apikey, agent.getEmail())) {
			setAgentNo(agent);
			agent.setPassword(se.hashPassPin(agent.getPassword()));
			agent.setPin(se.hashPassPin(agent.getPin()));
			agent.setVkey(gen.generatevkey());
			agent.setFloatbalance(10000);
			ev.verification(agent);
			agent.setVerified(false);
			if(agentDao.save(agent) != null) {
				return "done";
			}
		}else {
			return "false email";
		}
		
		return "error";
	}
	
	public void setAccNo(Account acc) {
		acc.setAccno(gen.generateAccNo());
		if(accDao.getAccountUser(acc.getAccno())!=null) {
			setAccNo(acc);
		}
	}

	public void setAgentNo(AtmAgent agent) {
		agent.setAgentNo(gen.generateAgentNo());
		if(accDao.getAccountUser(agent.getAgentNo())!=null) {
			setAgentNo(agent);
		}
	}

	public String verifyAccount(String vkey,Account account) {
		account = accDao.getVerified(vkey);
		if(account == null) {
			return "vkey expired";
		}
		account.setVerified(true);
		accDao.save(account);
		return "<h2>You are now verified and can login</h2>";
	}
	public String verifyAgent(String vkey,AtmAgent agent) {
		agent = agentDao.getVerified(vkey);
		if(agent == null) {
			return "vkey expired";
		}
		agent.setVerified(true);
		agentDao.save(agent);
		return "<h2>You are now verified and can login</h2>";
	}
}
