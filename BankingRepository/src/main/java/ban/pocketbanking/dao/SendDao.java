package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ban.pocketbanking.entities.Send;

public interface SendDao extends JpaRepository<Send, Integer>{

}
