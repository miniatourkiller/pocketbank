package ban.pocketbanking.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import ban.pocketbanking.dao.AccountDao;
import ban.pocketbanking.dao.AtmAgentDao;
import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.AtmAgent;
import ban.pocketbanking.entities.LoginDetails;
import ban.pocketbanking.utilities.SecurityEncryption;
import ban.pocketbanking.utilities.SessionUtil;

public class Login {
	@Autowired
	AccountDao accDao;

	@Autowired
	AtmAgentDao agentDao;
@Autowired
SecurityEncryption se;

@Autowired
SessionUtil su;
public String loginAcc(Account account, LoginDetails acc, HttpSession session) {
	account =accDao.getUserByEmail(acc.getEmail());
	if(account != null) {
		if(!account.isVerified()) {
			return "You have not yet verified it's you. Check your email and if you cannt find"+
		" a verification email, check the spam";
		}
		if(se.checkPassPin(account.getPassword(), acc.getPassword())) {
			su.createSession(session,"account", account.getEmail(), account.getAccno());
			return "Welcome "+account.getName();
		}
	}
	return "no account with those details, please register if no account";
}



public String loginAtm(LoginDetails agent, AtmAgent agt, HttpSession session) {
	 agt =agentDao.getUserByEmail(agent.getEmail());
	if(agt != null) {
		if(!agt.isVerified()) {
			return "You have not yet verified it's you. Check your email and if you cannt find"+
		" a verification email, check the spam";
		}
		if(se.checkPassPin(agt.getPassword(), agent.getPassword())) {
			su.createSession(session,"agent", agt.getEmail(), agt.getAgentNo());
			return "Welcome "+agt.getOwner();
		}
	}
	return "no account with those details, please register if no account";
}
}
