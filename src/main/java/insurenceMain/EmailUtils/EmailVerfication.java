package insurenceMain.EmailUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailVerfication {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public Boolean mailSent(String to,String subject,String body) {
		
		boolean isMailSent = false;
		
		try {
			
			MimeMessage MimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper message = new MimeMessageHelper(MimeMessage);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body,true);
			mailSender.send(MimeMessage);
			isMailSent = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isMailSent;
	}

}
