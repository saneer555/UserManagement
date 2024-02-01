package insurenceMain.Binding;

import lombok.Data;

@Data
public class ActivateAccount {
	
	private String regEmail;
	
	private String tempPassword;
	
	private String newPassword;
	
	private String confirmPassword;
	
	

}
