package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ban.pocketbanking.entities.MpesaDarajaResult;



public interface MpesaTransactionDao extends JpaRepository<MpesaDarajaResult, Integer>{
    
}
