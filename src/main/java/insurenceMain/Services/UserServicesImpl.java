package insurenceMain.Services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import insurenceMain.Binding.ActivateAccount;
import insurenceMain.Binding.Login;
import insurenceMain.Binding.User;
import insurenceMain.EmailUtils.EmailVerfication;
import insurenceMain.Entity.UserRegistration;
import insurenceMain.Repository.UserRepository;

@Service
public class UserServicesImpl implements UserServices{

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EmailVerfication MailSent;

	@Override
	public Boolean register(User user) {
		UserRegistration newUser = new UserRegistration();
		BeanUtils.copyProperties(user, newUser);
		newUser.setPassword(generatePassword());
		newUser.setIsActive(false);
		UserRegistration save = userRepo.save(newUser);

		String fileName = "Registration.txt";
		String subjet = "Youre Registration Success";
		try {
			String body = emailSent(newUser.getUserName(), newUser.getPassword(),fileName);
			MailSent.mailSent(newUser.getUserEmail(), subjet, body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return save.getUserId() != null;
	}

	@Override
	public Boolean activeAccount(ActivateAccount isActive) {
		UserRegistration newUser = new UserRegistration();
		newUser.setUserEmail(isActive.getRegEmail());
		newUser.setPassword(isActive.getTempPassword());

		Example<UserRegistration> of = Example.of(newUser);

		List<UserRegistration> list = userRepo.findAll(of);

		if(list.isEmpty()) {
			return false;
		}else {
			UserRegistration userRegistration = list.get(0);
			userRegistration.setPassword(isActive.getNewPassword());
			userRegistration.setIsActive(true);
			userRepo.save(userRegistration);
			return true;
		}

	}

	@Override
	public String userLogin(Login login) {

		UserRegistration newUser = new UserRegistration();
		newUser.setUserName(login.getUserName());
		newUser.setPassword(login.getPassword());

		Example<UserRegistration> of = Example.of(newUser);

		List<UserRegistration> findAll = userRepo.findAll(of);

		if(findAll.isEmpty()) {
			return "Invalid Credientails";
		}else {
			UserRegistration userRegistration = findAll.get(0);

			if(userRegistration.getIsActive().equals(true)) {
				return "Success";
			}else {
				return "Account  Not actived";
			}

		}

	}

	@Override
	public String forgotPassword(String userEmail) {
		UserRegistration findByUserEmail = userRepo.findByUserEmail(userEmail);
		if(findByUserEmail==null) {
			return "Invalid Email";
		}
		String subject="";
		String fileName ="RecoverPassword.txt";
		try {
			String body = emailSent(findByUserEmail.getUserName(), findByUserEmail.getPassword(), fileName);
			Boolean mailSent2 = MailSent.mailSent(userEmail, subject, body);
			if(mailSent2) {
				return "Password Sent to youre Registration Email Sucess";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<User> getAllUsers() {
		List<UserRegistration> findAll = userRepo.findAll();
		List<User> users = new ArrayList<>();

		for(UserRegistration user : findAll) {

			User newUser = new User();

			BeanUtils.copyProperties(newUser, user);

			users.add(newUser);
		}
		return users;
	}

	@Override
	public Boolean updateUser(Integer id, User user) {
		Optional<UserRegistration> byId = userRepo.findById(id);
		if(byId.isPresent()) {
			UserRegistration userRegistration = byId.get();
			userRegistration.setUserName(user.getUserName());
			userRegistration.setUserEmail(user.getUserEmail());
			userRegistration.setGender(user.getGender());
			userRegistration.setDateOfBirth(user.getDateOfBirth());
			userRepo.save(userRegistration);
			return true;
		}
		return false;
	}

	@Override
	public Boolean deleteUser(Integer id) {

		try {
			userRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean softDelete(Integer id, String statusChange) {
		Optional<UserRegistration> id2 = userRepo.findById(id);
		if(id2.isPresent()) {
			UserRegistration userRegistration = id2.get();
			userRegistration.setSoftDelete(statusChange);
			return true;
		}

		return false;
	}

	@Override
	public User getUserById(Integer id) {
		Optional<UserRegistration> findById = userRepo.findById(id);
		if(findById.isPresent()) {
			UserRegistration userRegistration = findById.get();
			User user = new User();
			BeanUtils.copyProperties(userRegistration, user);
			return user;
		}
		return null;
	}

	public String generatePassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder();

		for (int i = 0; i < 8; i++) {
			int randomIndex = random.nextInt(characters.length());
			password.append(characters.charAt(randomIndex));
		}

		return password.toString();
	}

	public String emailSent(String fullname,String password ,String filename) throws IOException {


		String mailSend = null;

		String url = "";

		try {
			FileReader fi = new FileReader(filename);
			BufferedReader br = new BufferedReader(fi);
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while(line != null) {
				sb.append(line);
				line=br.readLine();

			}

			br.close();
			mailSend = sb.toString();
			mailSend =mailSend.replace("{fullName}", fullname)
					.replace("{pwd}", password)
					.replace("{url}", url)
					.replace("{pwd}",password);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mailSend;

	}
}
