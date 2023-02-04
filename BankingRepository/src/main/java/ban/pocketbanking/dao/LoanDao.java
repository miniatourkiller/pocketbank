package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ban.pocketbanking.entities.Loan;

public interface LoanDao extends JpaRepository<Loan, Integer>{

}
