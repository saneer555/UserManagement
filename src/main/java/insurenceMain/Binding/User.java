package insurenceMain.Binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {
	

	private String userName;
	
	private String userEmail;
	
	private String userMobile;
	
	private String gender;
	
	private LocalDate dateOfBirth;
	
	private String ssn;
	

}
