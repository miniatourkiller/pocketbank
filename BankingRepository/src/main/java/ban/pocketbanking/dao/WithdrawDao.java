package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ban.pocketbanking.entities.Withdraw;

public interface WithdrawDao extends JpaRepository<Withdraw, Integer>{

}
