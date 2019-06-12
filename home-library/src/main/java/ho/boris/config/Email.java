package ho.boris.config;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class Email {
	
	private MailSender mailSender;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	public void sendEmail(String from, String to, String subject, String message) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setFrom(from);
		smm.setTo(to);
		smm.setSubject(subject);
		smm.setText(message);
		mailSender.send(smm);
	}
}
