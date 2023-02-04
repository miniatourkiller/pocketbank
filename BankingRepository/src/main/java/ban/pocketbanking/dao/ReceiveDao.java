package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ban.pocketbanking.entities.Receive;

public interface ReceiveDao extends JpaRepository<Receive,Integer>{

}
