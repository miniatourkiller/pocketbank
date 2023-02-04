package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ban.pocketbanking.entities.LoanTransaction;

public interface LoanTransactionDao extends JpaRepository<LoanTransaction, Integer>{

}
