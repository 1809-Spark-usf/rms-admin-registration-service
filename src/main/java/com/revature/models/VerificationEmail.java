package com.revature.models;

/**
 * The VerificationEmail Class
 * Used to transfer information needed
 * for the verification of admin users
 * through the RMSEmailService in an object
 * @author Austin D. 1811-Java-Nick 1/8/19 
 *
 */
public class VerificationEmail {
	
	/**The admin's email for the destination field*/
	private String adminEmail;
	/**The verification link to verify the admin's account*/
	private String verificationLink;
	/**Gets the admin email for VerificationEmail*/
	public String getAdminEmail() {
		return adminEmail;
	}
	/**Sets the admin email for VerificationEmail*/
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	/**Gets the verification link for VerificationEmail*/
	public String getVerificationLink() {
		return verificationLink;
	}
	/**Sets the verification link for VerificationEmail*/
	public void setVerificationLink(String verificationLink) {
		this.verificationLink = verificationLink;
	}
	/**To string for the VerificationEmail object*/
	@Override
	public String toString() {
		return "VerificationEmail [adminEmail=" + adminEmail + ", verificationLink=" + verificationLink + "]";
	}
	/**Constructor with args for VerificationEmail*/
	public VerificationEmail(String adminEmail, String verificationLink) {
		super();
		this.adminEmail = adminEmail;
		this.verificationLink = verificationLink;
	}
	/**No args constructor for VerificationEmail*/
	public VerificationEmail() {
		super();
	}
	
}
