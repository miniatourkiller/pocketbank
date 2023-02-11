package ban.pocketbanking.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProfilePic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String identity;
private String filePath;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getidentity() {
	return identity;
}
public void setidentity(String identity) {
	this.identity = identity;
}
public String getFilePath() {
	return filePath;
}
public void setFilePath(String filePath) {
	this.filePath = filePath;
}

}
