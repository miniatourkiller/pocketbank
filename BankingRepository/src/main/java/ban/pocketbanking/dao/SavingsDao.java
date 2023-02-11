package ban.pocketbanking.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ban.pocketbanking.entities.Savings;

public interface SavingsDao extends JpaRepository<Savings, Integer>{

	@Query("FROM Savings s WHERE s.savingAccName = ?1")
	Savings getBysavingAccName(String savingAccName);
	@Query("FROM Savings s WHERE s.savingsAccNo = ?1")
	Savings getBysavingsAccNo(String savingsAccNo);
	@Query("FROM Savings s WHERE s.accNo = ?1")
	ArrayList<Savings> getAllSavingsAccounts(String accNo);
}
