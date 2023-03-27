package ban.pocketbanking.main;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.AtmAgent;
import ban.pocketbanking.entities.Deposit;
import ban.pocketbanking.entities.Loan;
import ban.pocketbanking.entities.ProfilePic;
import ban.pocketbanking.entities.Savings;
import ban.pocketbanking.entities.SavingsTransaction;
import ban.pocketbanking.entities.Transactions;
import ban.pocketbanking.entities.Withdraw;
import ban.pocketbanking.essential.BalanceCheck;
import ban.pocketbanking.essential.C2B;
import ban.pocketbanking.essential.C2Bresponse;
import ban.pocketbanking.essential.DepositDetails;
import ban.pocketbanking.essential.EmailContent;
import ban.pocketbanking.essential.LoanDetails;
import ban.pocketbanking.essential.LoginDetails;
import ban.pocketbanking.essential.NewPassword;
import ban.pocketbanking.essential.SavingDetails;
import ban.pocketbanking.essential.SendDetails;
import ban.pocketbanking.essential.WithdrawDetails;
import ban.pocketbanking.services.AccountMaintainance;
import ban.pocketbanking.services.AccountServices;
import ban.pocketbanking.services.AgentServices;
import ban.pocketbanking.services.CheckBal;
import ban.pocketbanking.services.LoanServices;
import ban.pocketbanking.services.Login;
import ban.pocketbanking.services.Register;
import ban.pocketbanking.services.SavingsServices;
import ban.pocketbanking.utilities.Daraja;
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
return	su.destroySession(req.getSession());	 
}

@Autowired
CheckBal cb;
@RequestMapping(value = "balance", method= RequestMethod.POST)
public int getBalance(@RequestBody BalanceCheck bal, HttpServletRequest req, Account account, AtmAgent agent) {
	return cb.bal(req.getSession(), bal.getPin(), account, agent);
}

@Autowired
AccountServices as;

@RequestMapping(value="account")
public Account account(HttpServletRequest req) {
	return as.account(req.getSession());
}


@RequestMapping(value = "send", method= RequestMethod.POST)
public String send(HttpServletRequest req,@RequestBody SendDetails sd, Account acc, Account acc1, Transactions tr) {
	return as.send(sd, acc, acc1, req.getSession(), tr);
}

@RequestMapping(value = "withdraw", method= RequestMethod.POST)
public String withdraw(@RequestBody WithdrawDetails wd, AtmAgent agt, Withdraw w, Account acc, HttpServletRequest req) {
	return as.withdraw(wd, acc, agt, req.getSession(), w);
}
//this will happen before a send or deposit request
@RequestMapping(value = "accountsuggestion/{accno}")
public String accSuggestion(HttpServletRequest req,Account acc,@PathVariable("accno") String accno ) {
	return as.accountSuggestion(accno, req.getSession(), acc);
}
@Autowired
AgentServices agtServices;

@RequestMapping(value="agent")
public AtmAgent agent(HttpServletRequest req) {
	return agtServices.agent(req.getSession());
}

@RequestMapping(value = "deposit", method= RequestMethod.POST)
public String deposit(@RequestBody DepositDetails dd, HttpServletRequest req, Account acc, AtmAgent agt, Deposit d) {
	return agtServices.deposit(dd, req.getSession(), acc, agt, d);
}
//this will be called before a withdrawal from agent
@RequestMapping(value = "agentsuggestion/{agentno}")
public String agtSuggestion(HttpServletRequest req,@PathVariable("agentno") String agentNo, AtmAgent agt) {
	return agtServices.suggestion(req.getSession(), agentNo, agt);
}
@Autowired
LoanServices ls;

@Value("${loan.duration}")
int days;

@RequestMapping(value="checklimit", method = RequestMethod.GET)
public int checkLimit(HttpServletRequest req, ArrayList<Savings> savings){
	return ls.checkLimit(req.getSession(), savings);
}

