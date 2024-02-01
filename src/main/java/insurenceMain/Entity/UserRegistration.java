package insurenceMain.Entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="REGISTRATION")
public class UserRegistration {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	private String userName;
	
	private String userEmail;
	
	private String userMobile;
	
	private String gender;
	
	private LocalDate dateOfBirth;
	
	private String ssn;
	
	private String password;
	
	
	private Boolean isActive;
	
	private String softDelete;
	
	@Column(name="CREATED_DATE",updatable = false)
	@CreationTimestamp
	private LocalDate createdDate;
	
	@Column(name="UPDATED_DATE",insertable = false)
	private LocalDate updatedDate;
	
	private String createdBy;
	
	private String updatedBy;

}
