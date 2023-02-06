package ban.pocketbanking.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.AtmAgent;
import ban.pocketbanking.utilities.SessionUtil;

@Service
public class CheckBal {
	@Autowired
	 AccountServices accServe;
	@Autowired
	AgentServices agtServe;
	@Autowired
	SessionUtil su;
public int bal(HttpSession session, String pin, Account account, AtmAgent agent) {
	System.out.println(su.getSessionArray(session)[0]);
	if(su.getSessionArray(session)[0].equals("account")) {
		return accServe.getBalance(session, pin, account);
	}else if(su.getSessionArray(session)[0].equals("agent") ) {
		return agtServe.getFloat(session, pin, agent);
	}
	return -1;
}
}
