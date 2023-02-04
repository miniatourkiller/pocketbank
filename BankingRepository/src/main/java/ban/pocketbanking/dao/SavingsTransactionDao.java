package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ban.pocketbanking.entities.SavingsTransaction;

public interface SavingsTransactionDao extends JpaRepository<SavingsTransaction, Integer>{

}
