package com.revature.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.revature.dtos.AdminDto;
import com.revature.models.Admin;

@Service
public class RegistrationService {

	@Autowired
	AdminService adminService;
	
	@Value("${RMS_RESOURCE_URL:http://localhost:8080/email}")
	String emailUri;
	
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
		
		emailUri + "/sendadminconfirmation";
		
		return true;
		
	}
	
	private String generateVerificationCode() {
		
		UUID uuid = UUID.randomUUID();
		
		return uuid.toString();
		
	}
}
