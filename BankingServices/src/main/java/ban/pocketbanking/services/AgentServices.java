package ban.pocketbanking.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import ban.pocketbanking.dao.AtmAgentDao;
import ban.pocketbanking.entities.AtmAgent;
import ban.pocketbanking.essential.DepositDetails;
import ban.pocketbanking.utilities.EmailSending;
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
public String deposit(DepositDetails dd, HttpSession session) {
	if(session == null) {
		return "session expired";
	}
	return "";
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
}
