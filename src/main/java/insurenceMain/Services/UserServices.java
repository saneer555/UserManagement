package insurenceMain.Services;

import java.util.List;

import insurenceMain.Binding.ActivateAccount;
import insurenceMain.Binding.Login;
import insurenceMain.Binding.User;

public interface UserServices {
	
	public Boolean register(User user);
	
	public Boolean activeAccount(ActivateAccount isActive);
	
	public String userLogin(Login login);
	
	public String forgotPassword(String userName);
	
	public List<User>getAllUsers();
	
	public Boolean updateUser(Integer id,User user);
	
	public Boolean deleteUser(Integer id);
	
	public Boolean softDelete(Integer id, String statusChange);
	
	public User getUserById(Integer id);

}
