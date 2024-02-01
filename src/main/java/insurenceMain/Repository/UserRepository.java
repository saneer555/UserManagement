package insurenceMain.Repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import insurenceMain.Entity.UserRegistration;

public interface UserRepository extends JpaRepository<UserRegistration, Serializable>{
	
	public UserRegistration findByUserEmail(String userEmail);
	

}
