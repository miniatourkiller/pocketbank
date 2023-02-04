package ban.pocketbanking.utilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class SessionUtil {
public String createSession(HttpServletRequest req, String who,String email, String identityNo) {
	if(!checkSession(req.getSession())) {
		return "session available";
	}
	req.getSession().setAttribute("details",""+who+ ":"+email+":"+identityNo+"");
	return "done";
}

public String[] getSessionArray(HttpSession session) {
	if(!checkSession(session)) {
		String[] arr = {"no session"};
		return arr;
	}
	return ((String) session.getAttribute("details")).split(":", 3);
}
public boolean checkSession(HttpSession session) {
	if(session != null) {
		return true;
	}
	return false;
}

public String destroySession(HttpSession session) {
	session.invalidate();
	return "done";
}
}
