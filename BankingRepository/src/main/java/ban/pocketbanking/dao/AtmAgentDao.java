package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ban.pocketbanking.entities.AtmAgent;

public interface AtmAgentDao extends JpaRepository<AtmAgent, Integer>{
	@Query("FROM AtmAgent agt WHERE agt.agentNo = ?1")
	AtmAgent getAtmAgent(int atmNo);
	
	@Query("FROM AtmAgent agt WHERE agt.vkey = ?1")
	AtmAgent getVerified(String vkey);
	
	@Query("FROM AtmAgent agt WHERE agt.email = ?1")
	AtmAgent getUserByEmail(String email);
}
