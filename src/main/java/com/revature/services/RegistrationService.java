package com.revature.services;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
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

		if(accountAlreadyExists(adminDto.getUsername())) {
			return false;
		};
		
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
	 * Checks if an account with the same email already exists.
	 * 
	 * @param username
	 * @return true if the account exists, false otherwise
	 * @author Jaron | Java-Nick-1811 | 1/14/2019
	 */
	private boolean accountAlreadyExists(String username) {
		Admin admin = adminService.getByUsername(username);
		
		if(admin == null) {
			return false;
		}
		
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
		VerificationEmail verificationEmail = new VerificationEmail(email, registrationUri + "registration/" + verificationCode);
		new RestTemplate().postForLocation(URI.create(emailUri + "sendadminconfirmation"), verificationEmail);
	}
	
	/**
	 * 
	 * This method will be called as a fallback method if the hystrix command to send the email fails.
	 * 
	 * @param email
	 * @param verificationCode
	 */
	private void sendEmailFallback(String email, String verificationCode) {
		//This is called as a fallback method if the email cannot be sent
	}
	
	/**
	 * Generate a random uuid number to be used when verifying a user.
	 * 
	 * @return String of uuid
	 */
	private String generateVerificationCode() {
		
		UUID uuid = UUID.randomUUID();
		
		return uuid.toString();
		
	}
}
