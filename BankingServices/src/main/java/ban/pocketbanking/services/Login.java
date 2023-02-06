package ban.pocketbanking.services;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import ban.pocketbanking.dao.AccountDao;
import ban.pocketbanking.dao.AtmAgentDao;
import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.AtmAgent;
import ban.pocketbanking.essential.LoginDetails;
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
public String loginAcc(Account account, LoginDetails acc, HttpServletRequest req) {
	account =accDao.getUserByEmail(acc.getEmail());
	if(account != null) {
		if(!account.isVerified()) {
			return "You have not yet verified it's you. Check your email and if you cannt find"+
		" a verification email, check the spam";
		}
		if(se.checkPassPin(account.getPassword(), acc.getPassword())) {
			if(su.createSession(req,"account", account.getEmail(), account.getAccno()) == "done") {
				return "Welcome "+account.getName();
			}else if(su.createSession(req,"account", account.getEmail(), account.getAccno()) == "session available") {
				return "logout first";
			}
			else {
				return "failed to create a session";
			}
			
		}
	}
	return "no account with those details, please register if no account";
}



public String loginAtm(LoginDetails agent, AtmAgent agt, HttpServletRequest req) {
	 agt =agentDao.getUserByEmail(agent.getEmail());
	if(agt != null) {
		if(!agt.isVerified()) {
			return "You have not yet verified it's you. Check your email and if you cannt find"+
		" a verification email, check the spam";
		}
		if(se.checkPassPin(agt.getPassword(), agent.getPassword())) {
			if(su.createSession(req,"agent", agt.getEmail(), agt.getAgentNo()) == "done") {
				return "Welcome "+agt.getOwner();
			}else {
				return "failed to create a session";
			}
			
		}
	}
	return "no account with those details, please register if no account";
}
}
