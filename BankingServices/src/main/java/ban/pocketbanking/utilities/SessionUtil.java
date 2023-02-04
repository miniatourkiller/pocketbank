package ban.pocketbanking.utilities;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class SessionUtil {
public String createSession(HttpSession session, String who,String email, String identityNo) {
	if(checkSession(session)) {
		return "session available";
	}
	session.setAttribute("details",""+who+ ""+email+":"+identityNo+"");
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
