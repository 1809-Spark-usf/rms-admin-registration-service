package com.revature.test.service;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Admin;
import com.revature.repositories.AdminRepository;
import com.revature.services.AdminService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {

	@Mock
	AdminRepository mockRepository;
	
	@InjectMocks
	AdminService adminService;
	
	@Test //@Mathew K 
	public void testGetId() {
		int targetId = 10;
		
		Admin expectedadmin = new Admin(targetId, null, null, null, null);
			
		when(mockRepository.findByAdminId(targetId)).thenReturn(expectedadmin); // Turn Optional 
		
		Admin admin = adminService.getAdmin(targetId);
		assertNotNull("Hotel", admin);
		assertThat("HEllo", admin, is(expectedadmin));
	}
	
	@Test //@Mathew K 
	public void testGetIdInvalid() {
		int targetId = 20; 
		

		
		when(mockRepository.findById(targetId))
				.thenReturn(null); // Turn to Optional later 
		Admin admin = null;
		
			HttpClientErrorException ex = null;
			
			try {
				admin = adminService.getAdmin(targetId);
						
				fail("Verification Code does not exist: Throw Exception");
				
				
			} catch ( HttpClientErrorException e) {
				ex = e;
			}
			
			assertNotNull("Exception is thrown", ex);
			assertThat("Status code is 404", 
					ex.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}
	
	
	//Verification Code 
	@Test //@Mathew K 
	public void testGetVerificationCode() {
		String verificationCode = "hello";
		
		Admin expectedadmin = new Admin(0, null, null, null, null);
		expectedadmin.setVerificationCode(verificationCode);
		when(mockRepository.findByVerificationCode(verificationCode)).thenReturn(expectedadmin); // Turn Optional 
		
		Admin admin = adminService.getByVerificationCode(verificationCode);
		assertNotNull("Hotel", admin);
		assertThat("HEllo", admin, is(expectedadmin));
	}
	
	
	@Test //@Mathew K 
	public void testGetVerificationCodeInvalid() {
		String verificationCode = " HI";
		
		when(mockRepository.findByVerificationCode(verificationCode))
				.thenReturn(null); // Turn to Optional later 
		Admin admin = null;
		
			HttpClientErrorException ex = null;
			
			try {
				admin = adminService.
						getByVerificationCode(verificationCode);
				fail("Verification Code does not exist: Throw Exception");
				
				
			} catch ( HttpClientErrorException e) {
				ex = e;
			}
			
			assertNotNull("Exception is thrown", ex);
			assertThat("Status code is 404", 
					ex.getStatusCode(), is(HttpStatus.NOT_FOUND));
			
	}
	
	
	

}

