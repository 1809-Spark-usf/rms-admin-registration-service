package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

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
	 * sets the account status to be approved. Also sends the admin to be stored in the
	 * login database after the email has been verified.
	 * 
	 * @param verificationCode
	 * @author Jaron | Java-Nick-1811 : 1/11/2019
	 */
	@GetMapping("/{code}")
	public void verifyAdmin(@PathVariable("code") String verificationCode, HttpServletResponse response) {
		
		Admin adminToVerify = adminService.getByVerificationCode(verificationCode);
		adminService.approveAccount(adminToVerify.getAdminId());
		adminService.saveToLoginService(adminToVerify);
		try {
			response.sendRedirect("http://localhost:4200/adminVerified");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
