package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ban.pocketbanking.entities.Savings;

public interface SavingsDao extends JpaRepository<Savings, Integer>{

}
