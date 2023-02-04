package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ban.pocketbanking.entities.Deposit;

public interface DepositDao extends JpaRepository<Deposit,Integer>{

}
