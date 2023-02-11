package ban.pocketbanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ban.pocketbanking.entities.ProfilePic;

public interface ProfilePicDao extends JpaRepository<ProfilePic, Integer>{
@Query("FROM ProfilePic p WHERE p.identity = ?1")
public ProfilePic getByaccno(String identity);
}
