package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.dtos.AdminDto;
import com.revature.models.Admin;
import com.revature.services.AdminService;
import com.revature.services.RegistrationService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	AdminService adminService;
	
	@PostMapping
	@ResponseBody
	public Boolean registerAdmin(@RequestBody AdminDto adminDto) {
		
		return registrationService.registerNewAdmin(adminDto);
	
	}
	
	/**
	 * Verifies an admin's email is correct when a link is clicked on in an email sent
	 * after registering for an account. Finds admin with unique verification code and
	 * sets the account status to be approved.
	 * @param verificationCode
	 * @author Jaron | Java-Nick-1811 : 1/11/2019
	 */
	@GetMapping
	public void verifyAdmin(@PathVariable String verificationCode) {
		
		Admin adminToVerify = adminService.getByVerificationCode(verificationCode);
		adminService.approveAccount(adminToVerify.getAdminId());
	}
	
}
