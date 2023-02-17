package ban.pocketbanking.services;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import ban.pocketbanking.dao.AccountDao;
import ban.pocketbanking.dao.AtmAgentDao;
import ban.pocketbanking.dao.ProfilePicDao;
import ban.pocketbanking.entities.Account;
import ban.pocketbanking.entities.AtmAgent;
import ban.pocketbanking.entities.ProfilePic;
import ban.pocketbanking.essential.EmailContent;
import ban.pocketbanking.essential.NewPassword;
import ban.pocketbanking.utilities.EmailSending;
import ban.pocketbanking.utilities.Generator;
import ban.pocketbanking.utilities.SecurityEncryption;
import ban.pocketbanking.utilities.SessionUtil;

public class AccountMaintainance {
	@Autowired
AccountDao accDao;
	@Autowired
	Generator gen;
	@Autowired
	EmailSending es;
	@Autowired
	SecurityEncryption se;
	@Autowired
	SessionUtil su;
	@Autowired
	ProfilePicDao pDao;
	@Autowired
	AtmAgentDao agtDao;
	
	
	public String accountPasswordRetreivalMail(String email, Account acc) {
		acc = accDao.getUserByEmail(email);
		if(acc == null) {
			return "register";
		}else {
			acc.setPasswordretriever(gen.generatepasswordretriever());
			accDao.save(acc);
			String message = "Your password renewal code is "+acc.getPasswordretriever()+". Please don't share this information.";
			es.sendSimpleMail(message, email);
			return "done";
		}
	}
	 
	public String agentPasswordRetreivalMail(String email, AtmAgent agt) {
		agt = agtDao.getUserByEmail(email);
		if(agt == null) {
			return "register";
		}else {
			agt.setPasswordretriever(gen.generatepasswordretriever());
			agtDao.save(agt);
			String message = "Your password renewal code is "+agt.getPasswordretriever()+". Please don't share this information.";
			es.sendSimpleMail(message, email);
			return "done";
		}
	}
	
	public String agentPasswordChange(NewPassword np, AtmAgent agt) {
		agt = agtDao.getUserByEmail(np.getEmail());
		if(agt == null) {
			return "email error";
		}else {
			if(agt.getPasswordretriever().equals(np.getRetrievalCode())) {
				agt.setPasswordretriever(se.hashPassPin("sdfsdfsfsfsdf"));
				agt.setPassword(se.hashPassPin(np.getNewPassword()));
				agtDao.save(agt);
				return "done";
			}else {
				return "invalid code";
			}
		}
	}
	
	
	public String accountPasswordChange(NewPassword np, Account acc) {
		acc = accDao.getUserByEmail(np.getEmail());
		if(acc == null) {
			return "email error";
		}else {
			if(acc.getPasswordretriever().equals(np.getRetrievalCode())) {
				acc.setPasswordretriever(se.hashPassPin("sdfsdfsfsfsdf"));
				acc.setPassword(se.hashPassPin(np.getNewPassword()));
				accDao.save(acc);
				return "done";
			}else {
				return "invalid code";
			}
		}
	}
	
	public String profilePic(HttpSession session, MultipartFile file, ProfilePic p) {
		if(session  == null) {
			return "expired";
		}else {
			p.setidentity(su.getSessionArray(session)[2]);
			p.setFilePath(file.getOriginalFilename());
			try {
				file.transferTo(new File("C:\\Users\\User\\Documents\\workspace-spring-tool-suite-4-4.14.0.RELEASE\\PocketBanking\\BankingMain\\src\\main\\resources\\public\\profilepics\\"+file.getOriginalFilename()));
				pDao.save(p);
				return "done";
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			return null;
		
	}
	
	public String helpDesk(EmailContent m) {
		 es.sendSimpleMail(m.getMessage(), "jgathiru97@gmail.com");
		 return "done";
	}
	public ProfilePic getPic(HttpSession session) {
		return pDao.getByaccno(su.getSessionArray(session)[2]);
	}
}
