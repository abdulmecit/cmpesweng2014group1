package cmpesweng2014.group1.nutty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.model.Mail;

@Component
public class MailService {

	@Autowired
	private MailSender mailSender;

	public void sendMail(Mail mail) throws MailException{
		 
		SimpleMailMessage message = new SimpleMailMessage();
 
		message.setTo(mail.getTo());
		message.setSubject(mail.getSubject());
		message.setText(mail.getContent());
		mailSender.send(message);	
	}
}