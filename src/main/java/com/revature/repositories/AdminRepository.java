package com.revature.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	
	public List<Admin> findAll();

	public Admin findByUsername(String username);
	public Admin findByFirstname(String firstname);
	public Admin findByVerificationCode(String code);	
	
	@Modifying
	@Transactional
	@Query("UPDATE Admin a SET a.verified = true WHERE a.id = :id")
	public void setApprove(@Param("id") int id);
	
}
