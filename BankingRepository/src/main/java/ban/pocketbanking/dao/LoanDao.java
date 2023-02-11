package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ban.pocketbanking.entities.Loan;

public interface LoanDao extends JpaRepository<Loan, Integer>{
@Query("FROM Loan l WHERE l.accNo = ?1")
Loan getLoanUser(String accNo);
}
