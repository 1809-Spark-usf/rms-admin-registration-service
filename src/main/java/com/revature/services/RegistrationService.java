package com.revature.services;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.revature.dtos.AdminDto;
import com.revature.models.Admin;
import com.revature.models.VerificationEmail;

@Service
public class RegistrationService {

	@Autowired
	AdminService adminService;
	
	@Value("${RMS_RESOURCE_URL:http://localhost:8080/email/}")
	String emailUri;
	
	@Value("${RMS_RESOURCE_URL:http://localhost:8080/admin-registration/}")
	String registrationUri;
	
	public Boolean registerNewAdmin(AdminDto adminDto) {

		Admin admin = new Admin();
		admin.setFirstname(adminDto.getFirstname());
		admin.setLastname(adminDto.getLastname());
		admin.setUsername(adminDto.getUsername());
		admin.setPassword(adminDto.getPassword());
		admin.setVerified(false);
		admin.setVerificationCode(generateVerificationCode());
		admin.setRegistrationDate(LocalDate.now());
		
		adminService.createAdmin(admin);
		
		sendEmail(admin.getUsername(), admin.getVerificationCode());
		
		return true;
		
	}
	
	/**
	 * 
	 * Sends an email to the admin with a link to click on to verify their account.
	 * 
	 * @param email
	 * @param verificationCode
	 */
	@HystrixCommand(fallbackMethod = "sendEmailFallback")
	private void sendEmail(String email, String verificationCode) {
		VerificationEmail verificationEmail = new VerificationEmail(email, registrationUri + "registration?" + verificationCode);
		new RestTemplate().postForLocation(URI.create(emailUri + "sendadminconfirmation"), verificationEmail);
	}
	
	/**
	 * 
	 * This method will be called as a fallback method if the hystrix command to send the email fails.
	 * This allows the user to be notified if there is a problem sending the email.
	 * 
	 * @param email
	 * @param verificationCode
	 */
	private void sendEmailFallback(String email, String verificationCode) {
		
	}
	
	private String generateVerificationCode() {
		
		UUID uuid = UUID.randomUUID();
		
		return uuid.toString();
		
	}
}