@RequestMapping(value = "requestloan", method= RequestMethod.POST)
public String requestLoan(HttpServletRequest req, ArrayList<Savings> savings, Account acc,@RequestBody LoanDetails ld, Loan l) {
	return ls.requestLoan(req.getSession(), acc, ld, l, days, savings);
}
@RequestMapping(value = "repayloan", method= RequestMethod.POST)
public String repayLoan(HttpServletRequest req, Account acc, Loan l,@RequestBody LoanDetails ld) {
	return ls.payLoan(req.getSession(), l, acc, ld);
}

@RequestMapping(value = "checkloan", method= RequestMethod.POST)
public Loan checkLoan(Loan l, HttpServletRequest req) {
	return ls.loanDetails(l, req.getSession());
}

@RequestMapping(value = "establishpenalty", method= RequestMethod.POST)
public String establishPenalty(HttpServletRequest req, Loan l) {
	return ls.establishPenalty(req.getSession(), l, days);
}

@Autowired
SavingsServices ss;

@RequestMapping(value = "getsavingsaccounts", method= RequestMethod.GET)
public ArrayList<Savings> getsavingsaccounts(HttpServletRequest req){
	return ss.getAll(req.getSession());
}

@RequestMapping(value = "getsavingsaccount/{savingsaccountname}", method = RequestMethod.GET)
public Savings getsavingaccount(@PathVariable("savingsaccountname") String savingsAccName, HttpServletRequest req){
	return ss.getSavingaccount(savingsAccName,req.getSession());
}

@RequestMapping(value = "createsavingsaccount", method= RequestMethod.POST)
public String createsavingsaccount(HttpServletRequest req,@RequestBody Savings s, Savings s2) {
	return ss.createSavings(req.getSession(), s, s2);
}

@RequestMapping(value = "destroysavingsaccount/{savingsaccno}")
public String destroysavingsaccount(@PathVariable("savingsaccno") String savingsAccName, HttpServletRequest req, Savings s) {
	return ss.destroySavingAccount(req.getSession(), savingsAccName, s);
}

@RequestMapping(value = "depositsavings", method= RequestMethod.POST)
public String depositsavings(@RequestBody SavingDetails sd, Savings s, Account acc, SavingsTransaction st,HttpServletRequest req) {
	return ss.deposit(sd, req.getSession(), acc, s, st);
}

@RequestMapping(value = "withdrawsavings", method= RequestMethod.POST)
public String withdrawsavings(HttpServletRequest req, @RequestBody SavingDetails sd,Account acc, Savings s, SavingsTransaction st) {
	return ss.withdraw(req.getSession(), sd, acc, s, st);
}
@Autowired
AccountMaintainance am;
@RequestMapping(value = "accountgetcode/{email}")
public String accountsendCode(@PathVariable("email") String email, Account acc) {
	return am.accountPasswordRetreivalMail(email, acc);
}

@RequestMapping(value = "accountchangepassword", method= RequestMethod.POST)
public String accountnewPassword(Account acc, @RequestBody NewPassword np) {
	return am.accountPasswordChange(np, acc);
}
@RequestMapping(value = "agentgetcode/{email}")
public String agentsendCode(@PathVariable("email") String email, AtmAgent agt) {
	return am.agentPasswordRetreivalMail(email, agt);
}

@RequestMapping(value = "agentchangepassword", method= RequestMethod.POST)
public String agentnewPassword(AtmAgent agt, @RequestBody NewPassword np) {
	return am.agentPasswordChange(np, agt);
}

@RequestMapping(value = "helpdesk", method= RequestMethod.POST)
public String help(@RequestBody EmailContent m) {
	return am.helpDesk(m);
}

@RequestMapping(value = "profilepic")
public String profilepic(@RequestPart("file") MultipartFile file, ProfilePic p, HttpServletRequest req) {
	return am.profilePic(req.getSession(), file, p);
}
@RequestMapping(value = "getprofilepic")
public ProfilePic getprofilepic( HttpServletRequest req) {
	return am.getPic(req.getSession());
}

@Autowired
Daraja daraja;
@RequestMapping(value = "daraja", method= RequestMethod.POST)
public C2Bresponse daraja(@RequestBody C2B c2b){
return daraja.testApiC2B(c2b);
}
}
