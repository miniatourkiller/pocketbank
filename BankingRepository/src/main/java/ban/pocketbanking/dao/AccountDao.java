package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ban.pocketbanking.entities.Account;

public interface AccountDao extends JpaRepository<Account,Integer>{
	@Query("FROM Account acc WHERE acc.accno = ?1")
Account getAccountUser(String accNo);
	
	@Query("FROM Account acc WHERE acc.vkey = ?1")
	Account getVerified( String vkey);
	@Query("FROM Account acc WHERE acc.email = ?1")
	Account getUserByEmail(String email);
}
