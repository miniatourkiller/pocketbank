package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ban.pocketbanking.entities.Transactions;

public interface TransactionsDao extends JpaRepository<Transactions, Integer>{

}
