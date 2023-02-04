package ban.pocketbanking.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.AtmAgent;
import ban.pocketbanking.entities.LoginDetails;
import ban.pocketbanking.services.Login;
import ban.pocketbanking.services.Register;
import ban.pocketbanking.utilities.SessionUtil;

@RestController
public class BankController {
@RequestMapping(value = "hello")
public String helloWorld(HttpServletResponse res) {
	res.setContentType("HTML");
	return "<h1>HELLO WORLD</h1>";
}
@Value("${api.url}")
String url;
@Value("${api.key}")
String key;

@Autowired
Register register;

@RequestMapping(value="register", method = RequestMethod.POST)
public String registerAccount(@RequestBody Account acc){
	return register.regAcc(acc, url, key);
}

@RequestMapping(value = "verify/{vkey}")
public String verify(@PathVariable("vkey") String vkey, HttpServletResponse res, Account account) {
	res.setContentType("HTML");
	return register.verifyAccount(vkey, account);
}

@RequestMapping(value = "registeragent",method= RequestMethod.POST)
public String registerAgent(@RequestBody AtmAgent agent){
	return register.regAgent(agent, url, key);
}
@RequestMapping(value = "verifyagent/{vkey}")
public String verifyAgent(@PathVariable("vkey") String vkey, HttpServletResponse res, AtmAgent agent) {
	res.setContentType("HTML");
	return register.verifyAgent(vkey, agent);
}

@Autowired
Login login;

@RequestMapping(value="login", method = RequestMethod.POST)
public String loginAccount(@RequestBody LoginDetails account, Account acc,HttpServletRequest req){
	return login.loginAcc(acc, account, req);
}

@RequestMapping(value="loginagent", method = RequestMethod.POST)
public String loginAgent(@RequestBody LoginDetails agent, AtmAgent agt,HttpServletRequest req){
	return login.loginAtm( agent, agt,req);
}

@RequestMapping(value="checksession")
public String checksession(HttpServletRequest req) {
	return (String)req.getSession().getAttribute("details");
}
@RequestMapping(value="logout")
public String logout(SessionUtil su,HttpServletRequest req ) {
	su.destroySession(req.getSession());
	return "done";
}
}
