package com.revature.services;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.revature.dtos.AdminDto;
import com.revature.models.Admin;
import com.revature.models.VerificationEmail;
import com.revature.repositories.AdminRepository;

/**
 * Service to manage connections to the admin table in the database
 * @author Jaron | 1811-Java-Nick | 1/4/2019
 *
 */
@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepository;
	
	@Value("${RMS_RESOURCE_URL:http://localhost:8080/admin-login/}")
	String loginUri;
	
	
	/**
	 * Saves an admin to the database
	 * @param admin
	 */
	public void createAdmin(Admin admin) {
		adminRepository.save(admin);
	}
	
	/**
	 * Gets an admin from the databse by id
	 * @param id
	 * @return Admin
	 */
	public Admin getAdmin(Integer id) {
		return adminRepository.findById(id).get();
	}
	
	/**
	 * Gets all admins from the database
	 * @return List of admins
	 */
	public List<Admin> getAll() {
		return adminRepository.findAll();
	}
	
	
	public Admin getByVerificationCode(String code) {
		return adminRepository.findByVerificationCode(code);
	}
	
	/**
	 * 
	 * Sets an admin's account to be verified.
	 * 
	 * @param id
	 */
	public void approveAccount(int id) {
		adminRepository.setApprove(id);
	}
	
	/**
	 *  Gets an admin by username
	 *  
	 * @param username
	 * @return Admin
	 */
	public Admin getByUsername(String username) {
		return adminRepository.findByUsername(username);
	}
	
	/**
	 * 
	 * Sends an admin object to be persisted in the admin login service to be used for
	 * logging in.
	 * 
	 * @param adminDto
	 * @author Jaron | Java-Nick-1811 : 1/14/2019
	 */
	@HystrixCommand(fallbackMethod = "saveToLoginServiceFallback")
	public void saveToLoginService(Admin admin) {
		
		AdminDto adminDto = new AdminDto();
		adminDto.setFirstname(admin.getFirstname());
		adminDto.setLastname(admin.getLastname());
		adminDto.setUsername(admin.getUsername());
		adminDto.setPassword(admin.getPassword());
		
		new RestTemplate().postForLocation(URI.create(loginUri + "admin"), adminDto);
	}
	
	
	public void saveToLoginServiceFallback() {
		//This method is called as a fallback if saving to the login service fails.
	}
}
